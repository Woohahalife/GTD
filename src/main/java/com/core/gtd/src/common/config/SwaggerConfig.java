package com.core.gtd.src.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "GTD API 명세서",
                description = "Java, Spring 기반 GTD 프로젝트",
                version = "v1"
        )
)
@RequiredArgsConstructor
public class SwaggerConfig {

    String[] paths = {"/tasks"};

    @Bean
    public GroupedOpenApi getTodoApi() {
        return GroupedOpenApi.builder()
                .group("tasks")
                .pathsToMatch(paths[0] + "/**")
                .build();
    }

    /**
     * Swagger OpenAPI에 보안 요소 추가
     * 발급받은 JWT토큰을 인증에 활용해 API 테스트를 하기 위함
     */
    @Bean
    public OpenAPI apiKey() {
        SecurityScheme apiKey = new SecurityScheme() // API Key 정의
                .type(SecurityScheme.Type.APIKEY) //API Key정의
                .in(SecurityScheme.In.HEADER) // 헤더에 위치
                .name("Authorization"); // 이름은 Authorization

        SecurityRequirement securityRequirement = new SecurityRequirement() // 보안 요구사항 정의
                .addList("Bearer Token"); // Bearer Token 보안 요구사항 추가(모달창에 보임)

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("Bearer Token", apiKey))
                .addSecurityItem(securityRequirement);
    }
}