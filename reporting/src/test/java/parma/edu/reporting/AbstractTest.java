package parma.edu.reporting;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import parma.edu.reporting.dto.UserInfoDto;
import parma.edu.reporting.service.UserService;

import java.util.stream.IntStream;

/**
 * Базовый класс для подъёма контекста.
 */
@SpringBootTest
@ActiveProfiles("test")
public abstract class AbstractTest {
    @Value("${spring.accumulator.settings.interval}")
    protected Integer scheduleInterval;

    @MockBean
    protected UserService userService;

    protected void initAllMocks() {
        IntStream.range(1, 5).forEach(this::initUserServiceMockObject);
    }

    private void initUserServiceMockObject(Integer userId) {
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