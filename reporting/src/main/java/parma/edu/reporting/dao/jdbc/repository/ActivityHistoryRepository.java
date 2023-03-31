package parma.edu.reporting.dao.jdbc.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import parma.edu.reporting.dao.jdbc.parameter.ParameterResolver;
import parma.edu.reporting.dao.jdbc.query.*;
import parma.edu.reporting.dto.ActivityHistoryRequestDto;
import parma.edu.reporting.model.ActivityHistory;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ActivityHistoryRepository {
    private final DatabaseRepository databaseRepository;
    private final ParameterResolver<ActivityHistoryRequestDto> readResolver;
    private final ParameterResolver<ActivityHistory> changeResolver;

    public List<ActivityHistory> findActivityHistories(ActivityHistoryRequestDto dto) {
        Map<String, Object> whereConditions = readResolver.getQueryParameters(dto);
        DatabaseQuery query = new ActivityHistoryGetQuery(whereConditions);
        return databaseRepository.queryForList(query).stream()
                .map(it -> (ActivityHistory)query.getObject(it))
                .collect(Collectors.toList());
    }

    public Integer saveActivityHistory(ActivityHistory activity) {
        Map<String, Object> modifyConditions = changeResolver.getQueryParameters(activity);

        DatabaseQuery modifyQuery;
        ActivityHistoryRequestDto selectRequest = makeWhereRequestByOperationId(activity.getOperationId());
        List<ActivityHistory> activityHistories = findActivityHistories(selectRequest);
        if (activityHistories.isEmpty()) {
            modifyQuery = new ActivityHistoryInsertQuery(modifyConditions);
        } else {
            modifyQuery = new ActivityHistoryUpdateQuery(modifyConditions);
        }

        return databaseRepository.update(modifyQuery);
    }

    public ZonedDateTime readMaxHistoryUpdateDate() {
        DatabaseQuery query = new ActivityHistoryGetMaxDateQuery();
        ZonedDateTime result = null;
        try {
            result = (ZonedDateTime)query.getObject(databaseRepository.queryForMap(query));
        } catch (EmptyResultDataAccessException ignored) { }
        return result;
    }

    private ActivityHistoryRequestDto makeWhereRequestByOperationId(Integer operationId) {
        ActivityHistoryRequestDto whereDto = new ActivityHistoryRequestDto();
        whereDto.setOperationId(operationId);
        return whereDto;
    }
}