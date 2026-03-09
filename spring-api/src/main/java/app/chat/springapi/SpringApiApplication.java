package app.chat.springapi;

import app.chat.springapi.models.User;
import app.chat.springapi.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackageClasses = User.class)
@EnableJpaRepositories(basePackageClasses = UserRepository.class)

public class SpringApiApplication {



	public static void main(String[] args) {
		SpringApplication.run(SpringApiApplication.class, args);
	}

}
