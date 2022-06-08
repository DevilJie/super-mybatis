package com.cjxch.supermybatis.demo.dao.impl;

import com.cjxch.supermybatis.core.dao.impl.BaseDaoImpl;
import com.cjxch.supermybatis.demo.dao.ICompanyDao;
import com.cjxch.supermybatis.demo.entity.Company;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyDaoImpl extends BaseDaoImpl<Company> implements ICompanyDao {
}
