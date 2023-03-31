package parma.edu.money_transfer.dao;

import org.springframework.stereotype.Repository;
import parma.edu.money_transfer.model.BankAccount;

import java.util.List;

/**
 * Репозиторий для сущности `BankAccount`.
 */
@Repository
public interface BankAccountRepository extends BaseJpaRepository<BankAccount> {
    List<BankAccount> findByUserId(Integer userId);
}