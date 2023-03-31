package parma.edu.money_transfer.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import parma.edu.money_transfer.dto.OperationDetailsDto;
import parma.edu.money_transfer.dto.OperationDto;
import parma.edu.money_transfer.exception.BankExceptionResponse;
import parma.edu.money_transfer.service.OperationService;

import java.util.List;

@Api(value = "Методы работы с банковскими операциями", tags = {"Deal controller"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/deal")
public class DealResource {
    private final OperationService operationService;

    @ApiOperation(value = "Получить операцию по идентификатору")
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = OperationDto.class, message = "Получена операция"),
            @ApiResponse(code = 404, response = BankExceptionResponse.class, message = "По данному идентификатору нет операции")
    })
    @GetMapping("/operation/{id}")
    @ResponseBody
    public ResponseEntity<OperationDto> operation(
            @ApiParam(name = "id", value = "Идентификатор операции", example = "1", required = true)
            @PathVariable("id") Integer id) {
        OperationDto operation = operationService.getOperationByIdWithDto(id);
        return new ResponseEntity<>(operation, HttpStatus.OK);
    }

    @ApiOperation(value = "Получить перечень операций по массиву идентификаторов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = OperationDto.class, responseContainer = "List", message = "Получен перечень операций")
    })
    @GetMapping("/operations/{ids}")
    @ResponseBody
    public ResponseEntity<List<OperationDto>> operations(
            @ApiParam(name = "ids", value = "Перечень идентификаторов операций", example = "1", required = true)
            @PathVariable("ids") List<Integer> operationIds) {
        List<OperationDto> operations = operationService.getOperationByIds(operationIds);
        return new ResponseEntity<>(operations, HttpStatus.OK);
    }

    @ApiOperation(value = "Получить перечень операций по идентификатору счёта-источника")
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = OperationDto.class, responseContainer = "List", message = "Получен перечень операций"),
            @ApiResponse(code = 404, response = BankExceptionResponse.class, message = "По данному идентификатору нет банковского счёта")
    })
    @GetMapping("/bank-account/{source_account_id}")
    @ResponseBody
    public ResponseEntity<List<OperationDto>> operations(
            @ApiParam(name = "source_account_id", value = "Идентификатор счёта-источника операции", example = "1", required = true)
            @PathVariable("source_account_id") Integer sourceAccountId) {
        List<OperationDto> operations = operationService.getOperationsBySourceId(sourceAccountId);
        return new ResponseEntity<>(operations, HttpStatus.OK);
    }

    @ApiOperation(value = "Получить историю изменения статусов по операции")
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = OperationDetailsDto.class, responseContainer = "List", message = "Получена история изменения статусов"),
            @ApiResponse(code = 404, response = BankExceptionResponse.class, message = "По данному идентификатору нет операции")
    })
    @GetMapping("/status-history/{id}")
    @ResponseBody
    public ResponseEntity<OperationDetailsDto> statusHistory(
            @ApiParam(name = "id", value = "Идентификатор операции", example = "1", required = true)
            @PathVariable("id") Integer id) {
        OperationDetailsDto statusHistory = operationService.getStatusHistoryByOperationId(id);
        return new ResponseEntity<>(statusHistory, HttpStatus.OK);
    }

    @ApiOperation(value = "Создать банковскую операцию")
    @ApiResponses(value = {
            @ApiResponse(code = 201, response = Integer.class, message = "Банковская операция создана"),
            @ApiResponse(code = 400, response = BankExceptionResponse.class, message = "Все обязательные поля должны быть заполнены"),
            @ApiResponse(code = 403, response = BankExceptionResponse.class, message = "Недостаточно средств для выполнения данной операции"),
            @ApiResponse(code = 404, response = BankExceptionResponse.class, message = "Не найден банковский счёт или тип операции"),
    })
    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<Integer> makeOperation(@RequestBody OperationDto request) {
        Integer operationId = operationService.createOperation(request);
        return new ResponseEntity<>(operationId, HttpStatus.CREATED);
    }
}