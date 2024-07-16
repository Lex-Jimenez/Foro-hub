package alurachallenge.foro_hub.config;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI(){
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("URL del servidor de pruebas");

        Server prodServer = new Server();
        prodServer.setUrl("https://**************");
        prodServer.setDescription("URL del servidor de producción");

        Contact contact = new Contact();
        contact.setEmail("********************.com");
        contact.setName("***********");
        contact.setUrl("https://********************.com/contacto");

        License mitLicense = new License()
                .name("Licencia MIT")
                .url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Documentación de la API de FORO HUB project API")
                .version("1.0")
                .contact(contact)
                .description("Esta API expone los endpoints para usar FORO HUB.")
                .termsOfService("****************************")
                .license(mitLicense);

        SecurityRequirement securityRequirement = new SecurityRequirement().
                addList("Bearer Authentication");

        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");

        Components components = new Components().addSecuritySchemes("Bearer Authentication", securityScheme);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer, prodServer))
                .addSecurityItem(securityRequirement)
                .components(components);
    }

}