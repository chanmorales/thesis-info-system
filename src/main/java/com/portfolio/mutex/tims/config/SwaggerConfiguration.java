package com.portfolio.mutex.tims.config;

import com.portfolio.mutex.tims.dto.ExceptionDto;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(title = "Thesis Info System API", version = "1.0",
        description = "API definitions of Thesis Information Management System"),
    security = @SecurityRequirement(name = "bearerAuth"),
    servers = {
        @Server(url = "/")
    })
@SecurityScheme(name = "bearerAuth",
    description = "JWT Authentication",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER)
public class SwaggerConfiguration {

  @Bean
  public OpenApiCustomizer openApiCustomizer() {
    return openApi -> {
      openApi.getComponents().getSchemas()
          .putAll(ModelConverters.getInstance().read(ExceptionDto.class));
      openApi.getPaths().values()
          .forEach(pathItem -> pathItem.readOperations()
              // Add common API responses (401 and 500) to all resource definition
              .forEach(operation -> operation.getResponses()
                  .addApiResponse("401", new ApiResponse()
                      .description("Authentication failed.")
                      .content(new Content().addMediaType("application/text", new MediaType()
                          .example("Authentication failed."))))
                  .addApiResponse("500", new ApiResponse()
                      .description("Server has encountered an error it doesn't know how to handle.")
                      .content(new Content().addMediaType("application/json", new MediaType()
                          .schema(new Schema<ExceptionDto>()
                              .name("ExceptionDto")
                              .$ref("#/components/schemas/ExceptionDto"))
                          .example("""
                              {
                                "message": "Unknown error has occurred."
                              }"""))))));
    };
  }
}
