package parma.edu.auth.dao.enums;

import lombok.Getter;

/**
 * Таблица `users`
 */
@Getter
public enum UserTable {
    USERS("users");

    private final String table;

    UserTable(String table) {
        this.table = table;
    }
}