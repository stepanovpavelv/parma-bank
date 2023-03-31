package parma.edu.money_transfer.model.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import parma.edu.money_transfer.dto.BankAccountDto;
import parma.edu.money_transfer.model.enums.TransactionType;

@Getter
@Setter
@Builder
public class BankProcessingInfo {
    private BankAccountDto sourceAccount;
    private BankAccountDto targetAccount;
    private TransactionType type;
    private double amount;
}