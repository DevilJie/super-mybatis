package com.cjxch.supermybatis.core.tools;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日志打印工具 -- 已弃用，直接采用mybatis内置的Sql打印
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/22/18:49
 */
@Deprecated
public class SqlPrint {

    public static void print(Map<String, Object> map, String sql){
        return;
//        if(GlobalSetting.getGlobalSetting().getDatabaseSetting().getShowSql()) {
//            StringBuffer buffer = new StringBuffer();
//            try {
//                AtomicInteger index = new AtomicInteger(0);
//                getSubUtilSimple(sql, "\\#\\{", "\\}").stream().forEach(ret -> {
//                    if(ret.contains("[")){
//                        Integer indexNow = Integer.parseInt(getSubUtilSimple(ret, "\\[", "\\]").get(0));
//                        if(indexNow != index.get()){
//                            buffer.append(String.format("\n , #{%s} = %s", ret, getValueByKey(ret, map)));
//                            index.set(indexNow);
//                        }else{
//                            buffer.append(String.format(" , #{%s} = %s", ret, getValueByKey(ret, map)));
//                        }
//                    }else {
//                        buffer.append(String.format(" , #{%s} = %s", ret, getValueByKey(ret, map)));
//                    }
//                });
//            }catch(Exception e){}
//            logger.info("\n\n【 Super-Mybatis 】 Sql调试 start.....\n" +
//                    "【 Super-Mybatis 】 sql语句 ： {} \n" +
//                    "【 Super-Mybatis 】 参数 ：{} \n" +
//                    "【 Super-Mybatis 】 Sql调试 end.....\n", sql, buffer.length() > 2 ? buffer.substring(3) : "");
//        }
    }

    public static Object getValueByKey(String key, Map<String, Object> map){
        if(key.contains(".")){
            String startKey = key.substring(0, key.indexOf("."));
            key = key.substring(key.indexOf(".") + 1);
            if(startKey.contains("[")){
                Integer index = Integer.parseInt(getSubUtilSimple(startKey, "\\[", "\\]").get(0));
                startKey = startKey.substring(0, startKey.indexOf("["));
                List<Object> listMap = (List<Object>)map.get(startKey);
                map = JSON.parseObject(JSON.toJSONString(listMap.get(index)), Map.class);
                return getValueByKey(key, map);
            }else{
                map = JSON.parseObject(JSON.toJSONString(map.get(startKey)), Map.class);
                return getValueByKey(key, map);
            }
        }else{
            return map.get(key);
        }
    }

    public static void main(String[] args) {
        String sql = "select * from a where id = #{id} and bcd = #{bcd} and ccc=#{user.ccc}";
        getSubUtilSimple(sql, "\\#\\{", "\\}").stream().forEach(System.out::println);
        sql = "a[0]";
        System.out.println(sql.contains("["));
        System.out.println(getSubUtilSimple(sql, "\\[", "\\]"));
    }

    public static List<String> getSubUtilSimple(String content, String start, String end) {
        List<String> rets = new ArrayList<>();
        String rgex = start + "(.*?)" + end;
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher m = pattern.matcher(content);
        while (m.find()) {
            rets.add(m.group(1));
        }
        return rets;
    }
}
