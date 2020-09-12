package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.scmc.mapper")
public class ConmApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConmApplication.class, args);
	}

}
