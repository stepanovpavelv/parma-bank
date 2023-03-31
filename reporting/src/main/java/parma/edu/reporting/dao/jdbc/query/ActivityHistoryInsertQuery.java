package parma.edu.reporting.dao.jdbc.query;

import lombok.RequiredArgsConstructor;
import parma.edu.reporting.dao.jdbc.enums.ActivityHistoryField;
import parma.edu.reporting.dao.jdbc.enums.ActivityHistoryTable;

import java.util.Map;

@RequiredArgsConstructor
public class ActivityHistoryInsertQuery implements DatabaseQuery {
    private final Map<String, Object> insertParameters;

    @Override
    public String getTemplate() {
        return "INSERT INTO " + ActivityHistoryTable.ACTIVITY_HISTORY.getTable() +
               " (" + prepareQueryFields("") + ") " +
               " VALUES(" + prepareQueryFields(":") + "); ";
    }

    @Override
    public Map<String, Object> getParameters() {
        return insertParameters;
    }

    @Override
    public Object getObject(Map<String, Object> queryForMap) {
        return null;
    }

    private String prepareQueryFields(String prefix) {
        return prefix + ActivityHistoryField.OPERATION_ID.getField() + ", " +
               prefix + ActivityHistoryField.OPERATION_TYPE_ID.getField() + ", " +
               prefix + ActivityHistoryField.USER_SOURCE_ID.getField() + ", " +
               prefix + ActivityHistoryField.USER_SOURCE_LOGIN.getField() + ", " +
               prefix + ActivityHistoryField.USER_TARGET_ID.getField() + ", " +
               prefix + ActivityHistoryField.USER_TARGET_LOGIN.getField() + ", " +
               prefix + ActivityHistoryField.ACCOUNT_SOURCE_ID.getField() + ", " +
               prefix + ActivityHistoryField.ACCOUNT_TARGET_ID.getField() + ", " +
               prefix + ActivityHistoryField.OPERATION_STATUS_ID.getField() + ", " +
               prefix + ActivityHistoryField.DATE.getField() + ", " +
               prefix + ActivityHistoryField.UPDATE_DATE.getField() + ", " +
               prefix + ActivityHistoryField.AMOUNT.getField();
    }
}