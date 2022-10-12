package com.cjxch.supermybatis.generate.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Czy
 * @Description //TODO $
 * @Date $ $
 * @Param $
 * @return $
 **/
public class DBUtil {

    private static Map<String, String> propertiesToDbMap = new HashMap<>();

    static {
        propertiesToDbMap.put("Integer", "int");
        propertiesToDbMap.put("int", "int");
        propertiesToDbMap.put("Short", "tinyint");
        propertiesToDbMap.put("Long", "bigint");
        propertiesToDbMap.put("long", "bigint");
        propertiesToDbMap.put("BigDecimal", "decimal(19,2)");
        propertiesToDbMap.put("Double", "double");
        propertiesToDbMap.put("double", "double");
        propertiesToDbMap.put("Float", "float");
        propertiesToDbMap.put("float", "float");
        propertiesToDbMap.put("Boolean", "bit");
        propertiesToDbMap.put("boolean", "bit");
        propertiesToDbMap.put("Timestamp", "datetime");
        propertiesToDbMap.put("Date", "datetime");
        propertiesToDbMap.put("String", "VARCHAR(255)");
        propertiesToDbMap.put("Enum", "VARCHAR(20)");
    }

    public static String propertiesToDb(String properties){
        return propertiesToDbMap.get(properties);
    }
}
