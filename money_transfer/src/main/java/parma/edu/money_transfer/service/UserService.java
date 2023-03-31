package parma.edu.money_transfer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import parma.edu.money_transfer.dto.UserInfoDto;
import reactor.core.publisher.Mono;

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
                .onErrorResume(WebClientResponseException.class,
                        e -> e.getRawStatusCode() == HttpStatus.NOT_FOUND.value() ? Mono.empty() : Mono.error(e))
                .block();
    }

    @CacheEvict(value = "users", allEntries = true)
    @Scheduled(cron = "${spring.security.oauth2.resourceserver.user.schedule.cron}")
    public void refreshCaches() {
        log.info("Clear users cache");
    }
}