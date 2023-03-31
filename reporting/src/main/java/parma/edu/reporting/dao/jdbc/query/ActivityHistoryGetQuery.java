package parma.edu.reporting.dao.jdbc.query;

import lombok.RequiredArgsConstructor;
import parma.edu.reporting.dao.jdbc.enums.ActivityHistoryField;
import parma.edu.reporting.dao.jdbc.enums.ActivityHistoryTable;
import parma.edu.reporting.dao.jdbc.util.QueryUtils;
import parma.edu.reporting.model.ActivityHistory;
import parma.edu.reporting.util.DateUtils;

import java.util.Map;

@RequiredArgsConstructor
public class ActivityHistoryGetQuery implements DatabaseQuery {
    private final Map<String, Object> whereParameters;

    @Override
    public String getTemplate() {
        String template =
                "SELECT " +
                "  t1." + ActivityHistoryField.ID.getField() + ", " +
                "  t1." + ActivityHistoryField.OPERATION_ID.getField() + ", " +
                "  t1." + ActivityHistoryField.OPERATION_TYPE_ID.getField() + ", " +
                "  t1." + ActivityHistoryField.USER_SOURCE_ID.getField() + ", " +
                "  t1." + ActivityHistoryField.USER_SOURCE_LOGIN.getField() + ", " +
                "  t1." + ActivityHistoryField.USER_TARGET_ID.getField() + ", " +
                "  t1." + ActivityHistoryField.USER_TARGET_LOGIN.getField() + ", " +
                "  t1." + ActivityHistoryField.ACCOUNT_SOURCE_ID.getField() + ", " +
                "  t1." + ActivityHistoryField.ACCOUNT_TARGET_ID.getField() + ", " +
                "  t1." + ActivityHistoryField.OPERATION_STATUS_ID.getField() + ", " +
                "  t1." + ActivityHistoryField.DATE.getField() + ", " +
                "  t1." + ActivityHistoryField.UPDATE_DATE.getField() + ", " +
                "  t1." + ActivityHistoryField.AMOUNT.getField() +
                " FROM " + ActivityHistoryTable.ACTIVITY_HISTORY.getTable() + " AS t1 ";

        if (!whereParameters.isEmpty()) {
            template += " WHERE " + QueryUtils.getTemplateWhere(whereParameters) + ";";
        }

        return template;
    }

    @Override
    public Map<String, Object> getParameters() {
        return whereParameters;
    }

    @Override
    public ActivityHistory getObject(Map<String, Object> queryForMap) {
        ActivityHistory record = new ActivityHistory();
        record.setId((Integer)queryForMap.get(ActivityHistoryField.ID.getField()));
        record.setOperationId((Integer)queryForMap.get(ActivityHistoryField.OPERATION_ID.getField()));
        record.setOperationTypeId((Integer)queryForMap.get(ActivityHistoryField.OPERATION_TYPE_ID.getField()));
        record.setUserSourceId((Integer)queryForMap.get(ActivityHistoryField.USER_SOURCE_ID.getField()));
        record.setUserSourceLogin((String)queryForMap.get(ActivityHistoryField.USER_SOURCE_LOGIN.getField()));
        record.setUserTargetId((Integer)queryForMap.get(ActivityHistoryField.USER_TARGET_ID.getField()));
        record.setUserTargetLogin((String)queryForMap.get(ActivityHistoryField.USER_TARGET_LOGIN.getField()));
        record.setAccountSourceId((Integer)queryForMap.get(ActivityHistoryField.ACCOUNT_SOURCE_ID.getField()));
        record.setAccountTargetId((Integer)queryForMap.get(ActivityHistoryField.ACCOUNT_TARGET_ID.getField()));
        record.setOperationStatusId((Integer)queryForMap.get(ActivityHistoryField.OPERATION_STATUS_ID.getField()));
        record.setDate(DateUtils.getZonedDateTimeFromObject(queryForMap.get(ActivityHistoryField.DATE.getField())));
        record.setUpdateDate(DateUtils.getZonedDateTimeFromObject(queryForMap.get(ActivityHistoryField.UPDATE_DATE.getField())));
        record.setAmount((Double)queryForMap.get(ActivityHistoryField.AMOUNT.getField()));
        return record;
    }
}