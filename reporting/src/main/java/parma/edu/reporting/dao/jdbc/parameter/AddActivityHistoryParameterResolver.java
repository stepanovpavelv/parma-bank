package parma.edu.reporting.dao.jdbc.parameter;

import org.springframework.stereotype.Component;
import parma.edu.reporting.dao.jdbc.enums.ActivityHistoryField;
import parma.edu.reporting.model.ActivityHistory;
import parma.edu.reporting.util.DateUtils;

import java.util.HashMap;
import java.util.Map;

@Component
public class AddActivityHistoryParameterResolver implements ParameterResolver<ActivityHistory> {
    @Override
    public Map<String, Object> getQueryParameters(ActivityHistory request) {
        return new HashMap<>() {{
           put(ActivityHistoryField.OPERATION_ID.getField(), request.getOperationId());
           put(ActivityHistoryField.OPERATION_TYPE_ID.getField(), request.getOperationTypeId());
           put(ActivityHistoryField.USER_SOURCE_ID.getField(), request.getUserSourceId());
           put(ActivityHistoryField.USER_SOURCE_LOGIN.getField(), request.getUserSourceLogin());
           put(ActivityHistoryField.USER_TARGET_ID.getField(), request.getUserTargetId());
           put(ActivityHistoryField.USER_TARGET_LOGIN.getField(), request.getUserTargetLogin());
           put(ActivityHistoryField.ACCOUNT_SOURCE_ID.getField(), request.getAccountSourceId());
           put(ActivityHistoryField.ACCOUNT_TARGET_ID.getField(), request.getAccountTargetId());
           put(ActivityHistoryField.OPERATION_STATUS_ID.getField(), request.getOperationStatusId());
           put(ActivityHistoryField.DATE.getField(), DateUtils.getOffsetFromZonedDateTime(request.getDate()));
           put(ActivityHistoryField.UPDATE_DATE.getField(), DateUtils.getOffsetFromZonedDateTime(request.getUpdateDate()));
           put(ActivityHistoryField.AMOUNT.getField(), request.getAmount());
        }};
    }
}