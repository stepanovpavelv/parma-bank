package parma.edu.money_transfer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import parma.edu.money_transfer.dao.BankAccountRepository;
import parma.edu.money_transfer.dto.BankAccountDto;
import parma.edu.money_transfer.exception.BankingException;
import parma.edu.money_transfer.mapper.BankAccountMapper;
import parma.edu.money_transfer.model.BankAccount;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Компонент для работы с банковскими счетами по шине Kafka.
 */
@Component
@RequiredArgsConstructor
public class BankAccountKafkaConsumer {
    @Value("${additional-kafka.bank_account.topic}")
    private String topic;

    private final KafkaTemplate<Integer, BankAccountDto> kafkaOperationTemplate;
    private final BankAccountRepository bankAccountRepository;

    public void send(BankAccountDto dto) {
        kafkaOperationTemplate.send(topic, dto);
    }

    @KafkaListener(id = "bank_account", topics = "#{'${additional-kafka.bank_account.topic}'}", containerFactory = "singleFactoryAccount")
    public void receive(BankAccountDto dataDto) {
        BankAccount account;
        if (dataDto.getId() != null) {
            account = getAccountById(dataDto.getId());
            account.setIsEnabled(dataDto.getIsEnabled());
        } else {
            account = BankAccountMapper.INSTANCE.toEntity(dataDto);
        }

        bankAccountRepository.save(account);
    }

    private BankAccount getAccountById(Integer id) {
        Optional<BankAccount> accountOpt = bankAccountRepository.findById(id);
        if (accountOpt.isEmpty()) {
            throw new BankingException.NotFoundException("Bank account not exists", id);
        }

        return accountOpt.get();
    }
}