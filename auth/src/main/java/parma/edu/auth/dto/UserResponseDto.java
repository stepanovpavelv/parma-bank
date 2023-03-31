package parma.edu.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel(value = "User response", description = "Ответ на запрос пользователя")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserResponseDto extends BaseResponseDto {
    @ApiModelProperty(name = "login", value = "Логин пользователя", example = "starboy98")
    private String login;

    @ApiModelProperty(name = "fullName", value = "ФИО пользователя", example = "Иванов Петр Ильич")
    private String fullName;

    @ApiModelProperty(name = "role", value = "Роль пользователя", example = "ADMIN")
    private String role;

    @ApiModelProperty(name = "isEnabled", value = "Признак активности учетной записи пользователя", example = "true")
    private boolean isEnabled;
}