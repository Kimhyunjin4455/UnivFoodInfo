package org.zerock.univFood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class UnivFoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnivFoodApplication.class, args);
	}

}
