package parma.edu.auth.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import parma.edu.auth.dto.*;
import parma.edu.auth.exception.RegisterValidationException;
import parma.edu.auth.service.BankUserService;
import parma.edu.auth.service.RegisterService;

import javax.validation.Valid;

@Api(value = "Методы работы с пользователями", tags = {"User controller"})
@RestController
@ResponseBody
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserResource {
    private final RegisterService registerService;
    private final BankUserService userService;

    @ApiOperation(value = "Зарегистрировать нового пользователя системы")
    @ApiResponses(value = {
            @ApiResponse(code = 201, response = UserResponseDto.class, message = "Пользователь создан"),
            @ApiResponse(code = 400, response = ErrorResponseDto.class, message = "Проверьте корректность введенных полей"),
    })
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registration(@RequestBody
                                          @ApiParam(name = "request", required = true, value = "Запрос на регистрацию")
                                          @Valid CreateRequestDto request, BindingResult validResult) {
        if (validResult.hasErrors()) {
            throw new RegisterValidationException(validResult.getAllErrors());
        }

        UserResponseDto userDto = registerService.registerNewUser(request);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Получить информацию по пользователю")
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = UserResponseDto.class, message = "Пользователь получен"),
            @ApiResponse(code = 404, response = ErrorResponseDto.class, message = "Проверьте идентификатор пользователя"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> userById(
            @ApiParam(name = "id", required = true, value = "Идентификатор пользователя")
            @PathVariable("id") Integer userId) {
        UserResponseDto userDto = userService.loadUserById(userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}