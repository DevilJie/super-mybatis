package com.hsj.supermybatis.core.parser;

import com.hsj.supermybatis.base.annotation.Column;
import com.hsj.supermybatis.base.annotation.PrimaryKey;
import com.hsj.supermybatis.base.enu.BaseSqlTemplate;
import com.hsj.supermybatis.core.tools.ReflectionUtil;
import com.hsj.supermybatis.core.tools.SuperMybatisAssert;
import com.hsj.supermybatis.core.tools.TableTools;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 更新Sql
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/22/15:46
 */
public class UpdateSqlProviderParser extends BaseSqlProviderParser {

    @Override
    public String execute(Map<String, Object> map) {
        /**
         * 公共初始化
         */
        commonInit(map);

        Object insertEntity = map.get(SqlProviderConstants.ENTITY);
        String primaryKey = commonPrimaryKey(map);
        Serializable primaryKeyVal = (Serializable)ReflectionUtil.invokeGetterMethod(insertEntity, primaryKeyField.getName());
        SuperMybatisAssert.check(!StringUtils.isEmpty(primaryKeyVal), "Primary key value cannot be empty");

        StringBuffer setVal = new StringBuffer();

        AtomicBoolean updateAnyway = new AtomicBoolean(false);
        Arrays.asList(insertEntity.getClass().getDeclaredFields()).stream().
                filter(item -> item.getAnnotation(PrimaryKey.class) == null).forEach(item -> {
            if(item.getAnnotation(Column.class) != null) updateAnyway.set(item.getAnnotation(Column.class).updateAnyway());
            else updateAnyway.set(setting.getDatabaseSetting().getUpdateAnyway());
            Object val = ReflectionUtil.invokeGetterMethod(insertEntity, item.getName());
            if(updateAnyway.get() || (val != null && !StringUtils.isEmpty(String.valueOf(val))))
                setVal.append(String.format(",%s = #{%s.%s}", TableTools.fieldToColumn(setting, item), insertEntity.getClass().getSimpleName(), item.getName()));
        });

        map.put(insertEntity.getClass().getSimpleName(), insertEntity);

        SuperMybatisAssert.check(setVal.length() > 0, "Update property cannot be empty");

        return String.format(BaseSqlTemplate.UPDATE.getSql(), TABLE_NAME, "SET "+setVal.substring(1), primaryKey, primaryKeyVal);
    }
}
