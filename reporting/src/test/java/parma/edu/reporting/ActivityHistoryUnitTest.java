package parma.edu.reporting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import parma.edu.reporting.dto.ActivityHistoryDto;
import parma.edu.reporting.dto.ActivityHistoryRequestDto;
import parma.edu.reporting.service.ActivityHistoryService;
import parma.edu.reporting.service.StatisticsAccumulatorService;

import java.time.Duration;
import java.util.List;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@Tag("activity-histories")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "classpath:db/scripts/create-operations.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "classpath:db/scripts/clear-tables.sql")
@ActiveProfiles("test")
public class ActivityHistoryUnitTest extends AbstractTest {

    @SpyBean
    private StatisticsAccumulatorService accumulatorService;

    @Autowired
    private ActivityHistoryService activityService;

    @Test
    public void shouldScheduled_thenActivityRecordsOk() {
        final Integer activityId = 1;

        this.initAllMocks();

        await()
                .atMost(Duration.ofSeconds(scheduleInterval))
                .untilAsserted(() -> verify(accumulatorService, atLeast(2)).buildOperationActivities());

        ActivityHistoryRequestDto request = new ActivityHistoryRequestDto();
        request.setId(activityId);

        List<ActivityHistoryDto> records = activityService.readHistoryStatistics(request);
        Assertions.assertNotNull(records);
        Assertions.assertFalse(records.isEmpty());
        Assertions.assertEquals(activityId, records.get(0).getId());
    }
}