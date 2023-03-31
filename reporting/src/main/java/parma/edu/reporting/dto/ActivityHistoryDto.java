package parma.edu.reporting.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.ZonedDateTime;

@ApiModel(value = "Activity history", description = "Элемент статистики по операциям")
@Data
public class ActivityHistoryDto {
    @ApiModelProperty(name = "id", value = "Идентификатор записи статистики'", example = "1")
    private Integer id;

    @ApiModelProperty(name = "operationId", value = "Идентификатор банковской операции", example = "1")
    private Integer operationId;

    @ApiModelProperty(name = "operationTypeId", value = "Идентификатор типа операции", example = "1")
    private Integer operationTypeId;

    @ApiModelProperty(name = "userSourceId", value = "Идентификатор пользователя, совершившего операцию", example = "1")
    private Integer userSourceId;

    @ApiModelProperty(name = "userSourceLogin", value = "Логин пользователя, совершившего операцию", example = "1")
    private String userSourceLogin;

    @ApiModelProperty(name = "accountSourceId", value = "Идентификатор счета, инициировавшего операцию", example = "1")
    private Integer accountSourceId;

    @ApiModelProperty(name = "userTargetId", value = "Идентификатор адресата-пользователя", example = "2")
    private Integer userTargetId;

    @ApiModelProperty(name = "userTargetLogin", value = "Логин адресата-пользователя", example = "2")
    private String userTargetLogin;

    @ApiModelProperty(name = "accountTargetId", value = "Идентификатор адресата-счета", example = "2")
    private Integer accountTargetId;

    @ApiModelProperty(name = "operationStatusId", value = "Статус банковской операции", example = "1")
    private Integer operationStatusId;

    @ApiModelProperty(name = "date", value = "Время совершения операции", example = "2022-11-05T18:42:00.061954+04:00")
    private ZonedDateTime date;

    @ApiModelProperty(name = "updateDate", value = "Время обновления операции", example = "2022-11-05T18:43:00.061954+04:00")
    private ZonedDateTime updateDate;

    @ApiModelProperty(name = "amount", value = "Сумма банковской операции", example = "100")
    private double amount;
}