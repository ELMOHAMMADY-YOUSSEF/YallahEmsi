package YallahEmsi.config; // Bdel l-package 3la 7sab fin 7ettiti l-fichier

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Had l-koud kay-goul: Ay fichier l-lien dyalo /uploads/..., sir jbdo mn l-dossier uploads
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}