package backend.apiscart.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Value("${openapi.project-title}")
	private String projectTitle;
	@Value("${openapi.project-description}")
	private String projectDescription;
	@Value("${openapi.project-version}")
	private  String projectVersion;
 
    @Bean
    public OpenAPI customOpenAPI() {
        OpenApiProperties properties = infoGet();
        var openApi = new OpenAPI()
                .info(getInfo(properties));
        return openApi;
    }

    private OpenApiProperties infoGet() {
        OpenApiProperties prop = new OpenApiProperties();
        prop.setProjectTitle(projectTitle);
        prop.setProjectVersion(projectVersion);
        prop.setProjectDescription(projectDescription);
        return prop;
    }

    private Info getInfo(OpenApiProperties properties) {
        return new Info()
                .title(properties.getProjectTitle())
                .description(properties.getProjectDescription())
                .version(properties.getProjectVersion())
                .license(getLicense());
    }

    private License getLicense() {
        return new License()
                .name("Unlicense")
                .url("https://unlicense.org/");
    }
}
