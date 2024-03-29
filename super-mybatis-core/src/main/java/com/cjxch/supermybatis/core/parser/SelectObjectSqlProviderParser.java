package com.cjxch.supermybatis.core.parser;

import com.cjxch.supermybatis.base.annotation.Column;
import com.cjxch.supermybatis.base.bean.Pager;
import com.cjxch.supermybatis.base.enu.BaseSqlTemplate;
import com.cjxch.supermybatis.core.tools.CamelCaseUtils;
import com.cjxch.supermybatis.core.tools.ReflectionUtil;
import com.cjxch.supermybatis.core.tools.SuperMybatisAssert;
import com.cjxch.supermybatis.core.tools.TableTools;
import com.cjxch.supermybatis.core.tools.query.SmCriteria;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Map;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/22/15:46
 */
public class SelectObjectSqlProviderParser extends BaseSqlProviderParser {

    @Override
    public String execute(Map<String, Object> map) {
        /**
         * 公共初始化
         */
        commonInit(map);

        Pager.Order order = map.get(SqlProviderConstants.ORDER) == null ? Pager.Order.desc : (Pager.Order)map.get(SqlProviderConstants.ORDER);
        String orderBy = StringUtils.isEmpty(map.get(SqlProviderConstants.ORDER_BY)) ? "" : (String)map.get(SqlProviderConstants.ORDER_BY);
        if(!StringUtils.isEmpty(orderBy)) {
            orderBy = setting.getDatabaseSetting().getCamelModel() ? CamelCaseUtils.processNameWithUnderLine(orderBy) : orderBy;
            orderBy = " order by " + orderBy + " " + order.name();
        }


        SmCriteria smCriteria = (SmCriteria)map.get(SqlProviderConstants.SM_CRITERIA);
        String queryStatementSql = processSearchParam(map);

        String[] customerRetColumn = (smCriteria == null || StringUtils.isEmpty(smCriteria.getCustomerRetColumn()))
                ? null : smCriteria.getCustomerRetColumn();

        StringBuffer retColumn = new StringBuffer("");
        if(customerRetColumn != null && customerRetColumn.length > 0){
            Arrays.asList(customerRetColumn).forEach(str -> {
                retColumn.append(",");
                retColumn.append(TableTools.fieldNameToColumn(setting, str));
            });
        }else{
            retColumn.append(",*");
        }

        return String.format(BaseSqlTemplate.SELECT.getSql(), retColumn.toString().substring(1), TABLE_NAME, queryStatementSql, orderBy);
    }
}
