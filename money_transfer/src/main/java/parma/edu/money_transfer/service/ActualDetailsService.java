package parma.edu.money_transfer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parma.edu.money_transfer.dao.AccountDetailsRepository;
import parma.edu.money_transfer.dto.AccountDetailsDto;
import parma.edu.money_transfer.dto.BankAccountDetailsDto;
import parma.edu.money_transfer.dto.BankAccountDto;
import parma.edu.money_transfer.mapper.AccountDetailsMapper;
import parma.edu.money_transfer.mapper.BankAccountMapper;
import parma.edu.money_transfer.model.AccountDetails;
import parma.edu.money_transfer.model.BankAccount;
import parma.edu.money_transfer.model.enums.TransactionType;
import parma.edu.money_transfer.model.pojo.BankProcessingInfo;

import static parma.edu.money_transfer.exception.BankingException.ForbiddenException;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с балансами банковских счетов.
 */
@Service
@RequiredArgsConstructor
public class ActualDetailsService {
    private final BankAccountService bankAccountService;
    private final AccountDetailsRepository accountDetailsRepository;

    @Transactional(readOnly = true)
    public BankAccountDetailsDto getDetailsByAccountId(Integer accountId) {
        BankAccount account = bankAccountService.getAccountById(accountId);
        List<AccountDetails> detailItems = accountDetailsRepository.findAccountDetailsByBankAccountId(accountId);
        return AccountDetailsMapper.INSTANCE.toBankDetailsDto(account, detailItems);
    }

    protected AccountDetailsDto checkCurrentSourceBalance(BankProcessingInfo processingInfo) {
        Optional<AccountDetails> maxDetailsOpt = getLastBalanceRecord(processingInfo.getSourceAccount());

        if (processingInfo.getType() == TransactionType.INCOMING) {
            return maxDetailsOpt.map(AccountDetailsMapper.INSTANCE::toDto).orElse(null);
        }

        if (maxDetailsOpt.isEmpty() || maxDetailsOpt.get().getAmount() < processingInfo.getAmount()) {
            throw new ForbiddenException("Operation will not be executed. Source's amount is not enough");
        }

        return AccountDetailsMapper.INSTANCE.toDto(maxDetailsOpt.get());
    }

    protected void changeAccountBalances(AccountDetailsDto source, BankProcessingInfo processingInfo) {
        double sourceAmount = source != null ? source.getAmount() : 0.0;
        if (processingInfo.getType() == TransactionType.INCOMING) {
            sourceAmount += processingInfo.getAmount();
            accountDetailsRepository.save(createBalanceRecord(processingInfo.getSourceAccount(), sourceAmount));
            return;
        }

        sourceAmount -= processingInfo.getAmount();
        accountDetailsRepository.save(createBalanceRecord(processingInfo.getSourceAccount(), sourceAmount));

        if (processingInfo.getTargetAccount() != null) {
            Optional<AccountDetails> target = getLastBalanceRecord(processingInfo.getTargetAccount());
            double targetAmount = target.map(AccountDetails::getAmount).orElse(0.0) + processingInfo.getAmount();
            accountDetailsRepository.save(createBalanceRecord(processingInfo.getTargetAccount(), targetAmount));
        }
    }

    private Optional<AccountDetails> getLastBalanceRecord(BankAccountDto account) {
        List<AccountDetails> sourceDetails = accountDetailsRepository.findAccountDetailsByBankAccountId(account.getId());
        return sourceDetails.stream().max(Comparator.comparing(AccountDetails::getActualDate));
    }

    private AccountDetails createBalanceRecord(BankAccountDto accountDto, double newAmount) {
        AccountDetails details = new AccountDetails();
        details.setBankAccount(BankAccountMapper.INSTANCE.toEntity(accountDto));
        details.setAmount(newAmount);
        return details;
    }
}