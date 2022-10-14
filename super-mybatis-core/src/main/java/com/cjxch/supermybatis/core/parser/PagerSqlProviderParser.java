package com.cjxch.supermybatis.core.parser;

import com.cjxch.supermybatis.base.bean.Pager;
import com.cjxch.supermybatis.base.enu.DbType;
import com.cjxch.supermybatis.core.datasource.SuperMybatisDatasourceHandler;

import java.util.Map;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/22/15:46
 */
public class PagerSqlProviderParser extends SelectObjectSqlProviderParser {

    @Override
    public String execute(Map<String, Object> map) {
        /**
         * 公共初始化
         */
        commonInit(map);

        Pager pager = (Pager) map.get(SqlProviderConstants.PAGER);

        return DbType.getPagerSql(super.execute(map), setting.getDriverClass(), pager);
    }
}
