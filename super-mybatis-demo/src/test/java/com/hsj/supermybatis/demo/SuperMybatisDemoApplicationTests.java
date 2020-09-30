package com.hsj.supermybatis.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hsj.supermybatis.base.bean.Pager;
import com.hsj.supermybatis.demo.entity.User;
import com.hsj.supermybatis.demo.service.UserInfoService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.*;

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
		String s = userInfoService.insert(user);
		System.out.println(s);
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

		System.out.println(JSON.toJSONString(userInfoService.batchInsert(list)));
	}

	@Test
	void testAllList(){
		System.out.println(JSON.toJSONString(userInfoService.allList()));
	}

	@Test
	void testDelete(){
		System.out.println(userInfoService.delete("493137875643142166"));
	}

	@Test
	void testUpdate(){
		User user = new User();
		user.setId("493431920625979434");
		user.setName("红妹妹");
		user.setAge(19);
		user.setEmailAddress("cjxch@cjxch.com");
		user.setNname("哈哈");
		System.out.println(userInfoService.update(user));
	}

	@Test
	void testPager(){
		Pager pager = new Pager();
		pager.setPageSize(10);
		User u = new User();
		u.setAge(19);
		u.setName("红妹妹");
		pager = userInfoService.getPager(pager, u);
		System.out.println(JSON.toJSONString(pager));
	}
}
