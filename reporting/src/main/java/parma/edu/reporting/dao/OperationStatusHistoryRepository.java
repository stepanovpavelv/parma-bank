package parma.edu.reporting.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import parma.edu.reporting.model.OperationStatusHistory;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface OperationStatusHistoryRepository extends BaseJpaRepository<OperationStatusHistory> {
    @Query(value = "select oph1 from OperationStatusHistory oph1 " +
            " where oph1.date between :start and :finish " +
            "   and oph1.id = (select MAX(oph2.id) from OperationStatusHistory oph2 where oph1.operation.id = oph2.operation.id)")
    List<OperationStatusHistory> getOperationStatusHistoriesByDateBetween(ZonedDateTime start, ZonedDateTime finish);
}