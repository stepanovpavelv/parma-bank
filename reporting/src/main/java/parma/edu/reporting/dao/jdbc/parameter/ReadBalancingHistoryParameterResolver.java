package parma.edu.reporting.dao.jdbc.parameter;

import org.springframework.stereotype.Component;
import parma.edu.reporting.dao.jdbc.enums.BalancingHistoryField;
import parma.edu.reporting.dao.jdbc.util.QueryUtils;
import parma.edu.reporting.dto.BalancingHistoryRequestDto;
import parma.edu.reporting.util.DateUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class ReadBalancingHistoryParameterResolver implements ParameterResolver<BalancingHistoryRequestDto> {
    @Override
    public Map<String, Object> getQueryParameters(BalancingHistoryRequestDto request) {
        if (request == null) {
            return Collections.emptyMap();
        }

        Map<String, Object> whereParams = new HashMap<>();
        if (request.getId() != null) {
            whereParams.put(BalancingHistoryField.ID.getField(), request.getId());
        }
        if (request.getAccountId() != null) {
            whereParams.put(BalancingHistoryField.BANK_ACCOUNT_ID.getField(), request.getAccountId());
        }
        if (request.getUserId() != null) {
            whereParams.put(BalancingHistoryField.USER_ID.getField(), request.getUserId());
        }
        if (request.getUserLogin() != null) {
            whereParams.put(BalancingHistoryField.USER_LOGIN.getField(), request.getUserLogin());
        }

        boolean isDateCondition = (request.getStartDate() != null) || (request.getFinishDate() != null);
        if (isDateCondition) {
            whereParams.put(BalancingHistoryField.DATE.getField(), buildBetweenParameters(
                    DateUtils.getOffsetFromZonedDateTime(request.getStartDate() != null ? request.getStartDate() : DateUtils.getTheSmallestDate()),
                    DateUtils.getOffsetFromZonedDateTime(request.getFinishDate() != null ? request.getFinishDate() : DateUtils.getTheBiggestDate())));
        }

        boolean isAmountCondition = (request.getStartAmount() != null) || (request.getFinishAmount() != null);
        if (isAmountCondition) {
            whereParams.put(BalancingHistoryField.AMOUNT.getField(), buildBetweenParameters(
                    request.getStartAmount() != null ? request.getStartAmount() : 0,
                    request.getFinishAmount() != null ? request.getFinishAmount() : Double.MAX_VALUE));
        }

        return whereParams;
    }
}