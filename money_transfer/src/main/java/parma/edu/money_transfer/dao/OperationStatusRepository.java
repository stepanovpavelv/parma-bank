package parma.edu.money_transfer.dao;

import org.springframework.stereotype.Repository;
import parma.edu.money_transfer.model.OperationStatus;

/**
 * Репозиторий для сущности `OperationStatus`.
 */
@Repository
public interface OperationStatusRepository extends BaseJpaRepository<OperationStatus> {
}