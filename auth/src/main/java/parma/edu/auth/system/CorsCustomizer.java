package parma.edu.auth.system;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Component
public class CorsCustomizer {

    public void customize(HttpSecurity http) throws Exception {
        http.cors(c -> {
            CorsConfigurationSource source = s -> {
                CorsConfiguration conf = new CorsConfiguration();
                conf.setAllowCredentials(true);
                conf.setAllowedOrigins(List.of("http://127.0.0.1:4200"));
                conf.setAllowedHeaders(List.of("*"));
                conf.setAllowedMethods(List.of("*"));
                return conf;
            };

            c.configurationSource(source);
        });
    }
}