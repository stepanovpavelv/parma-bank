package parma.edu.money_transfer.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.ZonedDateTime;

@ApiModel(value = "Operation status history", description = "История изменения статусов операции")
@Data
public class OperationStatusHistoryDto {
    @ApiModelProperty(name = "id", value = "Идентификатор изменения статуса операции", example = "1")
    private Integer id;

    @ApiModelProperty(name = "date", value = "Время изменения статуса операции", example = "2021-03-01T12:09:01.044216+04:00")
    private ZonedDateTime date;

    @ApiModelProperty(name = "status", value = "Статус банковской операции")
    private OperationStatusDto status;
}