package parma.edu.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel(value = "Error response", description = "Ответ системы в случае возникновения ошибки")
@Getter
public class ErrorResponseDto extends BaseResponseDto {
    @ApiModelProperty(name = "message", value = "Сообщение об ошибке", example = "Что-то пошло не так")
    private final String message;

    public ErrorResponseDto(String message) {
        ok = false;
        this.message = message;
    }
}