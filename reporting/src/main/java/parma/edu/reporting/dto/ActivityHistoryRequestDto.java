package parma.edu.reporting.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.ZonedDateTime;

@ApiModel(value = "Activity history request", description = "Запрос получения истории активности по различным параметрам")
@Data
public class ActivityHistoryRequestDto {
    @ApiModelProperty(name = "id")
    private Integer id;

    @ApiModelProperty(name = "operationId", value = "Идентификатор банковской операции", example = "1")
    private Integer operationId;

    @ApiModelProperty(name = "operationTypeId", value = "Идентификатор типа операции", example = "1")
    private Integer operationTypeId;

    @ApiModelProperty(name = "userSourceId", value = "Идентификатор пользователя, совершившего операцию", example = "1")
    private Integer userSourceId;

    @ApiModelProperty(name = "userSourceLogin", value = "Логин пользователя, совершившего операцию", example = "test")
    private String userSourceLogin;

    @ApiModelProperty(name = "accountSourceId", value = "Идентификатор счета, инициировавшего операцию", example = "1")
    private Integer accountSourceId;

    @ApiModelProperty(name = "userTargetId", value = "Идентификатор адресата-пользователя", example = "2")
    private Integer userTargetId;

    @ApiModelProperty(name = "userTargetLogin", value = "Логин адресата-пользователя", example = "test")
    private String userTargetLogin;

    @ApiModelProperty(name = "accountTargetId", value = "Идентификатор адресата-счета", example = "2")
    private Integer accountTargetId;

    @ApiModelProperty(name = "operationStatusId", value = "Статус банковской операции", example = "1")
    private Integer operationStatusId;

    @ApiModelProperty(name = "startDate", value = "Время совершения операции (от)", example = "2022-01-05T18:42:00.061954+04:00")
    private ZonedDateTime startDate;

    @ApiModelProperty(name = "finishDate", value = "Время совершения операции (до)", example = "2022-12-05T18:42:00.061954+04:00")
    private ZonedDateTime finishDate;

    @ApiModelProperty(name = "startUpdateDate", value = "Время обновления операции (от)", example = "2022-01-05T18:43:00.061954+04:00")
    private ZonedDateTime startUpdateDate;

    @ApiModelProperty(name = "finishUpdateDate", value = "Время обновления операции (до)", example = "2022-11-05T18:43:00.061954+04:00")
    private ZonedDateTime finishUpdateDate;

    @ApiModelProperty(name = "startAmount", value = "Сумма банковской операции (от)", example = "10")
    private Double startAmount;

    @ApiModelProperty(name = "finishAmount", value = "Сумма банковской операции (до)", example = "100")
    private Double finishAmount;
}