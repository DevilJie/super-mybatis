package com.cjxch.supermybatis.core.parser;

import com.cjxch.supermybatis.base.bean.Pager;
import com.cjxch.supermybatis.base.enu.DbType;

import java.util.Map;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/22/15:46
 */
public class SelectPagerBySqlProviderParser extends SelectBySqlProviderParser {

    @Override
    public String execute(Map<String, Object> map) {

        String sql = (String)map.get(SqlProviderConstants.SQL);
        Pager pager = (Pager) map.get(SqlProviderConstants.PAGER);

        return DbType.getPagerSql(sql, setting.getDbDriverClass(), pager);
    }
}
