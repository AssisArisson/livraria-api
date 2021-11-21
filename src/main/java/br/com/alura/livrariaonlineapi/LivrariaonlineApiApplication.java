package br.com.alura.livrariaonlineapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class LivrariaonlineApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LivrariaonlineApiApplication.class, args);
	}

}
