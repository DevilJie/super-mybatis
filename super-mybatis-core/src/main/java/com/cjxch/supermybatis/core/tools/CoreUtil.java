package com.cjxch.supermybatis.core.tools;

import com.cjxch.supermybatis.base.exception.SuperMybatisException;
import com.cjxch.supermybatis.core.setting.GlobalSetting;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

public class CoreUtil {

    public static String processEnum(String v, Field field) {
        String returnStr = "";
        for (int i = 0; i < field.getType().getEnumConstants().length; i++) {
            if (v.equals(field.getType().getEnumConstants()[i].toString())) {
                returnStr = i + "";
            }
        }
        return returnStr;
    }

    public static String processEnum(String v, Class c) {
        String returnStr = "";
        for (int i = 0; i < c.getEnumConstants().length; i++) {
            if (v.equals(c.getEnumConstants()[i].toString())) {
                returnStr = i + "";
                break;
            }
        }
        return returnStr;
    }

    /**
     * 处理sql占位符
     *
     * @param param
     * @return
     */
    public static Map<String, Object> processSql(String sql, Map<String, Object> param) {
        param = sortMapByKey(param);
        Map<String, Object> map = new HashMap<String, Object>();
        if (sql.contains("= :") || sql.contains("=:") || sql.contains(", :") || sql.contains(",:")) {
            if (param == null || param.size() == 0) throw new SuperMybatisException("param can't be null");
        }
        if (param != null) for (Map.Entry<String, Object> entry : param.entrySet()) {
            Object object = entry.getValue();
            if (object instanceof String) {
                sql = sql.replace(":" + entry.getKey(), "#{param" + entry.getKey() + ", jdbcType=VARCHAR}");
                map.put("param" + entry.getKey(), object);
            } else if (object instanceof Integer || object instanceof Long || object instanceof Double
                    || object instanceof BigDecimal || object instanceof Float || object instanceof Enum) {
                sql = sql.replace(":" + entry.getKey(), "#{param" + entry.getKey() + ", jdbcType=NUMERIC}");

                String v = object.toString();

                if (object.getClass().isEnum()) {
                    v = processEnum(v, object.getClass());
                }

                map.put("param" + entry.getKey(), v);
            } else if (object instanceof Date) {
                sql = sql.replace(":" + entry.getKey(), "#{param" + entry.getKey() + ", jdbcType=TIMESTAMP}");
                map.put("param" + entry.getKey(), object);
            } else if (object instanceof Object[] || object instanceof List) {
                String random = StrUtil.RadomCode(36, "other");
                Object[] o;
                if (object instanceof List) {
                    o = ((List) object).toArray();
                } else {
                    o = (Object[]) object;
                }
                String sql_ = "(";
                String in = "";
                int n = 0;
                Object v_ = "";
                for (Object obj : o) {
                    v_ = obj;
                    if (obj.getClass().isEnum()) {
                        v_ = processEnum(obj.toString(), obj.getClass());
                    }
                    n++;
                    in += ",#{" + random + "in_" + n + "}";
                    map.put(random + "in_" + n, v_);
                }
                sql_ += in.substring(1) + ")";
                sql = sql.replace(":" + entry.getKey(), sql_);
            } else if (object instanceof Boolean) {
                sql = sql.replace(":" + entry.getKey(), "#{param" + entry.getKey() + ", jdbcType=NUMERIC}");
                map.put("param" + entry.getKey(), ((Boolean) object) ? 1 : 0);
            }
        }
        map.put("SQL", sql);
        return map;
    }

    public static Object process(Object e, Map<String, Object> hashMap) {
        String key;
        for (Map.Entry<String, Object> en : hashMap.entrySet()) {
            key = TableTools.columnToField(GlobalSetting.getGlobalSetting(), en.getKey(), e);

            if (en.getValue() instanceof java.sql.Date) {
                java.sql.Date d = (java.sql.Date) en.getValue();
                ReflectionUtil.invokeSetterMethod(e, key, new Date(d.getTime()));
            } else if (en.getValue() instanceof java.sql.Timestamp) {
                java.sql.Timestamp t = (java.sql.Timestamp) en.getValue();
                ReflectionUtil.invokeSetterMethod(e, key, new Date(t.getTime()));
            } else {
                try {
                    Field field = ReflectionUtil.getDeclaredField(e, key);
                    Class clazz = field.getType();
                    Object v = en.getValue();
                    if (clazz != null) {
                        if (clazz.equals(BigDecimal.class)) {
                            v = new BigDecimal(en.getValue().toString());
                        } else if (clazz.isEnum()) {
                            v = clazz.getEnumConstants()[Integer.parseInt(v.toString())];
                        } else if (clazz.equals(String.class)) {
                            v = en.getValue().toString();
                        } else if (clazz.equals(Double.class) || clazz.equals(double.class)) {
                            v = Double.parseDouble(en.getValue().toString());
                        } else if (clazz.equals(float.class) || clazz.equals(Float.class)) {
                            v = Float.parseFloat(en.getValue().toString());
                        } else if (clazz.equals(Integer.class) || clazz.equals(int.class)) {
                            v = Integer.parseInt(en.getValue().toString());
                        } else if (clazz.equals(Long.class) || clazz.equals(long.class)) {
                            v = Long.parseLong(en.getValue().toString());
                        } else if (clazz.equals(Boolean.class) || clazz.equals(boolean.class)) {
                            Boolean l = null;
                            if (en.getValue() != null) {
                                if (en.getValue().toString().equals("1")) v = true;
                                else if (en.getValue().toString().equals("0")) v = false;
                                else if (en.getValue().toString().equals("false")) v = false;
                                else if (en.getValue().toString().equals("true")) v = true;
                                else v = false;
                            }
                        } else {
                            v = en.getValue();
                        }
                    } else {
                        v = en.getValue();
                    }
                    ReflectionUtil.invokeSetterMethod(e, key, v);

                } catch (Exception ee) {
                }
            }
        }
        return e;
    }

    /**
     * 使用 Map按key进行排序
     * 
     * @param map
     * @return
     */
    public static Map<String, Object> sortMapByKey(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return new HashMap<String, Object>();
        }
        Map<String, Object> sortMap = new TreeMap<String, Object>(new Comparator<String>() {

            public int compare(String str1, String str2) {
                return str2.compareTo(str1);
            }
        });
        sortMap.putAll(map);
        return sortMap;
    }
}
