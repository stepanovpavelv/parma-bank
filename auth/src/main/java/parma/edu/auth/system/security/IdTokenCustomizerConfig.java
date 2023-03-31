package parma.edu.auth.system.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

@Configuration
public class IdTokenCustomizerConfig {

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer(UserDetailsService userService) {
        return (context) -> {

            if (OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())) {
                UserDetails userInfo = userService.loadUserByUsername(context.getPrincipal().getName());
                if (userInfo instanceof ApplicationUser ) {
                    context.getClaims().claims(claims ->
                            claims.put("scope", ((ApplicationUser) userInfo).getRole()));
                }
            }
        };
    }
}