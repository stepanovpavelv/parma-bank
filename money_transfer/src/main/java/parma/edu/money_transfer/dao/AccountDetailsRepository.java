package parma.edu.money_transfer.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import parma.edu.money_transfer.model.AccountDetails;

import java.util.List;

/**
 * Репозиторий для сущности `AccountDetails`.
 */
@Repository
public interface AccountDetailsRepository extends BaseJpaRepository<AccountDetails> {
    @Query("select details from AccountDetails as details where details.bankAccount.id = :bank_account_id")
    List<AccountDetails> findAccountDetailsByBankAccountId(@Param("bank_account_id") Integer bankAccountId);
}