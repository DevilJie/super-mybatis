package com.hsj.supermybatis.core.tools;

import com.hsj.supermybatis.core.setting.GlobalSetting;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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

    public static void main(String[] args) {
        String sql = "= :ABC";
        System.out.println(sql.contains("= :"));
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
                    Field field = ReflectionUtils.getDeclaredField(e, key);
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
