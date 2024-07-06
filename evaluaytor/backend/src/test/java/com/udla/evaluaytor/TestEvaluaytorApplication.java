package com.udla.evaluaytor;

import org.springframework.boot.SpringApplication;

public class TestEvaluaytorApplication {

	public static void main(String[] args) {
		SpringApplication.from(EvaluaytorApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
