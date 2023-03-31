package parma.edu.money_transfer.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.ZonedDateTime;

@ApiModel(value = "Bank account", description = "Банковский счёт / кошелёк пользователя")
@Data
public class BankAccountDto {
    @ApiModelProperty(name = "id", value = "Идентификатор кошелька", example = "1")
    private Integer id;

    @ApiModelProperty(name = "userId", value = "Идентификатор пользователя", example = "1")
    private Integer userId;

    @ApiModelProperty(name = "isEnabled", value = "Состояние счета", example = "true")
    private Boolean isEnabled;

    @ApiModelProperty(name = "createDate", value = "Время создания счета", example = "2022-11-05T18:42:00.061954+04:00")
    private ZonedDateTime createDate;

    @ApiModelProperty(name = "updateDate", value = "Время обновления счета", example = "2022-11-05T18:43:00.061954+04:00")
    private ZonedDateTime updateDate;
}