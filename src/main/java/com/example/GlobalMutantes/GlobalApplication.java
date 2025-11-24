package com.example.GlobalMutantes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example")
public class GlobalApplication {
	/*
	NOTA: El código está completamente comentado para explicar su ejecución adecuadamente.
	 */

	public static void main(String[] args) {
		SpringApplication.run(GlobalApplication.class, args);
	}

}
