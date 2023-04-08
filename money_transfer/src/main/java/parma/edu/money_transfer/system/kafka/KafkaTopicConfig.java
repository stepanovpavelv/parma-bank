package parma.edu.money_transfer.system.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Value("${additional-kafka.bank_account.topic}")
    private String accountTopicName;

    @Value("${additional-kafka.operation.topic}")
    private String operationTopicName;

    @Bean
    public NewTopic bankAccountTopic() {
        return TopicBuilder.name(accountTopicName).partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic operationTopic() {
        return TopicBuilder.name(operationTopicName).partitions(1).replicas(1).build();
    }
}