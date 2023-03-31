package parma.edu.reporting.dao.jdbc.enums;

import lombok.Getter;

/**
 * Перечень полей таблицы `activity_history`.
 */
@Getter
public enum ActivityHistoryField {
    ID("id"),
    OPERATION_ID("operation_id"),
    OPERATION_TYPE_ID("operation_type_id"),
    USER_SOURCE_ID("user_source_id"),
    USER_SOURCE_LOGIN("user_source_login"),
    USER_TARGET_ID("user_target_id"),
    USER_TARGET_LOGIN("user_target_login"),
    ACCOUNT_SOURCE_ID("account_source_id"),
    ACCOUNT_TARGET_ID("account_target_id"),
    DATE("date"),
    UPDATE_DATE("update_date"),
    AMOUNT("amount"),
    OPERATION_STATUS_ID("operation_status_id");

    private final String field;

    ActivityHistoryField(String field) {
        this.field = field;
    }
}