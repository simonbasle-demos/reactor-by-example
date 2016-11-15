package io.projectreactor.examples.spring;

import io.projectreactor.examples.MyReactiveLibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ByexampleApplication {

	@Bean
	public MyReactiveLibrary reactiveLibrary() {
		return new MyReactiveLibrary();
	}

	public static void main(String[] args) {
		SpringApplication.run(ByexampleApplication.class, args);
	}
}
