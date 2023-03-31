package parma.edu.money_transfer.dao;

import org.springframework.stereotype.Repository;
import parma.edu.money_transfer.model.Operation;

import java.util.List;

/**
 * Репозиторий для сущности `Operation`.
 */
@Repository
public interface OperationRepository extends BaseJpaRepository<Operation> {
    List<Operation> getOperationsByAccountSourceId(Integer accountSourceId);
}