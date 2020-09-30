package com.hsj.supermybatis.core.actuator.simple;

import com.hsj.supermybatis.core.actuator.SuperMybatisBaseQuery;
import com.hsj.supermybatis.core.mapper.BaseMapper;
import com.hsj.supermybatis.core.parser.SqlProviderConstants;
import com.hsj.supermybatis.core.tools.CoreUtil;

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
        Map<String, Object> map = new HashMap<>();
        map.put(SqlProviderConstants.SQL, this.sql);
        return getBaseMapper().queryBySql(map);
    }
}
