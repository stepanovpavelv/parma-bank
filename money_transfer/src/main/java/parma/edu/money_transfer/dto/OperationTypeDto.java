package parma.edu.money_transfer.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "Operation type", description = "Тип банковской операции")
@Data
public class OperationTypeDto {
    @ApiModelProperty(name = "id", value = "Идентификатор типа банковской операции", example = "1")
    private Integer id;

    @ApiModelProperty(name = "name", value = "Наименование типа банковской операции", example = "Пополнение")
    private String name;

    @ApiModelProperty(name = "isExpense", value = "Операция является расходом", example = "true")
    private Boolean isExpense;
}