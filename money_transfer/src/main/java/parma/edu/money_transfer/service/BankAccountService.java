package parma.edu.money_transfer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parma.edu.money_transfer.dao.BankAccountRepository;
import parma.edu.money_transfer.dto.BankAccountDto;
import parma.edu.money_transfer.dto.UserInfoDto;
import parma.edu.money_transfer.mapper.BankAccountMapper;
import parma.edu.money_transfer.model.BankAccount;

import static parma.edu.money_transfer.exception.BankingException.NotFoundException;
import static parma.edu.money_transfer.exception.BankingException.ForbiddenException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Сервис для работы с банковскими счетами пользователей.
 */
@Service
@RequiredArgsConstructor
public class BankAccountService {
    @Value("${spring.kafka.consumer.latch-timeout}")
    private Integer timeout;

    private final UserService userService;
    private final BankAccountKafkaConsumer bankAccountKafkaService;
    private final BankAccountRepository bankAccountRepository;

    @Transactional(readOnly = true, noRollbackFor = { NotFoundException.class })
    public BankAccountDto getAccountByIdWithDto(Integer id) {
        BankAccount account = getAccountById(id);
        return BankAccountMapper.INSTANCE.toDto(account);
    }

    @Transactional(readOnly = true)
    public List<BankAccountDto> getAccountByIds(List<Integer> ids) {
        List<BankAccount> accounts = bankAccountRepository.findAllById(ids);
        return BankAccountMapper.INSTANCE.toListDto(accounts);
    }

    @Transactional(readOnly = true)
    public List<BankAccountDto> getAccountsByUserId(Integer userId) {
        List<BankAccount> accounts = bankAccountRepository.findByUserId(userId);
        return BankAccountMapper.INSTANCE.toListDto(accounts);
    }

    @Transactional(value = "kafkaTransactionManager")
    public BankAccountDto createBankAccount(Integer userId) {
        UserInfoDto dto = userService.readUserById(userId);
        if (dto == null) {
            throw new ForbiddenException("Пользователя с id=" + userId + " не существует");
        }

        BankAccount savingAccount = new BankAccount();
        savingAccount = bankAccountRepository.save(savingAccount);

        BankAccountDto newDto = new BankAccountDto();
        newDto.setId(savingAccount.getId());
        newDto.setUserId(userId);
        newDto.setIsEnabled(true);

        saveAccountBlock(newDto, "Ошибка при создании счёта пользователя");

        BankAccount savedAccount = getAccountById(newDto.getId());
        return BankAccountMapper.INSTANCE.toDto(savedAccount);
    }

    @Transactional(value = "kafkaTransactionManager")
    public void setAccountState(Integer bankAccountId, boolean isEnabled) {
        BankAccountDto accountDto = getAccountByIdWithDto(bankAccountId);
        accountDto.setIsEnabled(isEnabled);
        saveAccountBlock(accountDto, "Ошибка при изменении активности счёта пользователя");
    }

    protected BankAccount getAccountById(Integer id) {
        Optional<BankAccount> accountOpt = bankAccountRepository.findById(id);
        if (accountOpt.isEmpty()) {
            throw new NotFoundException("Bank account not exists", id);
        }

        return accountOpt.get();
    }

    private void saveAccountBlock(BankAccountDto dto, String errorMessage) {
        CountDownLatch latch = new CountDownLatch(1);
        bankAccountKafkaService.setLatch(latch);
        bankAccountKafkaService.send(dto);
        try {
            boolean isLatched = bankAccountKafkaService.getLatch().await(timeout, TimeUnit.SECONDS);
            if (!isLatched) {
                throw new ForbiddenException(errorMessage);
            }
        } catch (InterruptedException e) {
            throw new ForbiddenException(errorMessage);
        }
    }
}