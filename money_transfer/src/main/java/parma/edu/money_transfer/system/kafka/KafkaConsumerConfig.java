package parma.edu.money_transfer.system.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.BatchMessagingMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import parma.edu.money_transfer.dto.BankAccountDto;
import parma.edu.money_transfer.dto.OperationDto;

import java.util.Map;

@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    @Value("${spring.kafka.consumer.group-id}")
    private String kafkaGroupId;

    @Value("${spring.kafka.consumer.client-id}")
    private String kafkaClientId;

    @Bean
    public KafkaListenerContainerFactory<?> batchFactoryAccount() {
        ConcurrentKafkaListenerContainerFactory<Integer, BankAccountDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryAccount());
        factory.setBatchListener(true);
        factory.setMessageConverter(new BatchMessagingMessageConverter(converter()));
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> singleFactoryAccount() {
        ConcurrentKafkaListenerContainerFactory<Integer, BankAccountDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryAccount());
        factory.setBatchListener(false);
        factory.setMessageConverter(converter());
        return factory;
    }

    @Bean
    public ConsumerFactory<Integer, BankAccountDto> consumerFactoryAccount() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public KafkaListenerContainerFactory<?> batchFactoryOperation() {
        ConcurrentKafkaListenerContainerFactory<Integer, OperationDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryOperation());
        factory.setBatchListener(true);
        factory.setMessageConverter(new BatchMessagingMessageConverter(converter()));
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> singleFactoryOperation() {
        ConcurrentKafkaListenerContainerFactory<Integer, OperationDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryOperation());
        factory.setBatchListener(false);
        factory.setMessageConverter(converter());
        return factory;
    }

    @Bean
    public ConsumerFactory<Integer, OperationDto> consumerFactoryOperation() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public KafkaListenerContainerFactory<?> kafkaListenerContainerFactory() {
        return new ConcurrentKafkaListenerContainerFactory<>();
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        return Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer,
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId,
                ConsumerConfig.CLIENT_ID_CONFIG, kafkaClientId,
                ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true
        );
    }

    @Bean
    public StringJsonMessageConverter converter() {
        return new StringJsonMessageConverter();
    }
}