package com.hsj.supermybatis.core.parser;

import com.hsj.supermybatis.base.bean.Pager;
import com.hsj.supermybatis.base.enu.DbType;

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

        return DbType.getPagerSql(super.execute(map), setting.getDbDriverClass(), pager);
    }
}
