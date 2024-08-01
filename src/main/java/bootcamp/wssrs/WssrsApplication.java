package bootcamp.wssrs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WssrsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WssrsApplication.class, args);
	}

}
