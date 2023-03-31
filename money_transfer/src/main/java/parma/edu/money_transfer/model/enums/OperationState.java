package parma.edu.money_transfer.model.enums;

import lombok.Getter;

/**
 * Состояние операции.
 * Маппится 1:1 с типом `OperationStatus`.
 */
@Getter
public enum OperationState {
    CREATED(1),
    PROCESSING(2),
    DONE(3),
    CANCELLED(4);

    private final int id;

    OperationState(int id) {
        this.id = id;
    }
}