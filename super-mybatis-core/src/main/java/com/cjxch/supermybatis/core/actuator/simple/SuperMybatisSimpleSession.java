package com.cjxch.supermybatis.core.actuator.simple;

import com.cjxch.supermybatis.core.actuator.SuperMybatisBaseQuery;
import com.cjxch.supermybatis.core.actuator.SuperMybatisBaseSession;
import com.cjxch.supermybatis.core.mapper.BaseMapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/30/22:05
 */
public class SuperMybatisSimpleSession extends SuperMybatisBaseSession {

    public SuperMybatisSimpleSession(BaseMapper baseMapper) {
        this.setBaseMapper(baseMapper);
    }

    @Override
    public SuperMybatisBaseQuery createQuery(String sql) {
        String _sql = sql;

        Pattern p = Pattern.compile(":(.*?)\\ ");//正则表达式，取=和|之间的字符串，不包括=和|
        Matcher matcher = p.matcher(sql+" ");

        String matcherStr = null;
        while( matcher.find() ){
            // 不包含前后的两个字符
            matcherStr = matcher.group(1);
            _sql = _sql.replace(":"+matcherStr, "#{"+matcherStr+"}");
        }

        return new SuperMybatisSimpleQuery(this.baseMapper, _sql);
    }

}
