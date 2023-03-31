package parma.edu.reporting.dao.jdbc.enums;

import lombok.Getter;

/**
 * Таблица `balancing_history`.
 */
@Getter
public enum BalancingHistoryTable {
    BALANCING_HISTORY("balancing_history");

    private final String table;

    BalancingHistoryTable(String table) {
        this.table = table;
    }
}