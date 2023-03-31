package parma.edu.reporting.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parma.edu.reporting.dao.AccountDetailsRepository;
import parma.edu.reporting.dao.OperationRepository;
import parma.edu.reporting.dao.OperationStatusHistoryRepository;
import parma.edu.reporting.dto.ActivityHistoryDto;
import parma.edu.reporting.dto.BalancingHistoryDto;
import parma.edu.reporting.dto.UserInfoDto;
import parma.edu.reporting.model.AccountDetails;
import parma.edu.reporting.model.Operation;
import parma.edu.reporting.model.OperationStatusHistory;
import parma.edu.reporting.util.DateUtils;

import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsAccumulatorService {
    private final ActivityHistoryService activityHistoryService;
    private final BalancingHistoryService balancingHistoryService;
    private final OperationRepository operationRepository;
    private final OperationStatusHistoryRepository operationStatusHistoryRepository;
    private final AccountDetailsRepository accountDetailsRepository;
    private final UserService userService;

    @Transactional
    @Scheduled(cron = "${spring.accumulator.settings.activity.schedule}")
    public void buildOperationActivities() {
        ZonedDateTime curDate = DateUtils.getCurrentTime();
        ZonedDateTime maxSavedDate = calculateMinimumDateOfActivityHistory();
        if (maxSavedDate == null) {
            return;
        }

        List<Operation> operations = operationRepository.getOperationsByUpdateDateBetween(maxSavedDate, curDate);
        if (operations.isEmpty()) {
            return;
        }

        log.info("Activities. Received new {} operations. Starting loading: {}", operations.size(), curDate);
        operations.forEach(operation -> {
            ActivityHistoryDto dto = makeActivityHistoryRecord(operation);
            activityHistoryService.saveHistoryStatistics(dto);
        });
        log.info("Activities. Finish loading: {}", DateUtils.getCurrentTime());
    }

    @Transactional
    @Scheduled(cron = "${spring.accumulator.settings.statuses.schedule}")
    public void buildOperationStatuses() {
        ZonedDateTime curDate = DateUtils.getCurrentTime();
        ZonedDateTime maxSavedDate = calculateMinimumDateOfActivityHistory();
        if (maxSavedDate == null) {
            return;
        }

        List<OperationStatusHistory> statuses = operationStatusHistoryRepository.getOperationStatusHistoriesByDateBetween(maxSavedDate, curDate);
        if (statuses.isEmpty()) {
            return;
        }

        log.info("Statuses. Received new {} operation statuses. Starting loading: {}", statuses.size(), curDate);
        statuses.forEach(status -> {
            ActivityHistoryDto dto = makeActivityHistoryRecord(status.getOperation());
            dto.setUpdateDate(status.getDate());
            dto.setOperationStatusId(status.getStatus().getId());
            activityHistoryService.saveHistoryStatistics(dto);
        });
        log.info("Statuses. Finish loading: {}", DateUtils.getCurrentTime());
    }

    @Transactional
    @Scheduled(cron = "${spring.accumulator.settings.balancing.schedule}")
    public void buildBalancingHistories() {
        ZonedDateTime curDate = DateUtils.getCurrentTime();
        ZonedDateTime maxSavedDate = calculateMinimumDateOfBalancingHistory();
        if (maxSavedDate == null) {
            return;
        }

        List<AccountDetails> actualDetails = accountDetailsRepository.getAccountDetailsByActualDateBetween(maxSavedDate, curDate);
        if (actualDetails.isEmpty()) {
            return;
        }

        log.info("Balancing. Received new {} account details. Starting loading: {}", actualDetails.size(), curDate);
        actualDetails.forEach(details -> {
            BalancingHistoryDto dto = makeBalancingHistoryRecord(details);
            balancingHistoryService.saveBalancingStatistics(dto);
        });
        log.info("Balancing. Finish loading: {}", DateUtils.getCurrentTime());
    }

    private ZonedDateTime calculateMinimumDateOfActivityHistory() {
        ZonedDateTime maxSavedDate = activityHistoryService.readCalculatedMaxDate();
        if (maxSavedDate == null) {
            maxSavedDate = operationRepository.getOperationMinimumDate();
        } else {
            maxSavedDate = maxSavedDate.plusSeconds(1);
        }
        return maxSavedDate;
    }

    private ZonedDateTime calculateMinimumDateOfBalancingHistory() {
        ZonedDateTime maxSavedDate = balancingHistoryService.readCalculatedMaxDate();
        if (maxSavedDate == null) {
            maxSavedDate = accountDetailsRepository.getActualMinimumDate();
        } else {
            maxSavedDate = maxSavedDate.plusSeconds(1);
        }
        return maxSavedDate;
    }

    private String getUserName(Integer id) {
        UserInfoDto dto = userService.readUserById(id);
        return dto != null ? dto.getLogin() : "undefined";
    }

    private ActivityHistoryDto makeActivityHistoryRecord(Operation operation) {
        ActivityHistoryDto dto = new ActivityHistoryDto();
        dto.setOperationId(operation.getId());
        dto.setOperationTypeId(operation.getType().getId());
        dto.setUserSourceId(operation.getAccountSource().getUserId());
        dto.setUserSourceLogin(this.getUserName(operation.getAccountSource().getUserId()));
        dto.setAccountSourceId(operation.getAccountSource().getId());
        if (operation.getAccountTarget() != null) {
            dto.setUserTargetId(operation.getAccountTarget().getUserId());
            dto.setUserTargetLogin(this.getUserName(operation.getAccountTarget().getUserId()));
            dto.setAccountTargetId(operation.getAccountTarget().getId());
        }
        dto.setDate(operation.getDate());
        dto.setUpdateDate(operation.getUpdateDate());
        dto.setAmount(operation.getAmount());
        return dto;
    }

    private BalancingHistoryDto makeBalancingHistoryRecord(AccountDetails details) {
        BalancingHistoryDto dto = new BalancingHistoryDto();
        dto.setAccountId(details.getBankAccount().getId());
        dto.setUserId(details.getBankAccount().getUserId());
        dto.setUserLogin(this.getUserName(details.getBankAccount().getUserId()));
        dto.setDate(details.getActualDate());
        dto.setAmount(details.getAmount());
        return dto;
    }
}