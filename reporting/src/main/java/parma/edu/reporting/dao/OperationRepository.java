package parma.edu.reporting.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import parma.edu.reporting.model.Operation;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Репозиторий для сущности `Operation`.
 */
@Repository
public interface OperationRepository extends BaseJpaRepository<Operation> {
    @Query(value = "select MIN(op.updateDate) from Operation op")
    ZonedDateTime getOperationMinimumDate();

    List<Operation> getOperationsByUpdateDateBetween(ZonedDateTime start, ZonedDateTime finish);
}