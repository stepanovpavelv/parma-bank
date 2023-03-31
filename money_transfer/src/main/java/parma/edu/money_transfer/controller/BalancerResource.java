package parma.edu.money_transfer.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Демонстрационные методы работы с балансировщиком нагрузки", tags = {"Balance controller"})
@RestController
@RequestMapping("/api/v1/balance")
public class BalancerResource {
    @Value("${spring.balancer.secret}")
    private String balanceSecret;

    @ApiOperation(value = "Получить уникальный идентификатор контейнера приложения")
    @ApiResponse(code = 200, response = String.class, message = "Получен идентификатор")
    @GetMapping("/secret")
    @ResponseBody
    public ResponseEntity<String> secret() {
        return new ResponseEntity<>(balanceSecret, HttpStatus.OK);
    }
}