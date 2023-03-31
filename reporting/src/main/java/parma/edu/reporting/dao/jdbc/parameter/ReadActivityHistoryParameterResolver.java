package parma.edu.reporting.dao.jdbc.parameter;

import org.springframework.stereotype.Component;
import parma.edu.reporting.dao.jdbc.enums.ActivityHistoryField;
import parma.edu.reporting.dto.ActivityHistoryRequestDto;
import parma.edu.reporting.util.DateUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Резолвер получения параметров по запросу `ActivityHistoryRequestDto`.
 */
@Component
public class ReadActivityHistoryParameterResolver implements ParameterResolver<ActivityHistoryRequestDto> {

    @Override
    public Map<String, Object> getQueryParameters(ActivityHistoryRequestDto request) {
        if (request == null) {
            return Collections.emptyMap();
        }

        Map<String, Object> whereParams = new HashMap<>();
        if (request.getId() != null) {
            whereParams.put(ActivityHistoryField.ID.getField(), request.getId());
        }
        if (request.getOperationId() != null) {
            whereParams.put(ActivityHistoryField.OPERATION_ID.getField(), request.getOperationId());
        }
        if (request.getOperationTypeId() != null) {
            whereParams.put(ActivityHistoryField.OPERATION_TYPE_ID.getField(), request.getOperationTypeId());
        }
        if (request.getUserSourceId() != null) {
            whereParams.put(ActivityHistoryField.USER_SOURCE_ID.getField(), request.getUserSourceId());
        }
        if (request.getUserSourceLogin() != null) {
            whereParams.put(ActivityHistoryField.USER_SOURCE_LOGIN.getField(), request.getUserSourceLogin());
        }
        if (request.getUserTargetId() != null) {
            whereParams.put(ActivityHistoryField.USER_TARGET_ID.getField(), request.getUserTargetId());
        }
        if (request.getUserTargetLogin() != null) {
            whereParams.put(ActivityHistoryField.USER_TARGET_LOGIN.getField(), request.getUserTargetLogin());
        }
        if (request.getAccountSourceId() != null) {
            whereParams.put(ActivityHistoryField.ACCOUNT_SOURCE_ID.getField(), request.getAccountSourceId());
        }
        if (request.getAccountTargetId() != null) {
            whereParams.put(ActivityHistoryField.ACCOUNT_TARGET_ID.getField(), request.getAccountTargetId());
        }
        if (request.getOperationStatusId() != null) {
            whereParams.put(ActivityHistoryField.OPERATION_STATUS_ID.getField(), request.getOperationStatusId());
        }

        boolean isDateCondition = (request.getStartDate() != null) || (request.getFinishDate() != null);
        if (isDateCondition) {
            whereParams.put(ActivityHistoryField.DATE.getField(), buildBetweenParameters(
                    DateUtils.getOffsetFromZonedDateTime(request.getStartDate() != null ? request.getStartDate() : DateUtils.getTheSmallestDate()),
                    DateUtils.getOffsetFromZonedDateTime(request.getFinishDate() != null ? request.getFinishDate() : DateUtils.getTheBiggestDate())));
        }

        boolean isUpdateDateCondition = (request.getStartUpdateDate() != null) || (request.getFinishUpdateDate() != null);
        if (isUpdateDateCondition) {
            whereParams.put(ActivityHistoryField.UPDATE_DATE.getField(), buildBetweenParameters(
                    DateUtils.getOffsetFromZonedDateTime(request.getStartUpdateDate() != null ? request.getStartUpdateDate() : DateUtils.getTheSmallestDate()),
                    DateUtils.getOffsetFromZonedDateTime(request.getFinishUpdateDate() != null ? request.getFinishUpdateDate() : DateUtils.getTheBiggestDate())));
        }

        boolean isAmountCondition = (request.getStartAmount() != null) || (request.getFinishAmount() != null);
        if (isAmountCondition) {
            whereParams.put(ActivityHistoryField.AMOUNT.getField(), buildBetweenParameters(
                    request.getStartAmount() != null ? request.getStartAmount() : 0,
                    request.getFinishAmount() != null ? request.getFinishAmount() : Double.MAX_VALUE));
        }
        return whereParams;
    }
}