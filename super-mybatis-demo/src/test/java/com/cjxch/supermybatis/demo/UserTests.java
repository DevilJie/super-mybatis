package com.cjxch.supermybatis.demo;

import com.alibaba.fastjson2.JSON;
import com.cjxch.supermybatis.base.bean.Pager;
import com.cjxch.supermybatis.core.tools.query.CriteriaConnector;
import com.cjxch.supermybatis.core.tools.query.SmCriteria;
import com.cjxch.supermybatis.core.tools.query.SmCriterion;
import com.cjxch.supermybatis.core.tools.query.SmCriterionArrays;
import com.cjxch.supermybatis.demo.dto.UserJobDto;
import com.cjxch.supermybatis.demo.entity.User;
import com.cjxch.supermybatis.demo.enu.Gender;
import com.cjxch.supermybatis.demo.service.UserInfoService;
import com.cjxch.supermybatis.demo.dto.UserDto;
import com.cjxch.supermybatis.tenant.SuperMybatisTenant;
import com.cjxch.supermybatis.tenant.SuperMybatisTenantInfo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class UserTests {



	private DataSource dataSource;

	@Before
	public void befor(){
		ConfigurableApplicationContext context = SpringApplication.run(SuperMybatisDemoApplication.class, new String[]{});
		userInfoService = context.getBean(UserInfoService.class);
		dataSource = (DataSource)context.getBean("supermybatisDataSource");
	}

	UserInfoService userInfoService;

	@Test
	public void get(){
		User user = userInfoService.get("764603998102622264");
		System.out.println(user);
	}
	@Test
	public void del(){
		System.out.println(userInfoService.delete("503681798811684901"));
	}

	@Test
	public void testInsert() throws IOException {
		try(SuperMybatisTenant tenant = SuperMybatisTenant.openTenant("org_id", "5")) {
			System.out.println(Thread.currentThread().getName());
			UserDto user = new UserDto();
			user.setAgeStart(18);
			user.setAge(19);
			user.setGender(Gender.FEMALE);
			user.setEmail("cjxch@cjxch.com");
			user.setRealName("红妹妹");
			String s = (String) userInfoService.insert(user);
			System.out.println(s + "对象：" + user.toString());
			System.out.println(dataSource);
		}
	}

	@Test
	public void testBatchInsert(){
		try(SuperMybatisTenant tenant = SuperMybatisTenant.openTenant("org_id", "4")) {
			List<User> list = new ArrayList() {
				{
					add(new User("菜菜2", 30, "314170122@qq.com"));
					add(new User("菜菜2", 30, "314170122@qq.com"));
				}
			};

			System.out.println(JSON.toJSONString(userInfoService.batchInsert(list)));
		}
	}

	@Test
	public void testAllList() throws InterruptedException {
		try(SuperMybatisTenant tenant = SuperMybatisTenant.openTenant("org_id", "5")) {
			TimeUnit.SECONDS.sleep(1);
			System.out.println(JSON.toJSONString(userInfoService.allList()));
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testDelete(){
		try(SuperMybatisTenant tenant = SuperMybatisTenant.openTenant("org_id", "4")) {
			System.out.println(userInfoService.delete("750106411618275355"));
		}
	}

	@Test
	public void testUpdate(){
		try(SuperMybatisTenant tenant = SuperMybatisTenant.openTenant("org_id", "4")) {
			User user = new User();
			user.setId("750106411635052592");
			user.setRealName("小屁孩");
			user.setAge(29);
			user.setEmail("cjxch@cjxch.com");
			System.out.println(userInfoService.update(user));
		}
	}

	@Test
	public void testPager(){
		SuperMybatisTenant tenant = SuperMybatisTenant.openTenant("org_id", "4");
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
		tenant.close();

		tenant = SuperMybatisTenant.openTenant("org_id", "5");
		pager = new Pager();
		pager.setPageSize(10);
		pager.setOrderBy("age");
		pager.setOrder(Pager.Order.asc);
		u = new UserDto();
		u.setAges(new ArrayList());
		u.getAges().add(19);
		u.getAges().add(22);
		pager = userInfoService.getPager(pager, u);
		System.out.println(JSON.toJSONString(pager));
		tenant.close();
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
	public void testSmCriteria(){

		SmCriterionArrays arr = new SmCriterionArrays.SmCriterionArraysBuild()
				.in("id", new String[]{"712433319333072950", "712433533724921876"})
				.like("realName", "%11", CriteriaConnector.AND).build(CriteriaConnector.AND);

		SmCriteria smc = new SmCriteria.SmCriteriaBuild()
				.add(arr)
				.add(SmCriterion.eq("id", "712436551878971436", CriteriaConnector.OR))
				.customerRetColumn(new String[]{"id", "realName"})
				.build();
		System.out.println(smc);
		System.out.println(JSON.toJSONString(userInfoService.getList(smc)));
		System.out.println(userInfoService.delete(smc));
		System.out.println(JSON.toJSONString(userInfoService.getList(smc)));
	}
}
