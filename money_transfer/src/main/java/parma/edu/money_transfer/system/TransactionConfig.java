package parma.edu.money_transfer.system;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.persistence.EntityManagerFactory;

@Configuration
public class TransactionConfig {

    @Primary
    @Bean
    public TransactionManager transactionManager(EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }

    @Bean
    public KafkaTransactionManager<Integer, ?> kafkaTransactionManager(ProducerFactory<Integer, ?> factory) {
        return new KafkaTransactionManager<>(factory);
    }
}