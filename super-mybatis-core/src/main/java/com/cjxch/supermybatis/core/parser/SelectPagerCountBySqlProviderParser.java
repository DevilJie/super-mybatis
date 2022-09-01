package com.cjxch.supermybatis.core.parser;

import java.util.Map;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/22/15:46
 */
public class SelectPagerCountBySqlProviderParser extends SelectBySqlProviderParser {

    @Override
    public String execute(Map<String, Object> map) {

        String sql = (String)map.get(SqlProviderConstants.SQL);

        sql = "SELECT COUNT(*) " + sql.substring(sql.toLowerCase().indexOf("from"));
        return commonTenantProcess(map, sql);
    }
}
