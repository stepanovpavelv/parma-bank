package parma.edu.reporting.dao.jdbc.enums;

import lombok.Getter;

/**
 * Перечень полей таблицы `balancing_history`.
 */
@Getter
public enum BalancingHistoryField {
    ID("id"),
    BANK_ACCOUNT_ID("bank_account_id"),
    USER_ID("user_id"),
    USER_LOGIN("user_login"),
    DATE("date"),
    AMOUNT("amount");

    private final String field;

    BalancingHistoryField(String field) {
        this.field = field;
    }
}