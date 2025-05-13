package ru.eva_nemo.antiprocrostinate;

import org.springframework.boot.SpringApplication;

public class TestJirBackendApplication {

	public static void main(String[] args) {
		SpringApplication.from(AntiprocrostinateApplication::main)
			.with(TestcontainersConfiguration.class)
			.run(args);
	}

}
