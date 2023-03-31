package parma.edu.reporting.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.ZonedDateTime;

@ApiModel(value = "Balancing history request", description = "Запрос получения истории изменения баланса по различным параметрам")
@Data
public class BalancingHistoryRequestDto {
    @ApiModelProperty(name = "id", value = "Идентификатор записи статистики'", example = "1")
    private Integer id;

    @ApiModelProperty(name = "userId", value = "Идентификатор пользователя-кошелька", example = "1")
    private Integer userId;

    @ApiModelProperty(name = "userLogin", value = "Логин пользователя-кошелька", example = "test")
    private String userLogin;

    @ApiModelProperty(name = "accountId", value = "Идентификатор счета", example = "1")
    private Integer accountId;

    @ApiModelProperty(name = "startDate", value = "Дата актуальности (от)", example = "2022-01-05T18:42:00.061954+04:00")
    private ZonedDateTime startDate;

    @ApiModelProperty(name = "finishDate", value = "Дата актуальности (до)", example = "2022-12-05T18:42:00.061954+04:00")
    private ZonedDateTime finishDate;

    @ApiModelProperty(name = "startAmount", value = "Размер средств на счете кошелька (от)", example = "10")
    private Double startAmount;

    @ApiModelProperty(name = "finishAmount", value = "Размер средств на счете кошелька (до)", example = "100")
    private Double finishAmount;
}