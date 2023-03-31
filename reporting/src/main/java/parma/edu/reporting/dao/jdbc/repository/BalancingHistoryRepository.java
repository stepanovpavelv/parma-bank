package parma.edu.reporting.dao.jdbc.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import parma.edu.reporting.dao.jdbc.parameter.ParameterResolver;
import parma.edu.reporting.dao.jdbc.query.*;
import parma.edu.reporting.dto.BalancingHistoryRequestDto;
import parma.edu.reporting.model.BalancingHistory;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BalancingHistoryRepository {
    private final DatabaseRepository databaseRepository;
    private final ParameterResolver<BalancingHistoryRequestDto> readResolver;
    private final ParameterResolver<BalancingHistory> changeResolver;
    
    public List<BalancingHistory> findBalancingActivities(BalancingHistoryRequestDto dto) {
        Map<String, Object> whereConditions = readResolver.getQueryParameters(dto);
        DatabaseQuery query = new BalancingHistoryGetQuery(whereConditions);
        return databaseRepository.queryForList(query).stream()
                .map(it -> (BalancingHistory)query.getObject(it))
                .collect(Collectors.toList());
    }
    
    public Integer saveBalancingHistory(BalancingHistory history) {
        Map<String, Object> insertParameters = changeResolver.getQueryParameters(history);
        DatabaseQuery query = new BalancingHistoryInsertQuery(insertParameters);
        return databaseRepository.update(query);
    }

    public ZonedDateTime readMaxBalancingUpdateDate() {
        DatabaseQuery query = new BalancingHistoryGetMaxDateQuery();
        ZonedDateTime result = null;
        try {
            result = (ZonedDateTime)query.getObject(databaseRepository.queryForMap(query));
        } catch (EmptyResultDataAccessException ignored) { }
        return result;
    }
}