package parma.edu.reporting.dao.jdbc.util;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class QueryUtils {
    public final static String BETWEEN_DELIMITER = "@";

    public static String getTemplateWhere(Map<String, Object> whereParameters) {
        Map<String, Object[]> arrayElementsMap = new HashMap<>();
        Map<String, Object> plainElementsMap = new HashMap<>();
        Map<String, Object[]> betweenElementsMap = new HashMap<>();

        whereParameters.entrySet().forEach(entry -> {
                    if (isBetweenParameters(entry.getValue())) {
                        addArrayParameter(entry, betweenElementsMap);
                    } else if (isCollection(entry.getValue())) {
                        addArrayParameter(entry, arrayElementsMap);
                    } else {
                        plainElementsMap.putIfAbsent(entry.getKey(), entry.getValue());
                    }
                });

        String betweenElementsString = getTemplateInBetweenWherePart(betweenElementsMap);
        String arrayElementsString = getTemplateInListWherePart(arrayElementsMap);
        String plainElementsString = getTemplatePlaneWherePart(plainElementsMap);

        List<String> andWhereParametersList = Stream.of(betweenElementsString, arrayElementsString, plainElementsString)
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.toList());

        return String.join(" AND ", andWhereParametersList);
    }

    private static boolean isBetweenParameters(Object value) {
        if (Objects.nonNull(value) && isCollection(value)) {
            Object[] values = castToArray(value);
            return values != null && values.length == 3 && values[0] == BETWEEN_DELIMITER;
        }
        return false;
    }

    private static boolean isCollection(Object value) {
        return Objects.nonNull(value) && (value.getClass().isArray() || (value instanceof List<?>));
    }

    private static void addArrayParameter(Map.Entry<String, Object> entry, Map<String, Object[]> arrayElementsMap) {
        Object[] arrValue = castToArray(entry.getValue());
        if (arrValue != null && arrValue.length > 0) {
            arrayElementsMap.putIfAbsent(entry.getKey(), arrValue);
        }
    }

    private static Object[] castToArray(Object value) {
        if (value instanceof List<?>) {
            return ((List<?>)value).toArray();
        }

        return (Object[])value;
    }

    /**
     * Плоские параметры
     */
    private static String getTemplatePlaneWherePart(Map<String, Object> whereParameters) {
        return whereParameters.keySet().stream().map(key ->
                " " + key + " = :" + key + " "
        ).collect(Collectors.joining(" AND "));
    }

    /**
     * Параметры "между"
     */
    private static String getTemplateInBetweenWherePart(Map<String, Object[]> whereParameters) {
        return whereParameters.entrySet().stream().map(entry ->
                " " + entry.getKey() + " BETWEEN " + entry.getValue()[1] + " AND " + entry.getValue()[2]
        ).collect(Collectors.joining(" AND "));
    }

    /**
     * Параметры - массивы
     */
    private static String getTemplateInListWherePart(Map<String, Object[]> whereParameters) {
        Map<String, List<String>> indexedArrayParamsStringListMap = new HashMap<>();
        whereParameters.forEach((key, value) -> {
            List<String> indexedArrayParamsStringList = new ArrayList<>();
            indexedArrayParamsStringListMap.putIfAbsent(key, indexedArrayParamsStringList);
            for(int index = 0; index < value.length; index++) {
                indexedArrayParamsStringList.add(":" + key + index);
            }
        });

        return indexedArrayParamsStringListMap.entrySet().stream().map(item ->
                item.getKey() + " IN (" + String.join(",", item.getValue()) + ")"
        ).collect(Collectors.joining(" AND "));
    }
}