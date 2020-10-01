package com.cjxch.supermybatis.core.generator;

import com.cjxch.supermybatis.core.tools.Snowflake;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/19/18:50
 */

public class SnowflakeIdentifierGenerator implements IdentifierGenerator{

    private Snowflake snowflake;

    public SnowflakeIdentifierGenerator(long workerId){
        snowflake = Snowflake.create(workerId);
    }

    @Override
    public String nextId(Object entity) {
        return String.valueOf(snowflake.nextId());
    }

    @Override
    public List<String> nextIds(Object entity, Integer size) {
        List<String> ret = new ArrayList<>();
        for(long l : snowflake.nextId(size)){
            ret.add(String.valueOf(l));
        }
        return ret;
    }
}
