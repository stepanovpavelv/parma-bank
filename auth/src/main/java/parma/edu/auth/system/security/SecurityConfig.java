package parma.edu.auth.system.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import parma.edu.auth.system.CorsCustomizer;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CorsCustomizer corsCustomizer;

    private static final String[] SWAGGER_ENDPOINTS = new String[] {
        "/v2/api-docs",
        "/swagger.json",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui.html",
        "/webjars/**",
        "/v3/api-docs/**",
        "/swagger-ui/**" // other public endpoints of your API may be appended to this array
    };

    @Value("${spring.security.client.redirect.logout}")
    private String logoutRedirectUri;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        corsCustomizer.customize(http);

        return http
                .csrf().disable()
                .logout().logoutSuccessUrl(logoutRedirectUri)
                .and()
                .formLogin()
                .and()
                .authorizeHttpRequests()
                .antMatchers("/api/v1/user/**").permitAll()
                .antMatchers(SWAGGER_ENDPOINTS).permitAll()
                .anyRequest().authenticated()
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}