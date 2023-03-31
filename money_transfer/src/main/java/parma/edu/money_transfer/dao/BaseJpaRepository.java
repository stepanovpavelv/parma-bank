package parma.edu.money_transfer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Базовый репозиторий для сервиса банковских переводов.
 * @param <T> - тип сущности.
 */
@NoRepositoryBean
public interface BaseJpaRepository<T> extends JpaRepository<T, Integer> {
}