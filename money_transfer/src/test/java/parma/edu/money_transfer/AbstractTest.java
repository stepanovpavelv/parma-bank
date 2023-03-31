package parma.edu.money_transfer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import parma.edu.money_transfer.dto.UserInfoDto;
import parma.edu.money_transfer.service.UserService;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Базовый класс юнит-теста.
 */
@EmbeddedKafka
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
public abstract class AbstractTest {
    protected final static long AWAIT_TIMEOUT_SECONDS = 10L;

    @Autowired
    protected EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    protected ObjectMapper mapper;

    @MockBean
    protected UserService userService;

    @Value("${spring.kafka.consumer.group-id}")
    protected String groupId;

    protected BlockingQueue<ConsumerRecord<Integer, String>> records;

    protected KafkaMessageListenerContainer<Integer, String> container;

    protected Map<String, Object> getConsumerProperties() {
        return Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, embeddedKafkaBroker.getBrokersAsString(),
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.GROUP_ID_CONFIG, groupId,
                ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true
        );
    }

    protected void initBaseProperties(String topicName) {
        DefaultKafkaConsumerFactory<Integer, String> consumerFactory = new DefaultKafkaConsumerFactory<>(getConsumerProperties());
        ContainerProperties containerProperties = new ContainerProperties(topicName);
        container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
        records = new LinkedBlockingQueue<>();
        container.setupMessageListener((MessageListener<Integer, String>) records::add);
        container.start();
        ContainerTestUtils.waitForAssignment(container, embeddedKafkaBroker.getPartitionsPerTopic());
    }

    protected void initUserServiceMockObject(Integer userId) {
        Mockito.when(userService.readUserById(userId)).thenReturn(generateTestUserInfo(userId));
    }

    private static UserInfoDto generateTestUserInfo(Integer userId) {
        return UserInfoDto.builder()
                .ok(true)
                .login("test_user_" + userId)
                .enabled(true)
                .role("USER")
                .fullName("Test User " + userId)
                .build();
    }
}