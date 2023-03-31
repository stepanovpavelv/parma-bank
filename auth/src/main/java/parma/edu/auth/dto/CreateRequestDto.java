package parma.edu.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import parma.edu.auth.system.security.Role;
import parma.edu.auth.util.EnumNamePattern;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel(value = "Register user request", description = "Запрос на создание пользователя")
@Data
public class CreateRequestDto {
    @NotNull
    @Size(min = 3, max = 20, message = "Длина уникального имени должна быть от 3 до 20")
    @ApiModelProperty(name = "login", required = true, value = "Логин пользователя", example = "starboy98")
    private String login;

    @NotNull
    @Size(min = 3, max = 20, message = "Пароль должен быть длиной от 3 до 20 символов")
    @ApiModelProperty(name = "password", required = true, value = "Пароль пользователя", example = "****")
    private String password;

    @NotNull
    @Size(min = 1, max = 100, message = "ФИО должно быть от 1 до 100 символов")
    @ApiModelProperty(name = "fullName", required = true, value = "ФИО пользователя", example = "Кипятков Иван Павлович")
    private String fullName;

    @NotNull
    @EnumNamePattern(regexp = "USER|ADMIN")
    @ApiModelProperty(name = "role", required = true, value = "Роль пользователя", example = "USER", notes = "У пользователя может быть только одна роль.")
    private Role role;
}