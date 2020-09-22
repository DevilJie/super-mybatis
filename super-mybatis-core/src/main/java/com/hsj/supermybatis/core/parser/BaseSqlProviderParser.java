package com.hsj.supermybatis.core.parser;

import com.hsj.supermybatis.base.annotation.PrimaryKey;
import com.hsj.supermybatis.base.annotation.Table;
import com.hsj.supermybatis.core.provider.BaseSqlProvider;
import com.hsj.supermybatis.core.provider.SqlProviderConstants;
import com.hsj.supermybatis.core.setting.GlobalSetting;
import com.hsj.supermybatis.core.tools.CamelCaseUtils;
import com.hsj.supermybatis.core.tools.SuperMybatisAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/22/15:50
 */
public abstract class BaseSqlProviderParser {

    static Logger logger = LoggerFactory.getLogger(BaseSqlProviderParser.class);

    protected String TABLE_NAME;
    protected Object entity;
    protected GlobalSetting setting = GlobalSetting.getGlobalSetting();
    protected PrimaryKey primaryKey;


//    static {
//        Long startTime = System.currentTimeMillis();
//        logger.info("Super-Mybatis SqlProviderParser Initializing...");
//        List<Class> clazz = LoadClassUtil.getClasssFromPackage("com.hsj.supermybatis.core.parser");
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

    public abstract String execute(Map<String, Object> map);

    public void commonInit(Map<String, Object> map){

        /**
         * 获取表名称
         */
        String className = ((Class)map.get(SqlProviderConstants.CLASS_NAME)).getName();
        SuperMybatisAssert.check((className != null && !"".equals(className)), "className can't be null");
        try{
            entity = Class.forName(className).newInstance();
        }catch(Exception e){
            e.printStackTrace();
            entity = null;
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
        List<Field> fieldList = Arrays.asList(entity.getClass().getDeclaredFields()).stream().
                filter(item -> item.getAnnotation(PrimaryKey.class) != null).collect(Collectors.toList());

        Field primaryKeyField = (fieldList != null && fieldList.size() > 0) ? fieldList.get(0) : null;

        SuperMybatisAssert.check(primaryKeyField != null, "Sorry ! Primary key not found , please check");

        primaryKey = primaryKeyField.getAnnotation(PrimaryKey.class);

        String PRIMARY_KEY = primaryKeyField.getName();
        PRIMARY_KEY = setting.getDatabaseSetting().getCamelModel() ? CamelCaseUtils.processNameWithUnderLine(PRIMARY_KEY) : PRIMARY_KEY;
        if(primaryKey != null && !"".equals(primaryKey.key())) PRIMARY_KEY = primaryKey.key();

        return PRIMARY_KEY;
    }
}