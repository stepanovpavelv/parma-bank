package parma.edu.auth.dao.repository;

import parma.edu.auth.dao.query.DatabaseQuery;

import java.util.List;
import java.util.Map;

/**
 * Основной интерфес поведения jdbc-репозитория.
 */
public interface DatabaseRepository {
    Integer update(DatabaseQuery query);

    Map<String, Object> queryForMap(DatabaseQuery query);

    Object queryForObject(DatabaseQuery query);

    List<Map<String, Object>> queryForList(DatabaseQuery query);
}