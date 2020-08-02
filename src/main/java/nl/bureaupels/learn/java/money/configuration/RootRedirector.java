package nl.bureaupels.learn.java.money.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Redirect invocation of root endpoint to /swagger-ui.html
 */
@Configuration
public class RootRedirector {
    @Bean
    public WebMvcConfigurer redirectRootRequest() {
        return new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("").setViewName("redirect:/actuator");
            }
        };
    }
}
