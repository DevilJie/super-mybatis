package com.hsj.supermybatis.core.provider;

import com.hsj.supermybatis.base.enu.BaseSqlTemplate;
import com.hsj.supermybatis.core.setting.GlobalSetting;

import java.util.Map;

/**
 * 根据ID获取 sql构建器
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/21/7:28
 */
@Deprecated
public class GetSqlProvider extends BaseSqlProvider{

    @Override
    public String execute(Map<String, Object> map) {
        super.execute(map);
        String tableName = TABLE_NAME;
        String primaryKey = PRIMARY_KEY;
        String sql = String.format(BaseSqlTemplate.GET.getSql(), tableName, primaryKey, "#{id}");
        if(GlobalSetting.getGlobalSetting().getDatabaseSetting().getShowSql()) {
            logger.info("\n\n【 Super-Mybatis 】 Sql调试 start.....\n" +
                        "【 Super-Mybatis 】 sql语句 ： {} \n" +
                        "【 Super-Mybatis 】 参数 ：#{id} = {} \n" +
                        "【 Super-Mybatis 】 Sql调试 end.....\n", sql, map.get("id"));
        }
        return sql;
    }
}
