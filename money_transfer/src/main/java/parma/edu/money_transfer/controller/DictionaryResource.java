package parma.edu.money_transfer.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import parma.edu.money_transfer.dto.OperationStatusDto;
import parma.edu.money_transfer.dto.OperationTypeDto;
import parma.edu.money_transfer.exception.BankExceptionResponse;
import parma.edu.money_transfer.service.DictionaryService;

import java.util.List;

@Api(value = "Методы по получению справочной информации", tags = {"Dictionary controller"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dictionary")
public class DictionaryResource {
    private final DictionaryService dictionaryService;

    @ApiOperation(value = "Получить все типы банковских операций")
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = OperationTypeDto.class, responseContainer = "List", message = "Получены типы банковских операций")
    })
    @GetMapping("/all/operation-type")
    @ResponseBody
    public ResponseEntity<List<OperationTypeDto>> operationTypes() {
        List<OperationTypeDto> items = dictionaryService.getAllTypes();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @ApiOperation(value = "Получить все статусы банковских операций")
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = OperationStatusDto.class, responseContainer = "List", message = "Получены банковские статусы")
    })
    @GetMapping("/all/operation-status")
    @ResponseBody
    public ResponseEntity<List<OperationStatusDto>> operationStatuses() {
        List<OperationStatusDto> items = dictionaryService.getAllStatuses();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @ApiOperation(value = "Получить тип банковской операции по идентификатору")
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = OperationTypeDto.class, message = "Получен тип банковской операции"),
            @ApiResponse(code = 404, response = BankExceptionResponse.class, message = "По данному идентификатору нет типа банковской операции")
    })
    @GetMapping("/operation-type/{id}")
    @ResponseBody
    public ResponseEntity<OperationTypeDto> operationType(
            @ApiParam(name = "id", value = "Идентификатор типа операции", example = "1", required = true)
            @PathVariable("id") Integer id) {
        OperationTypeDto item = dictionaryService.getTypeByIdWithDto(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @ApiOperation(value = "Получить статус банковской операции по идентификатору")
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = OperationStatusDto.class, message = "Получен статус банковской операции"),
            @ApiResponse(code = 404, response = BankExceptionResponse.class, message = "По данному идентификатору нет статуса банковской операции")
    })
    @GetMapping("/operation-status/{id}")
    @ResponseBody
    public ResponseEntity<OperationStatusDto> operationStatus(
            @ApiParam(name = "id", value = "Идентификатор статуса", example = "1", required = true)
            @PathVariable("id") Integer id) {
        OperationStatusDto item = dictionaryService.getStatusByIdWithDto(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
}