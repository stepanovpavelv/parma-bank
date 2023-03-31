package parma.edu.money_transfer.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@ApiModel(value = "Operation details", description = "Банковская операция с детализацией по статусам")
@Data
@AllArgsConstructor
public class OperationDetailsDto {
    @ApiModelProperty(name = "operation", value = "Банковская операция")
    private OperationDto operation;

    @ApiModelProperty(name = "statuses", value = "История изменения статусов операции")
    private List<OperationStatusHistoryDto> statuses;
}