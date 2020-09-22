package com.hsj.supermybatis.core.parser;

import com.hsj.supermybatis.base.enu.BaseSqlTemplate;
import com.hsj.supermybatis.core.enu.SuperMybatisSqlProvider;
import com.hsj.supermybatis.core.provider.BaseSqlProvider;
import com.hsj.supermybatis.core.setting.GlobalSetting;
import com.hsj.supermybatis.core.tools.SqlPrint;

import java.util.Map;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/22/15:46
 */
public class GetSqlProviderParser extends BaseSqlProviderParser {

    @Override
    public String execute(Map<String, Object> map) {
        /**
         * 公共初始化
         */
        commonInit(map);

        String sql = String.format(BaseSqlTemplate.GET.getSql(), TABLE_NAME, commonPrimaryKey(map), "#{id}");
        /**
         * 打印sql
         */
        SqlPrint.print(map, sql);
        return sql;

    }
}
