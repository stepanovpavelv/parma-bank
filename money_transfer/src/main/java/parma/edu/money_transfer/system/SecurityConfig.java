package parma.edu.money_transfer.system;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfig {
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

    @Value("${spring.security.oauth2.client.uri}")
    private String clientUrl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(c -> {
            CorsConfigurationSource source = s -> {
                CorsConfiguration cc = new CorsConfiguration();
                cc.setAllowCredentials(true);
                cc.setAllowedOrigins(List.of(clientUrl));
                cc.setAllowedHeaders(List.of("*"));
                cc.setAllowedMethods(List.of("*"));
                return cc;
            };

            c.configurationSource(source);
        });

        http
                .authorizeRequests()
                .antMatchers(SWAGGER_ENDPOINTS).permitAll()
                .antMatchers("/api/v1/balance/**").permitAll()
                .anyRequest()
                .authenticated().and()
                .oauth2ResourceServer()
                .jwt();

        return http.build();
    }
}