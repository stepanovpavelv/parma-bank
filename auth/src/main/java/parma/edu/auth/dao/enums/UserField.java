package parma.edu.auth.dao.enums;

import lombok.Getter;

/**
 * Перечень полей таблицы `users`.
 */
@Getter
public enum UserField {
    ID("id"),
    LOGIN("login"),
    PASSWORD("password"),
    FULL_NAME("full_name"),
    ROLE("role"),
    IS_ENABLED("is_enabled"),
    LOCKED("locked");

    private final String field;

    UserField(String field) {
        this.field = field;
    }
}
