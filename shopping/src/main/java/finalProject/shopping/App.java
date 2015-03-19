package finalProject.shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
@ComponentScan
@EnableAutoConfiguration
@ImportResource({"classpath*:applicationContext.xml"})
public class App {
	public static void main(String[] args) {
		System.out.println("Hello");
		SpringApplication.run(App.class, args);
	}
}
