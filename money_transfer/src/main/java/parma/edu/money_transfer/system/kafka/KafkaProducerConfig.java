package parma.edu.money_transfer.system.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;
import parma.edu.money_transfer.dto.BankAccountDto;
import parma.edu.money_transfer.dto.OperationDto;

import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    @Value("${spring.kafka.producer.client-id}")
    private String kafkaClientId;

    @Bean
    public ProducerFactory<Integer, BankAccountDto> producerOperationFactoryAccount() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<Integer, BankAccountDto> kafkaTemplateAccount() {
        KafkaTemplate<Integer, BankAccountDto> template = new KafkaTemplate<>(producerOperationFactoryAccount());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }

    @Bean
    public ProducerFactory<Integer, OperationDto> producerOperationFactoryOperation() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<Integer, OperationDto> kafkaTemplate() {
        KafkaTemplate<Integer, OperationDto> template = new KafkaTemplate<>(producerOperationFactoryOperation());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        return Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class,
                ProducerConfig.CLIENT_ID_CONFIG, kafkaClientId
        );
    }
}