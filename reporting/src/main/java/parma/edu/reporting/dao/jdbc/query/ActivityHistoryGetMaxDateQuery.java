package parma.edu.reporting.dao.jdbc.query;

import parma.edu.reporting.dao.jdbc.enums.ActivityHistoryField;
import parma.edu.reporting.dao.jdbc.enums.ActivityHistoryTable;
import parma.edu.reporting.util.DateUtils;

import java.time.ZonedDateTime;
import java.util.Map;

public class ActivityHistoryGetMaxDateQuery implements DatabaseQuery {
    @Override
    public String getTemplate() {
        return " SELECT MAX(" + ActivityHistoryField.UPDATE_DATE.getField() + ") " +
               " AS " + ActivityHistoryField.UPDATE_DATE.getField() +
               " FROM " + ActivityHistoryTable.ACTIVITY_HISTORY.getTable() + ";";
    }

    @Override
    public Map<String, Object> getParameters() {
        return null;
    }

    @Override
    public ZonedDateTime getObject(Map<String, Object> queryForMap) {
        return DateUtils.getZonedDateTimeFromObject(queryForMap.get(ActivityHistoryField.UPDATE_DATE.getField()));
    }
}