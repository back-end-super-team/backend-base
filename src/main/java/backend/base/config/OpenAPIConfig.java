package backend.backendbase.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Back End API documentation")
                        .description("Go here to test your API")
                        .version("1.0"));
    }
    // We can config to set up grouping API
//    @Bean
//    public GroupedOpenApi v1Api() {
//        return GroupedOpenApi.builder()
//                .group("api-v1")
//                .pathsToMatch("/**/v1/**")
//                .build();
//    }

}

/* DOCS for Swagger */
/*
* Swagger often used Annotations
* @Tag -> rename - description for a controller
* @Operation -> summary and description for a specific API
* @Hidden -> hiding specific API
* @Parameter -> show parameters of API
* @RequestBody -> show request body of API
* @ApiResponse -> show sample API response
* */