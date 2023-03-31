package parma.edu.money_transfer.model.enums;

/**
 * Тип выполнения операция (приходно/расходная).
 */
public enum TransactionType {
    /**
     * Расход.
     */
    DEBIT,

    /**
     * Приход.
     */
    INCOMING;

    public static TransactionType getByExpense(boolean isExpense) {
        return isExpense ? DEBIT : INCOMING;
    }
}