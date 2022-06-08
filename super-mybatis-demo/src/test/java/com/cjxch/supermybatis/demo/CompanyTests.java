package com.cjxch.supermybatis.demo;

import com.alibaba.fastjson2.JSON;
import com.cjxch.supermybatis.base.bean.Pager;
import com.cjxch.supermybatis.core.tools.query.CriteriaConnector;
import com.cjxch.supermybatis.core.tools.query.SmCriteria;
import com.cjxch.supermybatis.core.tools.query.SmCriterion;
import com.cjxch.supermybatis.core.tools.query.SmCriterionArrays;
import com.cjxch.supermybatis.demo.dao.ICompanyDao;
import com.cjxch.supermybatis.demo.dto.UserDto;
import com.cjxch.supermybatis.demo.dto.UserJobDto;
import com.cjxch.supermybatis.demo.entity.Company;
import com.cjxch.supermybatis.demo.entity.User;
import com.cjxch.supermybatis.demo.service.UserInfoService;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CompanyTests {

	Logger logger = LoggerFactory.getLogger(CompanyTests.class);

	ICompanyDao companyDao;

	@Before
	public void befor(){
		ConfigurableApplicationContext context = SpringApplication.run(SuperMybatisDemoApplication.class, new String[]{});
		companyDao = context.getBean(ICompanyDao.class) ;
	}

	@Test
	public void testInsert(){
		Company company = new Company();
		company.setAddress("地球上的摸一个角落");
		company.setName("大牛大牛");
		String id = companyDao.insert(company).toString();
		logger.info("新增成功，返回id：{} 对象：{}", id, company.toString());
	}
}
