package service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = "service")
@Configuration
public class SpringConfig {
    @Bean
    public Service service(){
        return new Service();
    }
}
