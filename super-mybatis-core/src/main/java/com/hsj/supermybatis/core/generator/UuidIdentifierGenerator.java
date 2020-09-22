package com.hsj.supermybatis.core.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/19/18:50
 */

public class UuidIdentifierGenerator implements IdentifierGenerator{

    @Override
    public String nextId(Object entity) {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public List<String> nextIds(Object entity, Integer size) {
        List<String> ret = new ArrayList<>();
        for(int i = 0 ; i < size ; i++){
            ret.add(nextId(entity));
        }
        return ret;
    }
}
