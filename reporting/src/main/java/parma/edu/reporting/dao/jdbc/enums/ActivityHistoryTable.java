package parma.edu.reporting.dao.jdbc.enums;

import lombok.Getter;

/**
 * Таблица `activity_history`.
 */
@Getter
public enum ActivityHistoryTable {
    ACTIVITY_HISTORY("activity_history");

    private final String table;

    ActivityHistoryTable(String table) {
        this.table = table;
    }
}