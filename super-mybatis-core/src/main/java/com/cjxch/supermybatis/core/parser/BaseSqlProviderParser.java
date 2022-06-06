package com.cjxch.supermybatis.core.parser;

import com.cjxch.supermybatis.base.annotation.Column;
import com.cjxch.supermybatis.base.annotation.PrimaryKey;
import com.cjxch.supermybatis.base.annotation.Table;
import com.cjxch.supermybatis.core.setting.GlobalSetting;
import com.cjxch.supermybatis.core.tools.*;
import com.cjxch.supermybatis.core.tools.query.*;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/22/15:50
 */
public abstract class BaseSqlProviderParser {

    protected String TABLE_NAME;
    protected Object entity;
    protected GlobalSetting setting = GlobalSetting.getGlobalSetting();
    protected PrimaryKey primaryKey;
    protected Field primaryKeyField;


//    static {
//        Long startTime = System.currentTimeMillis();
//        logger.info("Super-Mybatis SqlProviderParser Initializing...");
//        List<Class> clazz = LoadClassUtil.getClasssFromPackage("com.cjxch.supermybatis.core.parser");
//        clazz.stream().filter(item -> item.getAnnotation(SuperMybatisSqlProvider.class) != null).forEach(item -> {
//            try {
//                cachedParser.put(((SuperMybatisSqlProvider)item.getAnnotation(SuperMybatisSqlProvider.class)).value().getName(), (BaseSqlProviderParser)item.newInstance());
//                logger.info(String.format("Super-Mybatis SqlProviderParser[ %s ] Initialization completed", item.getSimpleName()));
//            } catch (Exception e) {
//            }
//        });
//        Long endTime = System.currentTimeMillis();
//        logger.info(String.format("Super-Mybatis SqlProviderParser Initialization completed withd %s ms", endTime - startTime));
//    }


    /**
     * 缓存现在所有的SQL处理器
     */
    public static Map<String, BaseSqlProviderParser> cachedParser = new HashMap<>();

    /**
     * 执行方法
     * @param parameter
     * @return
     */
    public abstract String execute(Map<String, Object> parameter);

    public void commonInit(Map<String, Object> map){

        entity = map.get(SqlProviderConstants.ENTITY);
        if(entity == null) {
            /**
             * 获取表名称
             */
            String className = ((Class) map.get(SqlProviderConstants.CLASS_NAME)).getName();
            SuperMybatisAssert.check((className != null && !"".equals(className)), "className can't be null");
            try {
                entity = Class.forName(className).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                entity = null;
            }
        }
        SuperMybatisAssert.check(entity != null, "unknown className");

        Table table = entity.getClass().getAnnotation(Table.class);
        TABLE_NAME = entity.getClass().getSimpleName();

        TABLE_NAME = setting.getDatabaseSetting().getCamelModel() ? CamelCaseUtils.processNameWithUnderLine(TABLE_NAME) : TABLE_NAME;
        /**
         * 如果配置了全局表前缀，则需要拼接
         */
        TABLE_NAME = (!StringUtils.isEmpty(setting.getDatabaseSetting().getTablePerfix()) ? setting.getDatabaseSetting().getTablePerfix() : "") + TABLE_NAME;

        /**
         * 如果配置了 @Table 注解，并且设置了表名称，则优先使用
         */
        if(table != null && !"".equals(table.value())) TABLE_NAME = table.value();
    }

    public String commonPrimaryKey(Map<String, Object> map){
        /**
         * TODO 查找实体类中，配置了主键注解 {@link PrimaryKey} 的属性，目前暂时只支持数据表单主键，后期完善
         */
        List<Field> fieldList = Arrays.asList(ReflectionUtil.getDeclaredField(entity)).stream().
                filter(item -> item.getAnnotation(PrimaryKey.class) != null).collect(Collectors.toList());

        primaryKeyField = (fieldList != null && fieldList.size() > 0) ? fieldList.get(0) : null;

        SuperMybatisAssert.check(primaryKeyField != null, "Sorry ! Primary key not found , please check");

        primaryKey = primaryKeyField.getAnnotation(PrimaryKey.class);

        String PRIMARY_KEY = TableTools.fieldToColumn(setting, primaryKeyField);
        if(primaryKey != null && !"".equals(primaryKey.key())) PRIMARY_KEY = primaryKey.key();

        return PRIMARY_KEY;
    }

    protected String processSearchParam(Map<String, Object> map) {
        StringBuffer queryStatement = new StringBuffer();

        Object queryEntity = map.get(SqlProviderConstants.ENTITY);
        SmCriteria smCriteria = (SmCriteria)map.get(SqlProviderConstants.SM_CRITERIA);
        String queryColumn = String.valueOf(map.get(SqlProviderConstants.QUERY_COLUMN));
        Object queryVal = String.valueOf(map.get(SqlProviderConstants.QUERY_VAL));


        SuperMybatisAssert.check(queryEntity != null || (queryColumn != null && queryVal != null), "You need to pass at least one query parameter");

        if(queryEntity != null) {
            Arrays.asList(ReflectionUtil.getDeclaredField(queryEntity)).stream().forEach(item -> {
                Column c = item.getAnnotation(Column.class);
                if (c != null && c.ignored() && StringUtils.isEmpty(c.matchBase())) {
                    //TODO 后续其他处理？？？
                } else {
                    if (!StringUtils.isEmpty(ReflectionUtil.getFieldValue(queryEntity, item.getName()))) {
                        queryStatement.append(TableTools.processQueryStatemenet(setting, item, queryEntity));
                    }
                }
            });
            map.put(queryEntity.getClass().getSimpleName(), queryEntity);
        }else if(smCriteria != null){
            List<Object> criterionList = smCriteria.getCriterionList();
            if(criterionList != null && criterionList.size() > 0){
                queryStatement.append(" and");
                criterionList.forEach(item -> {
                    CriteriaConnector connector;
                    CriteriaType type;
                    SmCriterion smc;
                    SmCriterionArrays sca;
                    if(item instanceof SmCriterion){
                        smc = (SmCriterion)item;
                        connector = smc.getConnector() == null ? CriteriaConnector.AND : smc.getConnector();
                        type = smc.getType();
                        String _Key = UUID.randomUUID().toString().replace("-", "");
                        if(criterionList.indexOf(item) != 0) queryStatement.append(" " + connector.name());
                        queryStatement.append(String.format(" %s%s#{%s}", TableTools.fieldNameToColumn(setting, smc.getKey()), type.getOperator(), _Key));
                        map.put(_Key, smc.getValue());
                    }else if(item instanceof SmCriterionArrays){
                        sca = (SmCriterionArrays)item;
                        connector = sca.getConnector() == null ? CriteriaConnector.AND : sca.getConnector();
                        if(criterionList.indexOf(item) != 0) queryStatement.append(" " + connector.name());
                        queryStatement.append(" (");
                        sca.getSmCriterionList().forEach(_item -> {
                            CriteriaConnector _connector;
                            CriteriaType _type;
                            _connector = _item.getConnector() == null ? CriteriaConnector.AND : _item.getConnector();
                            _type = _item.getType();
                            String _Key = UUID.randomUUID().toString().replace("-", "");
                            if(sca.getSmCriterionList().indexOf(_item) != 0) queryStatement.append(" " + _connector.name());
                            if(_type == CriteriaType.in){
                                String random = StrUtil.RadomCode(36, "other");
                                Object[] o;
                                if (_item.getValue() instanceof List) {
                                    o = ((List) _item.getValue()).toArray();
                                } else {
                                    o = (Object[]) _item.getValue();
                                }
                                String sql_ = "(";
                                String in = "";
                                int n = 0;
                                Object v_ = "";
                                for (Object obj : o) {
                                    v_ = obj;
                                    n++;
                                    in += ",#{" + random + "in_" + n + "}";
                                    map.put(random + "in_" + n, v_);
                                }
                                sql_ += in.substring(1) + ")";
                                queryStatement.append(String.format(" %s%s%s ", TableTools.fieldNameToColumn(setting, _item.getKey()), _type.getOperator(), sql_));
                            }else{
                                queryStatement.append(String.format(" %s%s#{%s}", TableTools.fieldNameToColumn(setting, _item.getKey()), _type.getOperator(), _Key));
                            }
                            map.put(_Key, _item.getValue());
                        });
                        queryStatement.append(" )");
                    }
                });
            }
        }else{
            queryStatement.append(String.format(" and %s = #{%s}", TableTools.fieldNameToColumn(setting, queryColumn), SqlProviderConstants.QUERY_VAL));
        }

        String queryStatementSql = queryStatement.toString();
        if(queryStatementSql.length() > 0) {
            queryStatementSql = " WHERE "+queryStatementSql.substring(4);
        }
        return queryStatementSql;
    }
}