package parma.edu.reporting.system;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static io.swagger.models.auth.In.HEADER;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Configuration
@EnableSwagger2
public class SpringFoxConfig implements WebMvcConfigurer {
    private static final String API_KEY = "JWT";
    private static final String APP_NAME = "Swagger (reporting)";
    private static final String DESCRIPTION = "Application for reporting banking operations and bank accounts";

    @Bean
    public Docket customImplementation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(List.of(apiKey()))
                .securityContexts(List.of(securityContext()))
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .enable(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    private ApiKey apiKey() {
        return new ApiKey(API_KEY, AUTHORIZATION, HEADER.name());
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[] { authorizationScope };
        return List.of(new SecurityReference(API_KEY, authorizationScopes));
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title(APP_NAME)
                .description(DESCRIPTION)
                .build();
    }
}