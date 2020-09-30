package com.hsj.supermybatis.core.parser;

import com.hsj.supermybatis.base.bean.Pager;
import com.hsj.supermybatis.base.enu.BaseSqlTemplate;
import com.hsj.supermybatis.base.enu.DbType;
import com.hsj.supermybatis.core.tools.ReflectionUtil;
import com.hsj.supermybatis.core.tools.TableTools;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Map;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/22/15:46
 */
public class PagerSqlProviderParser extends BaseSqlProviderParser {

    @Override
    public String execute(Map<String, Object> map) {
        /**
         * 公共初始化
         */
        commonInit(map);

        Pager pager = (Pager) map.get(SqlProviderConstants.PAGER);
        Pager.Order order = pager.getOrder() == null ? Pager.Order.desc : pager.getOrder();
        String orderBy = StringUtils.isEmpty(pager.getOrderBy()) ? "" : " order by " + pager.getOrderBy() + " " + order.name();
        StringBuffer queryStatement = new StringBuffer();

        Object insertEntity = map.get(SqlProviderConstants.ENTITY);

        if(insertEntity != null) {
            Arrays.asList(insertEntity.getClass().getDeclaredFields()).stream().forEach(item -> {
                if (!StringUtils.isEmpty(ReflectionUtil.getFieldValue(insertEntity, item.getName()))) {
                    queryStatement.append(String.format(" and %s = #{%s.%s}", TableTools.fieldToColumn(setting, item), insertEntity.getClass().getSimpleName(), item.getName()));
                }
            });
        }

        String queryStatementSql = queryStatement.toString();
        if(queryStatementSql.length() > 0) {
            queryStatementSql = " WHERE "+queryStatementSql.substring(5);
        }

        map.put(insertEntity.getClass().getSimpleName(), insertEntity);
        return DbType.getPagerSql(String.format(BaseSqlTemplate.SELECT.getSql(), TABLE_NAME, queryStatementSql, orderBy), setting.getDbDriverClass(), pager);
    }
}
