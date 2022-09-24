package com.cjxch.supermybatis.core.parser;

import com.cjxch.supermybatis.base.annotation.Column;
import com.cjxch.supermybatis.base.annotation.PrimaryKey;
import com.cjxch.supermybatis.base.enu.BaseSqlTemplate;
import com.cjxch.supermybatis.core.setting.GlobalSetting;
import com.cjxch.supermybatis.core.tools.ReflectionUtil;
import com.cjxch.supermybatis.core.tools.SuperMybatisAssert;
import com.cjxch.supermybatis.core.tools.TableTools;
import com.cjxch.supermybatis.tenant.SuperMybatisTenant;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Modifier;
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

        Object updateEntity = map.get(SqlProviderConstants.ENTITY);
        String primaryKey = commonPrimaryKey(map);
        Serializable primaryKeyVal = (Serializable)ReflectionUtil.invokeGetterMethod(updateEntity, primaryKeyField.getName());
        SuperMybatisAssert.check(!StringUtils.isEmpty(primaryKeyVal), "Primary key value cannot be empty");

        StringBuffer setVal = new StringBuffer();

        AtomicBoolean updateAnyway = new AtomicBoolean(false);
        Arrays.asList(ReflectionUtil.getDeclaredField(updateEntity)).stream().
                filter(item -> item.getAnnotation(PrimaryKey.class) == null
                        && !Modifier.isFinal(item.getModifiers())
                        && !Modifier.isStatic(item.getModifiers())).forEach(item -> {

            if(GlobalSetting.getGlobalSetting().getDatabaseSetting().getTenant() && SuperMybatisTenant.currentTenant() != null){
                if(TableTools.fieldToColumn(setting, item).equals(SuperMybatisTenant.currentTenant().tenantColumn())) return;
            }

            if(item.getAnnotation(Column.class) != null) updateAnyway.set(item.getAnnotation(Column.class).updateAnyway());
            else updateAnyway.set(setting.getDatabaseSetting().getUpdateAnyway());
            Object val = ReflectionUtil.invokeGetterMethod(updateEntity, item.getName());
            if(updateAnyway.get() || (val != null && !StringUtils.isEmpty(String.valueOf(val))))
                setVal.append(String.format(",%s = #{%s.%s}", TableTools.fieldToColumn(setting, item), updateEntity.getClass().getSimpleName(), item.getName()));
        });

        map.put(updateEntity.getClass().getSimpleName(), updateEntity);

        SuperMybatisAssert.check(setVal.length() > 0, "Update property cannot be empty");

        return commonTenantProcess(map, String.format(BaseSqlTemplate.UPDATE.getSql(), TABLE_NAME, "SET "+setVal.substring(1), primaryKey, primaryKeyVal));
    }
}
