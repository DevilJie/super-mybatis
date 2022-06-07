package com.cjxch.supermybatis.demo;

import com.alibaba.fastjson2.JSON;
import com.cjxch.supermybatis.base.bean.Pager;
import com.cjxch.supermybatis.core.tools.query.CriteriaConnector;
import com.cjxch.supermybatis.core.tools.query.SmCriteria;
import com.cjxch.supermybatis.core.tools.query.SmCriterion;
import com.cjxch.supermybatis.core.tools.query.SmCriterionArrays;
import com.cjxch.supermybatis.demo.dto.UserJobDto;
import com.cjxch.supermybatis.demo.entity.User;
import com.cjxch.supermybatis.demo.service.UserInfoService;
import com.cjxch.supermybatis.demo.dto.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.Serializable;
import java.util.*;

public class SuperMybatisDemoApplicationTests {

	@Before
	public void befor(){
		ConfigurableApplicationContext context = SpringApplication.run(SuperMybatisDemoApplication.class, new String[]{});
		userInfoService = context.getBean(UserInfoService.class);
	}

	UserInfoService userInfoService;

	@Test
	public void get(){
		System.out.println(userInfoService.loadByColumn("id", "713868623503953997"));
	}
	@Test
	public void del(){
		System.out.println(userInfoService.delete("503681798811684901"));
	}

	@Test
	public void testInsert(){
		UserDto user = new UserDto();
		user.setAgeStart(18);
		user.setAge(19);
		user.setEmail("cjxch@cjxch.com");
		user.setRealName("红妹妹");
		String s = (String) userInfoService.insert(user);
		System.out.println(s);
	}

	@Test
	public void testBatchInsert(){
		List<User> list = new ArrayList(){
			{
				add(new User("菜菜2", 30, "314170122@qq.com"));
				add(new User("菜菜2", 30, "314170122@qq.com"));
			}
		};

		System.out.println(JSON.toJSONString(userInfoService.batchInsert(list)));
	}

	@Test
	public void testAllList(){
		System.out.println(JSON.toJSONString(userInfoService.allList()));
	}

	@Test
	public void testDelete(){
		System.out.println(userInfoService.delete("493137875643142166"));
	}

	@Test
	public void testUpdate(){
		User user = new User();
		user.setId("496976191702437956");
		user.setRealName("小屁孩");
		user.setAge(29);
		user.setEmail("cjxch@cjxch.com");
		System.out.println(userInfoService.update(user));
	}

	@Test
	public void testPager(){
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
	public void testGetList(){
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
	public void testUserJob(){
		Pager pager = new Pager();
		pager.setPageSize(10);
		pager.setOrderBy("age");
		pager.setOrder(Pager.Order.asc);
		UserJobDto u = new UserJobDto();
		u.setRealName("妹");
		pager = userInfoService.getPager(pager, u);
		System.out.println(JSON.toJSONString(pager));
	}

	@Test
	public void testLoadByIds(){
		List<User> userList = userInfoService.loadListByIds(new Serializable[]{"711542893247991812", "712431236315877442"});
		System.out.println(JSON.toJSONString(userList));
	}

	@Test
	public void testSmCriteria(){

		SmCriterionArrays arr = new SmCriterionArrays.SmCriterionArraysBuild()
				.in("id", new String[]{"712433319333072950", "712433533724921876"})
				.like("real_name", "%11", CriteriaConnector.AND).build(CriteriaConnector.AND);

		SmCriteria smc = new SmCriteria.SmCriteriaBuild()
				.add(arr)
				.add(SmCriterion.eq("id", "712436551878971436", CriteriaConnector.OR))
				.build();
		System.out.println(smc);
		System.out.println(JSON.toJSONString(userInfoService.getList(smc)));
	}
}
