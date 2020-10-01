package com.cjxch.supermybatis.core.provider;

import com.cjxch.supermybatis.base.enu.BaseSqlTemplate;

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
        return String.format(BaseSqlTemplate.GET.getSql(), tableName, primaryKey, "#{id}");
    }
}
