package com.hsj.supermybatis.core.tools;

import com.hsj.supermybatis.base.annotation.Column;
import com.hsj.supermybatis.core.setting.GlobalSetting;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
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
        if(field.getAnnotation(Column.class) != null)
            ret = StringUtils.isEmpty(field.getAnnotation(Column.class).name()) ? ret : field.getAnnotation(Column.class).name();

        return ret;
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
}
