package parma.edu.money_transfer.dao;

import org.springframework.stereotype.Repository;
import parma.edu.money_transfer.model.OperationType;

/**
 * Репозиторий для сущности `OperationType`.
 */
@Repository
public interface OperationTypeRepository extends BaseJpaRepository<OperationType> {
}