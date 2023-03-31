package parma.edu.reporting.dao.jdbc.query;

import lombok.RequiredArgsConstructor;
import parma.edu.reporting.dao.jdbc.enums.BalancingHistoryField;
import parma.edu.reporting.dao.jdbc.enums.BalancingHistoryTable;
import parma.edu.reporting.dao.jdbc.util.QueryUtils;
import parma.edu.reporting.model.BalancingHistory;
import parma.edu.reporting.util.DateUtils;

import java.util.Map;

@RequiredArgsConstructor
public class BalancingHistoryGetQuery implements DatabaseQuery {
    private final Map<String, Object> whereParameters;

    @Override
    public String getTemplate() {
        String template =
                "SELECT " +
                        "  t1." + BalancingHistoryField.ID.getField() + ", " +
                        "  t1." + BalancingHistoryField.BANK_ACCOUNT_ID.getField() + ", " +
                        "  t1." + BalancingHistoryField.USER_ID.getField() + ", " +
                        "  t1." + BalancingHistoryField.USER_LOGIN.getField() + ", " +
                        "  t1." + BalancingHistoryField.DATE.getField() + ", " +
                        "  t1." + BalancingHistoryField.AMOUNT.getField() +
                        " FROM " + BalancingHistoryTable.BALANCING_HISTORY.getTable() + " AS t1 ";

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
    public BalancingHistory getObject(Map<String, Object> queryForMap) {
        BalancingHistory record = new BalancingHistory();
        record.setId((Integer)queryForMap.get(BalancingHistoryField.ID.getField()));
        record.setBankAccountId((Integer)queryForMap.get(BalancingHistoryField.BANK_ACCOUNT_ID.getField()));
        record.setUserId((Integer)queryForMap.get(BalancingHistoryField.USER_ID.getField()));
        record.setUserLogin((String)queryForMap.get(BalancingHistoryField.USER_LOGIN.getField()));
        record.setDate(DateUtils.getZonedDateTimeFromObject(queryForMap.get(BalancingHistoryField.DATE.getField())));
        record.setAmount((Double)queryForMap.get(BalancingHistoryField.AMOUNT.getField()));
        return record;
    }
}