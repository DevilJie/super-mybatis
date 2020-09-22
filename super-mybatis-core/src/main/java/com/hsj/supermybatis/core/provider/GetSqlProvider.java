package com.hsj.supermybatis.core.provider;

import com.hsj.supermybatis.base.enu.BaseSqlTemplate;
import org.apache.ibatis.mapping.SqlCommandType;

import java.util.Map;

/**
 * 根据ID获取 sql构建器
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/21/7:28
 */
public class GetSqlProvider extends BaseSqlProvider{

    public GetSqlProvider(){
        SQL_TYPE = SqlCommandType.SELECT;
    }

    @Override
    public String execute(Map<String, Object> map) {
        super.execute(map);
        String tableName = this.TABLE_NAME;
        String primaryKey = this.PRIMARY_KEY;
        return String.format(BaseSqlTemplate.GET.getSql(), tableName, primaryKey, "#{id}");
    }
}
