package com.cloudtask.cloudbeestask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.cloudtask")
@EntityScan("com.cloudtask.model")
@EnableJpaRepositories(basePackages = "com.cloudtask.repository")
public class CloudbeestaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudbeestaskApplication.class, args);
	}

}
