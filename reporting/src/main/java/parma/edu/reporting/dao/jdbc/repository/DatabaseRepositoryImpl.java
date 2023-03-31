package parma.edu.reporting.dao.jdbc.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import parma.edu.reporting.dao.jdbc.query.DatabaseQuery;

import java.util.List;
import java.util.Map;

/**
 * Общий jdbc-репозиторий для всех сущностей БД.
 */
@Repository
@RequiredArgsConstructor
public class DatabaseRepositoryImpl implements DatabaseRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer update(DatabaseQuery query) {
        return namedParameterJdbcTemplate.update(query.getTemplate(), query.getParameters());
    }

    @Override
    public Map<String, Object> queryForMap(DatabaseQuery query) {
        return namedParameterJdbcTemplate.queryForMap(query.getTemplate(), query.getParameters());
    }

    @Override
    public Object queryForObject(DatabaseQuery query) {
        return namedParameterJdbcTemplate.queryForObject(query.getTemplate(), query.getParameters(), query.getObjectClass());
    }

    @Override
    public List<Map<String, Object>> queryForList(DatabaseQuery query) {
        return namedParameterJdbcTemplate.queryForList(query.getTemplate(), query.getParameters());
    }
}