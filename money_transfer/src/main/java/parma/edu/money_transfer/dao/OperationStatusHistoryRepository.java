package parma.edu.money_transfer.dao;

import org.springframework.stereotype.Repository;
import parma.edu.money_transfer.model.OperationStatusHistory;

import java.util.List;

/**
 * Репозиторий для сущности `OperationStatusHistory`.
 */
@Repository
public interface OperationStatusHistoryRepository extends BaseJpaRepository<OperationStatusHistory> {
    List<OperationStatusHistory> findOperationStatusHistoriesByOperationId(Integer operation_id);
}