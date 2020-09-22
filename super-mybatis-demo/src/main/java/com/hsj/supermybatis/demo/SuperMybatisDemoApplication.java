package com.hsj.supermybatis.demo;

import com.hsj.supermybatis.extend.spring.anno.SuperMapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.hsj.supermybatis.demo.*")
@SuperMapperScan
public class SuperMybatisDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuperMybatisDemoApplication.class, args);
	}
}
