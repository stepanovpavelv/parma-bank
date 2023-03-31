package parma.edu.reporting.dao.jdbc.query;

import lombok.RequiredArgsConstructor;
import parma.edu.reporting.dao.jdbc.enums.BalancingHistoryField;
import parma.edu.reporting.dao.jdbc.enums.BalancingHistoryTable;

import java.util.Map;

@RequiredArgsConstructor
public class BalancingHistoryInsertQuery implements DatabaseQuery {
    private final Map<String, Object> insertParameters;

    @Override
    public String getTemplate() {
        return "INSERT INTO " + BalancingHistoryTable.BALANCING_HISTORY.getTable() + " (" +
                prepareQueryFields("") + ") VALUES (" +
                prepareQueryFields(":") + ");"; // RETURNING " + BalancingHistoryField.ID.getField();
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
        return prefix + BalancingHistoryField.BANK_ACCOUNT_ID.getField() + ", " +
               prefix + BalancingHistoryField.USER_ID.getField() + ", " +
               prefix + BalancingHistoryField.USER_LOGIN.getField() + ", " +
               prefix + BalancingHistoryField.DATE.getField() + ", " +
               prefix + BalancingHistoryField.AMOUNT.getField();
    }
}