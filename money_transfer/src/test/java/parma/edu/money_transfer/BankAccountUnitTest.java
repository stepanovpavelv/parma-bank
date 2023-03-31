package parma.edu.money_transfer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.jdbc.Sql;
import parma.edu.money_transfer.dto.BankAccountDto;
import parma.edu.money_transfer.service.BankAccountService;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import static parma.edu.money_transfer.exception.BankingException.NotFoundException;

@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, value = "classpath:/db/scripts/delete-all-bank-accounts.sql")
@Tag("bank-accounts")
public class BankAccountUnitTest extends AbstractTest {
    @Autowired
    private BankAccountService bankAccountService;

    @Value("${additional-kafka.bank_account.topic}")
    private String topicName;

    @BeforeAll
    public void init() {
        this.initBaseProperties(topicName);
    }

    @Test
    public void givenBankAccountService_whenSave_thenOk() {
        final int userId = 1;
        this.initUserServiceMockObject(userId);
        BankAccountDto bankAccount = bankAccountService.createBankAccount(userId);

        Assertions.assertNotNull(bankAccount);
        Assertions.assertEquals(userId, bankAccount.getUserId());
    }

    @Test
    public void givenBankAccountService_whenSaveAndRetrieveByNotId_thenNotFound() {
        final int userId = 2;
        this.initUserServiceMockObject(userId);
        BankAccountDto bankAccount = bankAccountService.createBankAccount(userId);
        Assertions.assertNotNull(bankAccount);

        try {
            ConsumerRecord<Integer, String> message = records.poll(AWAIT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
            Assertions.assertNotNull(message);
            Thread.sleep(500);

            List<BankAccountDto> accounts = bankAccountService.getAccountsByUserId(userId);
            Assertions.assertNotNull(accounts);
            Assertions.assertEquals(1, accounts.size());

            Integer curAccountId = accounts.get(0).getId();
            Integer bankIdForSearch = curAccountId + 1;

            Assertions.assertThrows(NotFoundException.class,
                    () -> bankAccountService.getAccountByIdWithDto(bankIdForSearch));

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void givenBankAccountService_whenSaveAndRetrieve_thenOk() {
        final int userId = 3;
        this.initUserServiceMockObject(userId);
        BankAccountDto bankAccount = bankAccountService.createBankAccount(userId);

        try {
            ConsumerRecord<Integer, String> message = records.poll(AWAIT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
            Assertions.assertNotNull(message);
            Thread.sleep(500);

            List<BankAccountDto> accounts = bankAccountService.getAccountsByUserId(userId);
            Assertions.assertNotNull(accounts);
            Assertions.assertEquals(1, accounts.size());

            Integer curAccountId = accounts.get(0).getId();

            Assertions.assertNotNull(bankAccount);
            Assertions.assertEquals(curAccountId, bankAccountService.getAccountByIdWithDto(curAccountId).getId());

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void givenBankAccountService_whenSaveAndRetrieveByUserId_thenOk() {
        final int userId = 4;
        final int anotherUserId = 5;
        final int accountsNumber = 10;
        final int anotherNumber = 0;

        this.initUserServiceMockObject(userId);
        for (int i = 0; i < accountsNumber; i++) {
            bankAccountService.createBankAccount(userId);
        }

        try {
            ConsumerRecord<Integer, String> message = records.poll(AWAIT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
            Assertions.assertNotNull(message);
            Thread.sleep(500);

            List<BankAccountDto> accounts = bankAccountService.getAccountsByUserId(userId);
            Assertions.assertNotNull(accounts);
            Assertions.assertEquals(accountsNumber, accounts.size());

            List<BankAccountDto> anotherAccounts = bankAccountService.getAccountsByUserId(anotherUserId);
            Assertions.assertNotNull(anotherAccounts);
            Assertions.assertEquals(anotherNumber, anotherAccounts.size());

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void givenBankAccountService_whenSaveAndChangeState_thenOk() {
        final int userId = 5;
        final boolean initialState = true;
        final boolean newState = false;

        this.initUserServiceMockObject(userId);
        bankAccountService.createBankAccount(userId);

        try {
            ConsumerRecord<Integer, String> message = records.poll(AWAIT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
            Assertions.assertNotNull(message);
            Thread.sleep(500);

            List<BankAccountDto> accounts = bankAccountService.getAccountsByUserId(userId);
            Assertions.assertNotNull(accounts);
            Assertions.assertEquals(1, accounts.size());
            Assertions.assertEquals(initialState, accounts.get(0).getIsEnabled());

            bankAccountService.setAccountState(accounts.get(0).getId(), newState);

            message = records.poll(AWAIT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
            Assertions.assertNotNull(message);
            Thread.sleep(500);

            BankAccountDto accountDto = bankAccountService.getAccountByIdWithDto(accounts.get(0).getId());
            Assertions.assertNotNull(accountDto);
            Assertions.assertEquals(newState, accountDto.getIsEnabled());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    public void tearDown() {
        container.stop();
    }
}