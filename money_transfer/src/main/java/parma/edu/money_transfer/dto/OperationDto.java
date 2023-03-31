package parma.edu.money_transfer.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.ZonedDateTime;

@ApiModel(value = "Operation", description = "Информация о банковской операции")
@Data
public class OperationDto {
    @ApiModelProperty(name = "id", value = "Идентификатор банковской операции", example = "1")
    private Integer id;

    @ApiModelProperty(required = true, name = "accountSourceId", value = "Идентификатор счета, инициировавшего операцию", example = "1")
    private BankAccountDto accountSource;

    @ApiModelProperty(name = "accountTargetId", value = "Идентификатор счета, на который могут отправиться средства", example = "2")
    private BankAccountDto accountTarget;

    @ApiModelProperty(required = true, name = "operationTypeId", value = "Идентификатор типа операции", example = "2")
    private OperationTypeDto operationType;

    @ApiModelProperty(name = "date", value = "Время совершения операции", example = "2022-11-05T18:42:00.061954+04:00")
    private ZonedDateTime date;

    @ApiModelProperty(name = "updateDate", value = "Время обновления операции", example = "2022-11-05T18:43:00.061954+04:00")
    private ZonedDateTime updateDate;

    @ApiModelProperty(required = true, name = "amount", value = "Сумма банковской операции", example = "100")
    private double amount;
}