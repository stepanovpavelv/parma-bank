package parma.edu.reporting.dao.jdbc.query;

import parma.edu.reporting.dao.jdbc.enums.BalancingHistoryField;
import parma.edu.reporting.dao.jdbc.enums.BalancingHistoryTable;
import parma.edu.reporting.util.DateUtils;

import java.time.ZonedDateTime;
import java.util.Map;

public class BalancingHistoryGetMaxDateQuery implements DatabaseQuery {

    @Override
    public String getTemplate() {
        return " SELECT MAX(" + BalancingHistoryField.DATE.getField() + ") " +
                " AS " + BalancingHistoryField.DATE.getField() +
                " FROM " + BalancingHistoryTable.BALANCING_HISTORY.getTable() + ";";
    }

    @Override
    public Map<String, Object> getParameters() {
        return null;
    }

    @Override
    public ZonedDateTime getObject(Map<String, Object> queryForMap) {
        return DateUtils.getZonedDateTimeFromObject(queryForMap.get(BalancingHistoryField.DATE.getField()));
    }
}