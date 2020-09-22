package com.hsj.supermybatis.demo;

import com.hsj.supermybatis.demo.entity.User;
import com.hsj.supermybatis.demo.service.UserInfoService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class SuperMybatisDemoApplicationTests {

	@Autowired
	UserInfoService userInfoService;

	@Test
	void contextLoads() {
		userInfoService.process();
	}

	@Test
	void testInsert(){

		User user = new User("菜菜2", 30, "314170122@qq.com", "菜鸡小彩虹");
		userInfoService.insert(user);
	}

	@Test
	void testBatchInsert(){
		List<User> list = new ArrayList(){
			{
				add(new User("菜菜2", 30, "314170122@qq.com", "菜鸡小彩虹1"));
				add(new User("菜菜2", 30, "314170122@qq.com", "菜鸡小彩虹2"));
				add(new User("菜菜2", 30, "314170122@qq.com", "菜鸡小彩虹3"));
				add(new User("菜菜2", 30, "314170122@qq.com", "菜鸡小彩虹4"));
				add(new User("菜菜2", 30, "314170122@qq.com", "菜鸡小彩虹5"));
				add(new User("菜菜2", 30, "314170122@qq.com", "菜鸡小彩虹6"));
				add(new User("菜菜2", 30, "314170122@qq.com", "菜鸡小彩虹7"));
			}
		};

		userInfoService.batchInsert(list);
	}

}
