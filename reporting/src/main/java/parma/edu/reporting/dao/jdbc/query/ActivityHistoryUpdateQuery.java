package parma.edu.reporting.dao.jdbc.query;

import lombok.RequiredArgsConstructor;
import parma.edu.reporting.dao.jdbc.enums.ActivityHistoryField;
import parma.edu.reporting.dao.jdbc.enums.ActivityHistoryTable;
import parma.edu.reporting.dao.jdbc.util.QueryUtils;

import java.util.Map;

@RequiredArgsConstructor
public class ActivityHistoryUpdateQuery implements DatabaseQuery {
    private final Map<String, Object> updateParameters;

    @Override
    public String getTemplate() {
        String template =
                " UPDATE " + ActivityHistoryTable.ACTIVITY_HISTORY.getTable() + " " +
                " SET " + prepareSetQueryFields();

        if (!updateParameters.isEmpty()) {
            template += " WHERE " + QueryUtils.getTemplateWhere(updateParameters);
        }

        return template;
    }

    @Override
    public Map<String, Object> getParameters() {
        return updateParameters;
    }

    @Override
    public Object getObject(Map<String, Object> queryForMap) {
        return null;
    }

    private String prepareSetQueryFields() {
        return prepareSetQueryField(ActivityHistoryField.OPERATION_TYPE_ID.getField()) + ", " +
               prepareSetQueryField(ActivityHistoryField.USER_SOURCE_ID.getField()) + ", " +
               prepareSetQueryField(ActivityHistoryField.USER_SOURCE_LOGIN.getField()) + ", " +
               prepareSetQueryField(ActivityHistoryField.USER_TARGET_ID.getField()) + ", " +
               prepareSetQueryField(ActivityHistoryField.USER_TARGET_LOGIN.getField()) + ", " +
               prepareSetQueryField(ActivityHistoryField.ACCOUNT_SOURCE_ID.getField()) + ", " +
               prepareSetQueryField(ActivityHistoryField.ACCOUNT_TARGET_ID.getField()) + ", " +
               prepareSetQueryField(ActivityHistoryField.OPERATION_STATUS_ID.getField()) + ", " +
               prepareSetQueryField(ActivityHistoryField.DATE.getField()) + ", " +
               prepareSetQueryField(ActivityHistoryField.UPDATE_DATE.getField()) + ", " +
               prepareSetQueryField(ActivityHistoryField.AMOUNT.getField()) + " ";
    }

    private String prepareSetQueryField(String field) {
        return field + " = :" + field;
    }
}