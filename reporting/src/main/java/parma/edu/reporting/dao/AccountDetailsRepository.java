package parma.edu.reporting.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import parma.edu.reporting.model.AccountDetails;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Репозиторий для сущности `AccountDetails`.
 */
@Repository
public interface AccountDetailsRepository extends BaseJpaRepository<AccountDetails> {
    @Query(value = "select MIN(details.actualDate) from AccountDetails details")
    ZonedDateTime getActualMinimumDate();

    List<AccountDetails> getAccountDetailsByActualDateBetween(ZonedDateTime start, ZonedDateTime finish);
}