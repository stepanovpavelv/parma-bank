package parma.edu.auth.dao.parameter;

import java.util.Map;

/**
 * Интерфейс поведения сервиса получения параметров по запросу.
 * @param <T> - тип запроса.
 */
public interface ParameterResolver<T> {
    Map<String, Object> getQueryParameters(T request);

    default <V> Object[] buildBetweenParameters(V param1, V param2) {
        return new Object[] { "@", param1, param2 };
    }
}