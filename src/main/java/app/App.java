package app;


//import config.Config;
//import config.DataSourceConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;


@SpringBootApplication
@ComponentScan(basePackages = {"controller", "repository", "service", "entity"})
@EnableJpaRepositories(basePackages = "repository")
@EnableAutoConfiguration
@EnableWebMvc
@EntityScan(basePackages = {"entity"})
public class App {

    public static void main(String[] args) {

        ApplicationContext applicationContext = SpringApplication.run(App.class, args);
        Arrays.stream(applicationContext.getBeanDefinitionNames()).forEach(s -> System.out.println(s.toUpperCase()));

    }
}
