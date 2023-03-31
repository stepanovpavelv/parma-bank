package parma.edu.money_transfer;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import parma.edu.money_transfer.dto.*;
import parma.edu.money_transfer.model.enums.OperationState;
import parma.edu.money_transfer.service.ActualDetailsService;
import parma.edu.money_transfer.service.BankAccountService;
import parma.edu.money_transfer.service.OperationService;

import static parma.edu.money_transfer.exception.BankingException.NotFoundException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Tag("operations")
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class OperationUnitTest extends AbstractTest {
    private final static int USER_ID1 = 1;
    private final static int USER_ID2 = 2;
    private final static int USER_ID3 = 3;

    private final static int ADDING_OPERATION_TYPE = 1;
    private final static int TRANSFER_OPERATION_TYPE = 2;
    private final static int WITHDRAW_OPERATION_TYPE = 3;

    @Value("${additional-kafka.operation.topic}")
    private String topicName;

    @Autowired
    private OperationService operationService;
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private ActualDetailsService actualDetailsService;

    @BeforeAll
    public void init() {
        this.initBaseProperties(topicName);
    }

    //@BeforeAll
    //@ValueSource(ints = {1,2,3})
    @ParameterizedTest
    @MethodSource("userIdProvider")
    @DisplayName("Creating accounts for users")
    @Order(0)
    public void createTestBankAccounts(int userId) throws InterruptedException {
        this.initUserServiceMockObject(userId);
        BankAccountDto bankAccount = bankAccountService.createBankAccount(userId);
        Assertions.assertNotNull(bankAccount);
        Assertions.assertEquals(userId, bankAccount.getUserId());
        Assertions.assertTrue(bankAccount.getIsEnabled());
        Thread.sleep(500);
    }

    @Test
    @DisplayName("User id=1 is trying to withdraw 100 money units from empty account.")
    @Order(1)
    public void givenOperationService_shouldNotWithdrawMoney_forUserId1() {
        final double sum = 100.0;
        BankAccountDto source = makeSingleAccount(USER_ID1);
        OperationDto operation = new OperationDto();
        operation.setOperationType(makeType(WITHDRAW_OPERATION_TYPE));
        operation.setAccountSource(source);
        operation.setAmount(sum);

        operation.setId(operationService.createOperation(operation));

        try {
            ConsumerRecord<Integer, String> message = records.poll(AWAIT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
            Assertions.assertNotNull(message);

            OperationDto kafkaOperation = mapper.readValue(message.value(), OperationDto.class);
            Assertions.assertNotNull(kafkaOperation);
            Assertions.assertEquals(sum, kafkaOperation.getAmount());
            Thread.sleep(1000);
        } catch (InterruptedException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // по операции должны быть только 3 статуса: создана, принята, отклонена
        OperationDetailsDto operationDetails = operationService.getStatusHistoryByOperationId(operation.getId());
        Assertions.assertNotNull(operationDetails);
        Assertions.assertNotNull(operationDetails.getStatuses());
        Assertions.assertEquals(3, operationDetails.getStatuses().size());
        Assertions.assertEquals(OperationState.CANCELLED.getId(), operationDetails.getStatuses().get(2).getStatus().getId());
    }

    @Test
    @DisplayName("User id=1 is adding 100 money units to his cash.")
    @Order(2)
    public void givenOperationService_shouldAddMoney_ForUserId1() {
        final double sum = 100.0;
        final double currentBalance = 100.0;

        BankAccountDto sourceAccount = makeSingleAccount(USER_ID1);
        OperationDto operation = new OperationDto();
        operation.setOperationType(makeType(ADDING_OPERATION_TYPE));
        operation.setAccountSource(sourceAccount);
        operation.setAmount(sum);

        operation.setId(operationService.createOperation(operation));

        try {
            ConsumerRecord<Integer, String> message = records.poll(AWAIT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
            Assertions.assertNotNull(message);

            OperationDto kafkaOperation = mapper.readValue(message.value(), OperationDto.class);
            Assertions.assertNotNull(kafkaOperation);
            Assertions.assertEquals(sum, kafkaOperation.getAmount());
        } catch (InterruptedException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // чекаем историю операций
        final int index = 1;
        List<OperationDto> currentOperations = operationService.getOperationsBySourceId(sourceAccount.getId());
        Assertions.assertNotNull(currentOperations);
        Assertions.assertEquals(2, currentOperations.size());
        Assertions.assertEquals(sum, currentOperations.get(index).getAmount());
        Assertions.assertEquals(sourceAccount.getId(), currentOperations.get(index).getAccountSource().getId());
        Assertions.assertEquals(USER_ID1, currentOperations.get(index).getAccountSource().getUserId());
        Assertions.assertNull(currentOperations.get(index).getAccountTarget());

        // и текущий баланс
        BankAccountDetailsDto sourceDetails = actualDetailsService.getDetailsByAccountId(sourceAccount.getId());
        Assertions.assertNotNull(sourceDetails);
        Assertions.assertNotNull(sourceDetails.getAccount());
        Assertions.assertNotNull(sourceDetails.getDetailItems());
        Assertions.assertEquals(1, sourceDetails.getDetailItems().size());
        Assertions.assertEquals(currentBalance, sourceDetails.getDetailItems().get(0).getAmount());
    }

    @Test
    @DisplayName("User id=1 is trying to spend 200 money units to user id=2.")
    @Order(3)
    public void givenOperationService_shouldSpendMoneyToUserId2_ByUserId1() {
        final double transferSum = 200.0;
        final double currentBalance = 100.0;

        BankAccountDto sourceAccount = makeSingleAccount(USER_ID1);
        BankAccountDto targetAccount = makeSingleAccount(USER_ID2);
        OperationDto operation = new OperationDto();
        operation.setOperationType(makeType(TRANSFER_OPERATION_TYPE));
        operation.setAccountSource(sourceAccount);
        operation.setAccountTarget(targetAccount);
        operation.setAmount(transferSum);

        operation.setId(operationService.createOperation(operation));

        try {
            ConsumerRecord<Integer, String> message = records.poll(AWAIT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
            Assertions.assertNotNull(message);

            OperationDto kafkaOperation = mapper.readValue(message.value(), OperationDto.class);
            Assertions.assertNotNull(kafkaOperation);
            Assertions.assertEquals(transferSum, kafkaOperation.getAmount());
        } catch (InterruptedException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        final int index = 2;
        // чекаем историю операций
        List<OperationDto> currentOperations = operationService.getOperationsBySourceId(sourceAccount.getId());
        Assertions.assertNotNull(currentOperations);
        Assertions.assertEquals(3, currentOperations.size());
        Assertions.assertEquals(transferSum, currentOperations.get(index).getAmount());
        Assertions.assertEquals(sourceAccount.getId(), currentOperations.get(index).getAccountSource().getId());
        Assertions.assertNotNull(currentOperations.get(index).getAccountTarget());
        Assertions.assertEquals(targetAccount.getId(), currentOperations.get(index).getAccountTarget().getId());
        Assertions.assertEquals(USER_ID1, currentOperations.get(index).getAccountSource().getUserId());
        Assertions.assertEquals(USER_ID2, currentOperations.get(index).getAccountTarget().getUserId());

        // и текущий баланс. Баланс не должен измениться!
        BankAccountDetailsDto sourceDetails = actualDetailsService.getDetailsByAccountId(sourceAccount.getId());
        Assertions.assertNotNull(sourceDetails);
        Assertions.assertNotNull(sourceDetails.getAccount());
        Assertions.assertNotNull(sourceDetails.getDetailItems());
        Assertions.assertEquals(1, sourceDetails.getDetailItems().size());
        Assertions.assertEquals(currentBalance, sourceDetails.getDetailItems().get(0).getAmount());
    }

    @ParameterizedTest
    @CsvSource({
            "10, 1, 2, 1000.0",
            "1, 20, 2, 205.0",
            "1, 2, 30, 58.5",
            "-1, , 1, 120.1",
            "9, , 3, 19291.9"
    })
    @DisplayName("Tests would not be executed due to non-existing operation params.")
    @Order(4)
    public void givenOperationService_shouldNotExecute_NotFoundException(Integer sourceId, Integer targetId, Integer typeId, Double amount) {
        BankAccountDto sourceAccount = new BankAccountDto();
        sourceAccount.setId(sourceId);

        BankAccountDto targetAccount = new BankAccountDto();
        targetAccount.setId(targetId);

        OperationTypeDto operationType = new OperationTypeDto();
        operationType.setId(typeId);

        OperationDto operation = new OperationDto();
        operation.setAccountSource(sourceAccount);
        operation.setAccountTarget(targetAccount);
        operation.setOperationType(operationType);
        operation.setAmount(amount);

        Assertions.assertThrows(NotFoundException.class, () -> operationService.createOperation(operation));
    }

    @Test
    @DisplayName("User id=1 is trying to spend 55 money units to user id=3.")
    @Order(5)
    public void givenOperationService_shouldSpendMoneyToUserId3_ByUserId1() {
        final double transferSum = 55.0;
        final double currentBalance = 100.0;

        BankAccountDto sourceAccount = makeSingleAccount(USER_ID1);
        BankAccountDto targetAccount = makeSingleAccount(USER_ID3);
        OperationDto operation = new OperationDto();
        operation.setOperationType(makeType(TRANSFER_OPERATION_TYPE));
        operation.setAccountSource(sourceAccount);
        operation.setAccountTarget(targetAccount);
        operation.setAmount(transferSum);

        operation.setId(operationService.createOperation(operation));

        try {
            ConsumerRecord<Integer, String> message = records.poll(AWAIT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
            Assertions.assertNotNull(message);

            OperationDto kafkaOperation = mapper.readValue(message.value(), OperationDto.class);
            Assertions.assertNotNull(kafkaOperation);
            Assertions.assertEquals(transferSum, kafkaOperation.getAmount());
            Thread.sleep(500);
        } catch (InterruptedException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        final int index = 3;
        // чекаем историю операций
        List<OperationDto> currentOperations = operationService.getOperationsBySourceId(sourceAccount.getId());
        Assertions.assertNotNull(currentOperations);
        Assertions.assertEquals(4, currentOperations.size());
        Assertions.assertEquals(transferSum, currentOperations.get(index).getAmount());
        Assertions.assertEquals(sourceAccount.getId(), currentOperations.get(index).getAccountSource().getId());
        Assertions.assertNotNull(currentOperations.get(index).getAccountTarget());
        Assertions.assertEquals(targetAccount.getId(), currentOperations.get(index).getAccountTarget().getId());
        Assertions.assertEquals(USER_ID1, currentOperations.get(index).getAccountSource().getUserId());
        Assertions.assertEquals(USER_ID3, currentOperations.get(index).getAccountTarget().getUserId());

        // текущий баланс - аккаунт источник
        BankAccountDetailsDto sourceDetails = actualDetailsService.getDetailsByAccountId(sourceAccount.getId());
        Assertions.assertNotNull(sourceDetails);
        Assertions.assertNotNull(sourceDetails.getAccount());
        Assertions.assertNotNull(sourceDetails.getDetailItems());
        Assertions.assertEquals(2, sourceDetails.getDetailItems().size());
        Assertions.assertEquals(currentBalance - transferSum, sourceDetails.getDetailItems().get(1).getAmount());

        // текущий баланс - аккаунт-приёмник
        BankAccountDetailsDto targetDetails = actualDetailsService.getDetailsByAccountId(targetAccount.getId());
        Assertions.assertNotNull(targetDetails);
        Assertions.assertNotNull(targetDetails.getAccount());
        Assertions.assertNotNull(targetDetails.getDetailItems());
        Assertions.assertEquals(1, targetDetails.getDetailItems().size());
        Assertions.assertEquals(transferSum, targetDetails.getDetailItems().get(0).getAmount());
    }

    @AfterAll
    public void tearDown() {
        container.stop();
    }

    private BankAccountDto makeSingleAccount(int userId) {
        Optional<BankAccountDto> firstOpt = bankAccountService.getAccountsByUserId(userId).stream().findFirst();
        if (firstOpt.isEmpty()) {
            throw new NotFoundException("Collection is empty");
        }
        return firstOpt.get();
    }

    private static Stream<Integer> userIdProvider() {
        return Stream.of(USER_ID1, USER_ID2, USER_ID3);
    }

    private static OperationTypeDto makeType(int typeId) {
        OperationTypeDto operationType = new OperationTypeDto();
        operationType.setId(typeId);
        return operationType;
    }
}