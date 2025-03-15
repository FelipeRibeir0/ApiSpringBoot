package com.productsAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "API de Produtos", version = "1.0", description = "Documentação da API de Produtos"))
public class FirstApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstApiApplication.class, args);
	}

}