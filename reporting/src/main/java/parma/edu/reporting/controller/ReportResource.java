package parma.edu.reporting.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import parma.edu.reporting.dto.ActivityHistoryDto;
import parma.edu.reporting.dto.ActivityHistoryRequestDto;
import parma.edu.reporting.dto.BalancingHistoryDto;
import parma.edu.reporting.dto.BalancingHistoryRequestDto;
import parma.edu.reporting.service.ActivityHistoryService;
import parma.edu.reporting.service.BalancingHistoryService;

import java.util.List;

@Api(value = "Методы работы с отчетностью", tags = {"Report controller"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/report")
public class ReportResource {
    private final ActivityHistoryService activityService;
    private final BalancingHistoryService balancingService;

    @ApiOperation(value = "Получить историю выполнения банковских операций")
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = ActivityHistoryDto.class, responseContainer = "List", message = "Получен перечень статистических записей")
    })
    @PostMapping("/activity")
    @ResponseBody
    public ResponseEntity<List<ActivityHistoryDto>> activityHistories(
            @RequestBody
            @ApiParam(name = "request", value = "Запрос истории операций", required = true)
            ActivityHistoryRequestDto request) {
        List<ActivityHistoryDto> activityHistory = activityService.readHistoryStatistics(request);
        return new ResponseEntity<>(activityHistory, HttpStatus.OK);
    }

    @ApiOperation(value = "Получить историю изменения баланса")
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = BalancingHistoryDto.class, responseContainer = "List", message = "Получен перечень статистических записей")
    })
    @PostMapping("/balance")
    @ResponseBody
    public ResponseEntity<List<BalancingHistoryDto>> balancingHistories(
            @RequestBody
            @ApiParam(name = "request", value = "Запрос истории изменения баланса", required = true)
            BalancingHistoryRequestDto request) {
        List<BalancingHistoryDto> balancingHistory = balancingService.readBalancingStatistics(request);
        return new ResponseEntity<>(balancingHistory, HttpStatus.OK);
    }
}