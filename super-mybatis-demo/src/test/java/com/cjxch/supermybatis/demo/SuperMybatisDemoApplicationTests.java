package com.cjxch.supermybatis.demo;

import com.alibaba.fastjson.JSON;
import com.cjxch.supermybatis.base.bean.Pager;
import com.cjxch.supermybatis.demo.dto.UserJobDto;
import com.cjxch.supermybatis.demo.entity.User;
import com.cjxch.supermybatis.demo.service.UserInfoService;
import com.cjxch.supermybatis.demo.dto.UserDto;
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
	void get(){
		System.out.println(userInfoService.get("503681798811684901"));
		System.out.println(userInfoService.get("503681798811684901"));
		System.out.println(userInfoService.delete("503681798811684901"));
		System.out.println(userInfoService.get("503681798811684901"));
	}
	@Test
	void del(){
		System.out.println(userInfoService.delete("503681798811684901"));
	}

	@Test
	void testInsert(){
		UserDto user = new UserDto();
		user.setAgeStart(18);
		user.setAge(19);
		user.setEmail("cjxch@cjxch.com");
		user.setRealName("红妹妹");
		String s = (String) userInfoService.insert(user);
		System.out.println(s);
	}

	@Test
	void testBatchInsert(){
		List<User> list = new ArrayList(){
			{
				add(new User("菜菜2", 30, "314170122@qq.com"));
				add(new User("菜菜2", 30, "314170122@qq.com"));
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
		user.setId("496976191702437956");
		user.setRealName("小屁孩");
		user.setAge(29);
		user.setEmail("cjxch@cjxch.com");
		System.out.println(userInfoService.update(user));
	}

	@Test
	void testPager(){
		Pager pager = new Pager();
		pager.setPageSize(10);
		pager.setOrderBy("age");
		pager.setOrder(Pager.Order.asc);
		UserDto u = new UserDto();
		u.setAges(new ArrayList());
		u.getAges().add(19);
		u.getAges().add(22);
		pager = userInfoService.getPager(pager, u);
		System.out.println(JSON.toJSONString(pager));
	}

	@Test
	void testGetList(){
		UserDto u = new UserDto();
		u.setAgeStart(19);
		u.setAgeEnd(19);
		u.setRealName("妹");
		User user = userInfoService.get(u);
		System.out.println(user);
		u.setRealName("红妹妹");
		System.out.println(userInfoService.getList(user));
		System.out.println(userInfoService.getList(user));
	}

	@Test
	void testUserJob(){
		Pager pager = new Pager();
		pager.setPageSize(10);
		pager.setOrderBy("age");
		pager.setOrder(Pager.Order.asc);
		UserJobDto u = new UserJobDto();
		u.setRealName("妹");
		pager = userInfoService.getPager(pager, u);
		System.out.println(JSON.toJSONString(pager));
	}
}
