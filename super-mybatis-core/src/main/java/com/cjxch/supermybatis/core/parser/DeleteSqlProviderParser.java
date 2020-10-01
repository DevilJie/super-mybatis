package com.cjxch.supermybatis.core.parser;

import com.cjxch.supermybatis.base.enu.BaseSqlTemplate;

import java.util.Map;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/22/15:46
 */
public class DeleteSqlProviderParser extends BaseSqlProviderParser {

    @Override
    public String execute(Map<String, Object> map) {
        /**
         * 公共初始化
         */
        commonInit(map);

       return String.format(BaseSqlTemplate.DELETE.getSql(), TABLE_NAME, commonPrimaryKey(map), "#{id}");
    }
}
