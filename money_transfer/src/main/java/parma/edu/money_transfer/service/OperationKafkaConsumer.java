package parma.edu.money_transfer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import parma.edu.money_transfer.dto.AccountDetailsDto;
import parma.edu.money_transfer.dto.OperationDto;
import parma.edu.money_transfer.exception.BankingException;
import parma.edu.money_transfer.model.enums.OperationState;
import parma.edu.money_transfer.model.enums.TransactionType;
import parma.edu.money_transfer.model.pojo.BankProcessingInfo;

/**
 * Компонент для работы с операциями по шине Kafka.
 */
@Component
@RequiredArgsConstructor
public class OperationKafkaConsumer {
    @Value("${additional-kafka.operation.topic}")
    private String topic;

    private final ActualDetailsService actualDetailsService;
    private final OperationHistoryService operationHistoryService;
    private final KafkaTemplate<Integer, OperationDto> kafkaOperationTemplate;

    public void send(OperationDto dto) {
        kafkaOperationTemplate.send(topic, dto);
    }

    @KafkaListener(id = "operation", topics = "#{'${additional-kafka.operation.topic}'}", containerFactory = "singleFactoryOperation")
    public void receive(OperationDto dataDto) {
        operationHistoryService.saveHistoryItem(dataDto, OperationState.PROCESSING);

        // логика проверки баланса
        BankProcessingInfo info = BankProcessingInfo.builder()
                .sourceAccount(dataDto.getAccountSource())
                .targetAccount(dataDto.getAccountTarget())
                .type(TransactionType.getByExpense(dataDto.getOperationType().getIsExpense()))
                .amount(dataDto.getAmount())
                .build();

        AccountDetailsDto source;
        try {
            source = actualDetailsService.checkCurrentSourceBalance(info);

            actualDetailsService.changeAccountBalances(source, info);
            operationHistoryService.saveHistoryItem(dataDto, OperationState.DONE);
        } catch (BankingException.ForbiddenException ex) {
            operationHistoryService.saveHistoryItem(dataDto, OperationState.CANCELLED);
        }
    }
}