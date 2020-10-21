package com.cjxch.supermybatis.core.tools;

import com.cjxch.supermybatis.base.annotation.Column;
import com.cjxch.supermybatis.base.enu.MatchMode;
import com.cjxch.supermybatis.core.setting.GlobalSetting;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 表格操作工具类
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/23/16:04
 */
public class TableTools {

    /**
     * 实体属性名称转数据库列
     * @return
     */
    public static String fieldToColumn(GlobalSetting setting, Field field){
        boolean camelModel = setting.getDatabaseSetting().getCamelModel();
        String ret = (camelModel ? CamelCaseUtils.processNameWithUnderLine(field.getName()) : field.getName());
        Column column = field.getAnnotation(Column.class);
        if(column != null) {
            ret = StringUtils.isEmpty(column.name()) ? ret : field.getAnnotation(Column.class).name();
            if(!StringUtils.isEmpty(column.matchBase())) ret = (camelModel ? CamelCaseUtils.processNameWithUnderLine(column.matchBase()) : column.matchBase());
        }

        return ret;
    }
    /**
     * 实体属性名称转数据库列
     * @return
     */
    public static String fieldNameToColumn(GlobalSetting setting, String fieldName){
        boolean camelModel = setting.getDatabaseSetting().getCamelModel();
        return (camelModel ? CamelCaseUtils.processNameWithUnderLine(fieldName) : fieldName);
    }

    /**
     * 列转属性
     * @param setting
     * @param column
     * @param entity
     * @return
     */
    public static String columnToField(GlobalSetting setting, String column, Object entity){
        boolean camelModel = setting.getDatabaseSetting().getCamelModel();

        String ret = camelModel ? CamelCaseUtils.toCamelCase(column) : column;
        List<Field> fieldList = Arrays.asList(entity.getClass().getDeclaredFields()).stream().
            filter(item ->
                    item.getAnnotation(Column.class) != null &&
                    !StringUtils.isEmpty((item.getAnnotation(Column.class)).name()) &&
                    (item.getAnnotation(Column.class)).name().equals(column)).
            collect(Collectors.toList());
        if(fieldList != null && fieldList.size() > 0) ret = fieldList.get(0).getName();

        return ret;
    }

    public static String processQueryStatemenet(GlobalSetting setting, Field item, Object entity){
        StringBuffer queryStatement = new StringBuffer();
        queryStatement.append(String.format("and %s", TableTools.fieldToColumn(setting, item)));
        Column column = item.getAnnotation(Column.class);
        MatchMode matchMode = column == null ? MatchMode.FULL_MATCH : column.matchMode();
        if(matchMode == null) matchMode = MatchMode.FULL_MATCH;
        switch(matchMode){
            case FULL_MATCH:queryStatement.append(String.format(" = #{%s.%s} ", entity.getClass().getSimpleName(), item.getName())); break;
            case LEFT_MATCH:queryStatement.append(String.format(" like concat(#{%s.%s}, '%%') ", entity.getClass().getSimpleName(), item.getName())); break;
            case RIGHT_MATCH:queryStatement.append(String.format(" like concat('%%', #{%s.%s}) ", entity.getClass().getSimpleName(), item.getName())); break;
            case CENTER_MATCH:queryStatement.append(String.format(" like concat('%%', #{%s.%s}, '%%') ", entity.getClass().getSimpleName(), item.getName())); break;
            case GT:queryStatement.append(String.format(" > #{%s.%s} ", entity.getClass().getSimpleName(), item.getName())); break;
            case LT:queryStatement.append(String.format(" < #{%s.%s} ", entity.getClass().getSimpleName(), item.getName())); break;
            case GE:queryStatement.append(String.format(" >= #{%s.%s} ", entity.getClass().getSimpleName(), item.getName())); break;
            case LE:queryStatement.append(String.format(" <= #{%s.%s} ", entity.getClass().getSimpleName(), item.getName())); break;
            case IN:
                StringBuffer sb = new StringBuffer("");
                AtomicInteger index = new AtomicInteger(0);
                ((List)ReflectionUtil.getFieldValue(entity, item.getName())).forEach(obj -> {
                    sb.append(String.format(",#{%s.%s[%s]}", entity.getClass().getSimpleName(), item.getName(), index.get()));
                    index.addAndGet(1);
                });
                if(sb.toString().length() > 0) queryStatement.append(String.format(" in (%s) ", sb.substring(1)));
                break;
            case NOT_IN:
                sb = new StringBuffer("");
                index = new AtomicInteger(0);
                ((List)ReflectionUtil.getFieldValue(entity, item.getName())).forEach(obj -> {
                    sb.append(String.format(",#{%s.%s[%s]}", entity.getClass().getSimpleName(), item.getName(), index.get()));
                    index.addAndGet(1);
                });
                if(sb.toString().length() > 0) queryStatement.append(String.format(" not in (%s) ", sb.substring(1)));
                break;
        }
        return queryStatement.toString();
    }
}
