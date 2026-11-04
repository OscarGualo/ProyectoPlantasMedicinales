package ec.epn.huertos.comun;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

/**
 * Sirve la SPA compilada (AD-02). Las rutas del frontend, como /huertos/5, devuelven index.html para que
 * React Router las resuelva; las rutas /api inexistentes responden 404.
 */
@Configuration(proxyBeanMethods = false)
public class SpaConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String ruta, Resource ubicacion) throws IOException {
                        Resource recurso = ubicacion.createRelative(ruta);
                        if (recurso.exists() && recurso.isReadable()) {
                            return recurso;
                        }
                        if (ruta.startsWith("api/") || ruta.contains(".")) {
                            return null;
                        }
                        Resource indice = new ClassPathResource("/static/index.html");
                        return indice.exists() ? indice : null;
                    }
                });
    }
}
