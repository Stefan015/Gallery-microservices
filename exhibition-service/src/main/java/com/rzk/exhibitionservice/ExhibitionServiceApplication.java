package com.rzk.exhibitionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ExhibitionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExhibitionServiceApplication.class, args);
	}

}
