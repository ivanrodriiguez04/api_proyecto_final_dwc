package Api.proyectoFinalDWSDIW.configuracion;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Aplica las reglas a todas las rutas bajo "/api/**"
                .allowedOrigins("http://localhost:8080","http://localhost:4200") // Permite solicitudes desde el frontend en localhost:8080
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos HTTP permitidos
                .allowedHeaders("*") // Permite todos los encabezados en la solicitud
                .allowCredentials(true); // Permite el uso de cookies o credenciales en las solicitudes
    }
}
