package com.cjxch.supermybatis.demo;

import com.cjxch.supermybatis.extend.spring.anno.SuperMapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SuperMapperScan
public class SuperMybatisDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuperMybatisDemoApplication.class, args);
	}
}
