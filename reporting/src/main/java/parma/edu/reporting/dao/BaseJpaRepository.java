package parma.edu.reporting.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Базовый репозиторий для сервиса отчётности.
 * @param <T> - тип сущности.
 */
@NoRepositoryBean
public interface BaseJpaRepository<T> extends JpaRepository<T, Integer> {
}