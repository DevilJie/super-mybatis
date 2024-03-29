package com.cjxch.supermybatis.core.parser;

import java.util.Map;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/22/15:46
 */
public class UpdateBySqlProviderParser extends BaseSqlProviderParser {

    @Override
    public String execute(Map<String, Object> map) {
        return commonTenantProcess(map, (String)map.get(SqlProviderConstants.SQL));
    }
}
