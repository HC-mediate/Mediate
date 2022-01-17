package com.ko.mediate.HC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class HcApplication {

	public static void main(String[] args) {
		SpringApplication.run(HcApplication.class, args);
	}

}
