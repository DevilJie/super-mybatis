package com.hsj.supermybatis.core.actuator.simple;

import com.hsj.supermybatis.base.bean.Pager;
import com.hsj.supermybatis.core.actuator.SuperMybatisBaseQuery;
import com.hsj.supermybatis.core.mapper.BaseMapper;
import com.hsj.supermybatis.core.parser.SqlProviderConstants;
import com.hsj.supermybatis.core.tools.CoreUtil;
import com.hsj.supermybatis.core.tools.SuperMybatisAssert;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/30/22:14
 */
public class SuperMybatisSimpleQuery extends SuperMybatisBaseQuery {

    public SuperMybatisSimpleQuery(BaseMapper baseMapper, String sql) {
        this.setBaseMapper(baseMapper);
        this.setSql(sql);
    }

    @Override
    public <T> List<T> list(Class<T> t) {
        return list().stream().map(item -> {
            T entity = null;
            try {
                entity = t.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return (T)CoreUtil.process(entity, item);
        }).collect(Collectors.toList());
    }

    @Override
    public List<HashMap<String, Object>> list() {
        return getBaseMapper().queryBySql(parameter);
    }

    @Override
    public Serializable uniqueResult() {
        List<HashMap<String, Object>> ret = getBaseMapper().queryBySql(parameter);
        if(ret == null || ret.size() == 0) return null;
        SuperMybatisAssert.check(ret.size() == 1, "The data you query is not unique, Please check.");
        HashMap<String, Object> retMap = ret.get(0);
        for(Map.Entry<String, Object> entry : retMap.entrySet()){
            return (Serializable) entry.getValue();
        }
        return null;
    }

    @Override
    public <T> T uniqueObject(Class<T> t) {
        List<T> retList = list().stream().map(item -> {
            T entity = null;
            try {
                entity = t.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return (T)CoreUtil.process(entity, item);
        }).collect(Collectors.toList());
        if(retList == null || retList.size() == 0) return null;
        SuperMybatisAssert.check(retList.size() == 1, "The data you query is not unique, Please check.");
        return retList.get(0);
    }

    @Override
    public Object uniqueObject() {
        List<HashMap<String, Object>> retList = getBaseMapper().queryBySql(parameter);
        if(retList == null || retList.size() == 0) return null;
        SuperMybatisAssert.check(retList.size() == 1, "The data you query is not unique, Please check.");
        return retList.get(0);
    }

    @Override
    public Long executeUpdate() {
        return getBaseMapper().updateBySql(parameter);
    }

    @Override
    public Serializable execute() {
        return getBaseMapper().updateBySql(parameter);
    }

    @Override
    public Pager queryPager(Pager pager) {
        parameter.put(SqlProviderConstants.ORDER, pager.getOrder());
        parameter.put(SqlProviderConstants.ORDER_BY, pager.getOrderBy());
        parameter.put(SqlProviderConstants.PAGER, pager);
        List list = getBaseMapper().queryPagerBySql(parameter);

        Long totalCount = getBaseMapper().queryPagerCountBySql(parameter);

        pager.setResult(list);
        pager.setTotalCount(totalCount==null?0:totalCount.intValue());
        return pager;
    }

    @Override
    public Pager queryPager(Pager pager, Class t) {
        pager = queryPager(pager);

        List retList = list().stream().map(item -> {
            Object entity = null;
            try {
                entity = t.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return CoreUtil.process(entity, item);
        }).collect(Collectors.toList());

        pager.setResult(retList);
        
        return pager;
    }
}
