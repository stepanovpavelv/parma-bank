package parma.edu.reporting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import parma.edu.reporting.dto.BalancingHistoryDto;
import parma.edu.reporting.dto.BalancingHistoryRequestDto;
import parma.edu.reporting.service.BalancingHistoryService;
import parma.edu.reporting.service.StatisticsAccumulatorService;

import java.time.Duration;
import java.util.List;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@Tag("balancing-histories")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "classpath:db/scripts/create-operations.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "classpath:db/scripts/clear-tables.sql")
@ActiveProfiles("test")
public class BalancingHistoryUnitTest extends AbstractTest {

    @SpyBean
    private StatisticsAccumulatorService accumulatorService;

    @Autowired
    private BalancingHistoryService balancingService;

    @Test
    public void shouldScheduled_thenBalancingRecordsOk() {
        final Integer userId = 1;

        this.initAllMocks();

        await()
                .atMost(Duration.ofSeconds(scheduleInterval))
                .untilAsserted(() -> verify(accumulatorService, atLeast(1)).buildBalancingHistories());

        BalancingHistoryRequestDto request = new BalancingHistoryRequestDto();
        request.setUserId(userId);

        List<BalancingHistoryDto> histories = balancingService.readBalancingStatistics(request);
        Assertions.assertNotNull(histories);
        Assertions.assertFalse(histories.isEmpty());
        Assertions.assertEquals(userId, histories.get(0).getUserId());
    }
}