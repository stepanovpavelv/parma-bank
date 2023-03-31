package parma.edu.reporting.dao.jdbc.parameter;

import org.springframework.stereotype.Component;
import parma.edu.reporting.dao.jdbc.enums.BalancingHistoryField;
import parma.edu.reporting.model.BalancingHistory;
import parma.edu.reporting.util.DateUtils;

import java.util.HashMap;
import java.util.Map;

@Component
public class AddBalancingHistoryParameterResolver implements ParameterResolver<BalancingHistory> {

    @Override
    public Map<String, Object> getQueryParameters(BalancingHistory request) {
        return new HashMap<>() {{
            put(BalancingHistoryField.BANK_ACCOUNT_ID.getField(), request.getBankAccountId());
            put(BalancingHistoryField.USER_ID.getField(), request.getUserId());
            put(BalancingHistoryField.USER_LOGIN.getField(), request.getUserLogin());
            put(BalancingHistoryField.DATE.getField(), DateUtils.getOffsetFromZonedDateTime(request.getDate()));
            put(BalancingHistoryField.AMOUNT.getField(), request.getAmount());
        }};
    }
}