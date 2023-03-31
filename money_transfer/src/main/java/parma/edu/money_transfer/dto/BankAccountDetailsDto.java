package parma.edu.money_transfer.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel(value = "Bank account details", description = "Банковский счет с детализацией")
@Data
public class BankAccountDetailsDto {
    @ApiModelProperty(name = "account", value = "Банковский счет")
    private BankAccountDto account;

    @ApiModelProperty(name = "detailItems", value = "Полная детализация")
    private List<AccountDetailsDto> detailItems;
}