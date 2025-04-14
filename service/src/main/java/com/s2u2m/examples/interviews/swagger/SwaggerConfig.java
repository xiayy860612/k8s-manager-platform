package com.s2u2m.examples.interviews.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({SwaggerProperties.class})
public class SwaggerConfig {
  public static final String[] JWT_HOST_URLS =
      new String[] {"/swagger-*/**", "/javainuse-openapi/**", "/v3/api-docs/**", "/v2/api-docs/**"};
  private static final String BASIC_SCHEME = "basic";

  @ConditionalOnMissingBean
  @Bean
  public OpenAPI openAPI(SwaggerProperties properties) {
    Info info =
        new Info()
            .title(properties.getTitle())
            .description(properties.getDescription())
            .version(properties.getVersion());

    SecurityRequirement requirement =
        new SecurityRequirement().addList(BASIC_SCHEME);
    Components components =
        new Components()
            .addSecuritySchemes(BASIC_SCHEME, getBasicScheme());
    return new OpenAPI().info(info).addSecurityItem(requirement).components(components);
  }

  private static SecurityScheme getBasicScheme() {
    return new SecurityScheme().scheme(BASIC_SCHEME).type(Type.HTTP);
  }

}
