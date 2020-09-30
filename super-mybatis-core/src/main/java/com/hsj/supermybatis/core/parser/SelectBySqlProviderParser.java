package com.hsj.supermybatis.core.parser;

import com.hsj.supermybatis.base.bean.Pager;
import com.hsj.supermybatis.base.enu.DbType;

import java.util.Map;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/22/15:46
 */
public class SelectBySqlProviderParser extends SelectObjectSqlProviderParser {

    @Override
    public String execute(Map<String, Object> map) {

        String sql = (String)map.get(SqlProviderConstants.SQL);

        return sql;
    }
}
