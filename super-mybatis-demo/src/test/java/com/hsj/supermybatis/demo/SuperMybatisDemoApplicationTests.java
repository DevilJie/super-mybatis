package com.hsj.supermybatis.demo;

import com.hsj.supermybatis.demo.service.UserInfoService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class SuperMybatisDemoApplicationTests {

	@Autowired
	UserInfoService userInfoService;

	@Test
	void contextLoads() {
		userInfoService.process();
	}
}
