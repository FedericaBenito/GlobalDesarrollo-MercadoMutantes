package GLOBAL_MUTANTES.apiConfig;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Global Mutantes - API de Detección de ADN Mutante")
                        .version("1.0.0")
                        .description("""
                                API REST desarrollada para la detección de mutantes mediante el análisis 
                                de secuencias de ADN. El sistema identifica patrones genéticos específicos 
                                que determinan si un individuo es mutante o humano.
                                
                                **Características principales:**
                                - Detección de secuencias de ADN mutante
                                - Almacenamiento persistente de análisis realizados
                                - Estadísticas de verificaciones de ADN
                                - Validación de matrices NxN con bases nitrogenadas válidas (A, T, C, G)
                                
                                **Criterios de detección:**
                                Un humano es considerado mutante si se encuentran más de una secuencia 
                                de cuatro letras iguales de forma horizontal, vertical u oblicua.
                                """)
                        .contact(new Contact()
                                .name("Federica Benito")
                                .email("thaliaunder@gmail.com")
                                .url("https://github.com/federica-benito"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT"))
                        .termsOfService("https://github.com/federica-benito/mutantes"))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de Desarrollo Local"),
                        new Server()
                                .url("https://api-mutantes.herokuapp.com")
                                .description("Servidor de Producción")
                ))
                .externalDocs(new io.swagger.v3.oas.models.ExternalDocumentation()
                        .description("Documentación del Proyecto - Comisión 3K10")
                        .url("https://github.com/federica-benito/mutantes/blob/main/README.md"));
    }
}