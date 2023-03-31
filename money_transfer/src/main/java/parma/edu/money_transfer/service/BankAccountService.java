package parma.edu.money_transfer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
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

/**
 * Сервис для работы с банковскими счетами пользователей.
 */
@Service
@RequiredArgsConstructor
public class BankAccountService {
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

    @Transactional
    public BankAccountDto createBankAccount(Integer userId) {
        UserInfoDto dto = userService.readUserById(userId);
        if (dto == null) {
            throw new ForbiddenException("Пользователя с id=" + userId + " не существует");
        }

        BankAccountDto newAccount = new BankAccountDto();
        newAccount.setUserId(userId);
        newAccount.setIsEnabled(true);

        bankAccountKafkaService.send(newAccount);

        return newAccount;
    }

    public void setAccountState(Integer bankAccountId, boolean isEnabled) {
        BankAccountDto accountDto = getAccountByIdWithDto(bankAccountId);
        accountDto.setIsEnabled(isEnabled);

        bankAccountKafkaService.send(accountDto);
    }

    protected BankAccount getAccountById(Integer id) {
        Optional<BankAccount> accountOpt = bankAccountRepository.findById(id);
        if (accountOpt.isEmpty()) {
            throw new NotFoundException("Bank account not exists", id);
        }

        return accountOpt.get();
    }
}