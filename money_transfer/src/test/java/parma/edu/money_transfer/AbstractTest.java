package parma.edu.money_transfer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.test.context.EmbeddedKafka;
import parma.edu.money_transfer.dto.UserInfoDto;
import parma.edu.money_transfer.service.UserService;

/**
 * Базовый класс юнит-теста.
 */
@EmbeddedKafka
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
public abstract class AbstractTest {
    protected final static long AWAIT_TIMEOUT_SECONDS = 10L;
    protected final static long POLL_INTERVAL_MS = 150L;

    @Autowired
    protected ObjectMapper mapper;

    @MockBean
    protected UserService userService;

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