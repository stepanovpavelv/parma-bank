package parma.edu.reporting.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import parma.edu.reporting.dto.UserInfoDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private static final String USER_API = "/api/v1/user/{id}";

    private final WebClient webClient;

    @Cacheable(value = "users", key = "#id")
    public UserInfoDto readUserById(Integer id) {
        log.info("Executing request to userId={}", id);
        return webClient
                .get()
                .uri(USER_API, id)
                .retrieve()
                .bodyToMono(UserInfoDto.class)
                .block();
    }

    @CacheEvict(value = "users", allEntries = true)
    @Scheduled(cron = "${spring.security.oauth2.resourceserver.user.schedule.cron}")
    public void refreshCaches() {
        log.info("Clear users cache");
    }
}