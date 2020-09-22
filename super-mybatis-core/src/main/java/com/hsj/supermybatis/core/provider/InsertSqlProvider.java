package com.hsj.supermybatis.core.provider;

import com.hsj.supermybatis.base.enu.BaseSqlTemplate;
import org.apache.ibatis.mapping.SqlCommandType;

import java.util.Map;

/**
 * 插入数据 sql构建器
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/21/7:28
 */
public class InsertSqlProvider extends BaseSqlProvider{

    public InsertSqlProvider(){
        SQL_TYPE = SqlCommandType.INSERT;
    }

    @Override
    public String execute(Map<String, Object> map) {
        String tableName = this.TABLE_NAME;
        String sqlTemplate = BaseSqlTemplate.INSERT.getSql();
        return null;
    }
}
