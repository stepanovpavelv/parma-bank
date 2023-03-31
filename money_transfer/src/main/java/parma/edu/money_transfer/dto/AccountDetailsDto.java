package parma.edu.money_transfer.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.ZonedDateTime;

@ApiModel(value = "Account details", description = "История изменения баланса")
@Data
public class AccountDetailsDto {
    @ApiModelProperty(name = "id", value = "Идентификатор истории баланса", example = "1")
    private Integer id;
//    private BankAccountDto account;

    @ApiModelProperty(name = "actualDate", value = "Дата актуальности средств", example = "2022-10-09T03:02:00.074816+04:00")
    private ZonedDateTime actualDate;

    @ApiModelProperty(name = "amount", value = "Размер денежных средств", example = "1000")
    private double amount;
}