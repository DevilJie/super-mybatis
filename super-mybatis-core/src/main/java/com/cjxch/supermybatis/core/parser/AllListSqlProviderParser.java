package com.cjxch.supermybatis.core.parser;

import com.cjxch.supermybatis.base.enu.BaseSqlTemplate;
import com.cjxch.supermybatis.core.setting.GlobalSetting;
import com.cjxch.supermybatis.tenant.SuperMybatisTenant;

import java.util.Map;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/22/15:46
 */
public class AllListSqlProviderParser extends BaseSqlProviderParser {

    @Override
    public String execute(Map<String, Object> map) {
        /**
         * 公共初始化
         */
        commonInit(map);

        return commonTenantProcess(map, String.format(BaseSqlTemplate.ALL_LIST.getSql(), TABLE_NAME));

    }
}
