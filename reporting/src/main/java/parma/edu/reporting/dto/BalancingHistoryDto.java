package parma.edu.reporting.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.ZonedDateTime;

@ApiModel(value = "Activity history", description = "Элемент статистики по операциям")
@Data
public class BalancingHistoryDto {
    @ApiModelProperty(name = "id", value = "Идентификатор записи статистики'", example = "1")
    private Integer id;

    @ApiModelProperty(name = "userId", value = "Идентификатор пользователя-кошелька", example = "1")
    private Integer userId;

    @ApiModelProperty(name = "userLogin", value = "Логин пользователя-кошелька", example = "1")
    private String userLogin;

    @ApiModelProperty(name = "accountId", value = "Идентификатор счета", example = "1")
    private Integer accountId;

    @ApiModelProperty(name = "date", value = "Дата актуальности", example = "2022-11-05T18:42:00.061954+04:00")
    private ZonedDateTime date;

    @ApiModelProperty(name = "amount", value = "Размер средств на счете кошелька", example = "100")
    private double amount;
}