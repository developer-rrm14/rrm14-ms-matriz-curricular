package com.rrm14.cliente.escola.matrizcurricular;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MatrizCurricularApplication extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MatrizCurricularApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(MatrizCurricularApplication.class, args);
	}

}
