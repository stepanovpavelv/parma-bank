package parma.edu.money_transfer.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "Operation status", description = "Статус банковской операции")
@Data
public class OperationStatusDto {
    @ApiModelProperty(name = "id", value = "Идентификатор статуса банковской операции", example = "1")
    private Integer id;

    @ApiModelProperty(name = "name", value = "Наименивание статуса банковской операции", example = "Создана")
    private String name;
}