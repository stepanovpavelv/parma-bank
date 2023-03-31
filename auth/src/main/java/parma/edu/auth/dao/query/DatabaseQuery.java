package parma.edu.auth.dao.query;

import java.util.Map;

/**
 * Интерфейс запроса.
 */
public interface DatabaseQuery {
    String getTemplate();

    Map<String, Object> getParameters();

    default Class<Object> getObjectClass() {
        return Object.class;
    }

    Object getObject(Map<String, Object> queryForMap);
}