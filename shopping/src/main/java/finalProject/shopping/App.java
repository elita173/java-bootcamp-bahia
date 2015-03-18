package finalProject.shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
@ComponentScan
@EnableAutoConfiguration
public class App {
	public static void main(String[] args) {
		System.out.println("Hello");
		SpringApplication.run(App.class, args);
	}
}
