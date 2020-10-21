package com.cjxch.supermybatis.cache.base.aspect;

import com.cjxch.supermybatis.cache.base.annotation.CacheEvict;
import com.cjxch.supermybatis.cache.base.annotation.CacheSet;
import com.cjxch.supermybatis.base.annotation.PrimaryKey;
import com.cjxch.supermybatis.base.bean.BaseAspectConstants;
import com.cjxch.supermybatis.base.exception.SuperMybatisException;
import com.cjxch.supermybatis.cache.base.core.ParamNameParser;
import com.cjxch.supermybatis.cache.base.core.ReflectParamNames;
import com.cjxch.supermybatis.cache.base.core.SpelParser;
import com.cjxch.supermybatis.cache.base.core.SuperMybatisCacheConstants;
import com.cjxch.supermybatis.cache.base.log.PrintLog;
import com.cjxch.supermybatis.core.tools.ReflectionUtil;
import com.cjxch.supermybatis.core.tools.SuperMybatisAssert;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/10/03/7:03
 * @Email: cjxch@cjxch.com
 */
public class BaseAspect {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    protected ProceedingJoinPoint pjp;

    protected String className;

    protected boolean canGo;

    protected Long expires;

    protected Method method;

    public String processKeySet() throws Exception {
        Method method = loadInvokeMethod();
        CacheSet set = method.getAnnotation(CacheSet.class);
        if(set == null){
            PrintLog.DEBUG(logger, "The current call method is " + method.getName() + " However, no @CacheSet annotation was detected");
            throw new SuperMybatisException(SuperMybatisCacheConstants.CONTINUE_FLAG);
        }

        expires = set.expires();
        if(expires == null || expires == 0l){
            PrintLog.DEBUG(logger, "The current call method is " + method.getName() + ", No expires time settings were found, Ready to search from dbCacheSetting");
            PrintLog.DEBUG(logger, "@CacheEnable did not find an expires time setting, Ready to search from GlobalSettings");
            expires = SuperMybatisCacheConstants.dbCacheSetting.getExpires();
        }

        PrintLog.DEBUG(logger, "The current call method is " + method.getName() + " @CacheSet annotations found");

        return processKey(set.key(), set.condition(), set.group());
    }

    public String processKeyEvict() throws Exception {
        Method method = loadInvokeMethod();
        CacheEvict evict = method.getAnnotation(CacheEvict.class);
        if(evict == null){
            PrintLog.DEBUG(logger, "The current call method is " + method.getName() + " However, no @CacheEvict annotation was detected");
            throw new SuperMybatisException(SuperMybatisCacheConstants.CONTINUE_FLAG);
        }

        PrintLog.DEBUG(logger, "The current call method is " + method.getName() + " @CacheEvict annotations found");

        return processKey(evict.key(), evict.group());
    }

    private String processKey(String k, String group){
        return processKey(k, null, group);
    }

    private String processKey(String k, String condition, String group){
        StringBuffer key = new StringBuffer(k);
        if(!StringUtils.isEmpty(group)) key = key.insert(0, "'"+group+":'+");
        String entityClass = className.substring(className.lastIndexOf(".")+1);
        if(StringUtils.isEmpty(key.toString())){
            key = new StringBuffer("");
            StringBuffer finalKey = key;
            Arrays.asList(pjp.getArgs()).forEach(i -> {
                finalKey.append(":" + i.toString());
            });
            PrintLog.DEBUG(logger, "current cache key is " + entityClass + key.toString());
            return entityClass + key.toString();
        }else{
            String retStr = key.toString();
//            for(String place : getPlace(key.toString(), "\\{", "\\}")){
//                retStr = retStr.replace("{"+place+"}", convertToVal(className, place, pjp.getArgs()));
//            }

            String[] paramNames = ParamNameParser.get(retStr);
            if (paramNames==null){
//          反射得到形参名称
                paramNames = ReflectParamNames.getNames(pjp.getTarget().getClass().getName(), pjp.getSignature().getName());
                ParamNameParser.put(retStr, paramNames);
            }

            retStr = SpelParser.getKey(retStr, condition, paramNames, pjp.getArgs());
            SuperMybatisAssert.check(retStr != null, SuperMybatisCacheConstants.CONTINUE_FLAG);

            PrintLog.DEBUG(logger, "current cache key is " + retStr);
            return retStr;
        }
    }

    private Method loadInvokeMethod() throws Exception{
        className = pjp.getTarget().getClass().getName();

        PrintLog.DEBUG(logger, className +" Database caching is enabled......");

        String methodName = pjp.getSignature().getName();

        Method[] methods = Class.forName(pjp.getSignature().getDeclaringTypeName()).getDeclaredMethods();

        Object[] paramsType = new Object[0];
        if(pjp.getArgs() != null) {
            int i = 0;
            paramsType = new Object[pjp.getArgs().length];
            for(Object obj : pjp.getArgs()){
                paramsType[i] = obj;
                i++;
            }
        }


        Object[] finalParamsType = paramsType;
        List<Method> methodArrays = Arrays.asList(methods).stream().filter(i -> {
            boolean nameCons = i.getName().equals(methodName);
            if(!nameCons) return false;
            boolean paramCount = i.getParameterCount() == finalParamsType.length;
            if(!paramCount) return false;
            int sameNum = 0;
            for(int b = 0 ; b < i.getGenericParameterTypes().length ; b++){
                try {
                    String typeName1 = finalParamsType[b].getClass().getTypeName();
                    String typeName2 = i.getGenericParameterTypes()[b].getTypeName();
                    Class class1 = Class.forName(typeName1);
                    Class class2;
                    if(typeName2.equals("T")) class2 = new Object().getClass();
                    else class2 = Class.forName(typeName2);

                    if (typeName1.equals(typeName2) || class2.isAssignableFrom(class1)) sameNum++;
                }catch(Exception e){}
            }

            if(sameNum != i.getParameterCount()) return false;

            return true;
        }).collect(Collectors.toList());

        method = methodArrays.get(0);
        return method;
    }


    /**
     * 占位符转换为值
     * @return
     */
    public static String convertToVal(String className, String place, Object[] args){
        if(place.indexOf(".") == -1){
            try {
                if(place.equals(BaseAspectConstants.CLASS_NAME)) return className.substring(className.lastIndexOf(".")+1);
                return String.valueOf(args[Integer.parseInt(place) - 1]);
            }catch(Exception e){
                return null;
            }
        }else{
            try {
                Integer index = Integer.parseInt(place.substring(0, place.indexOf("."))) -1;
                Object arg = args[index];
                return convertToVal(place.substring(place.indexOf(".")+1), arg);
            }catch(Exception e){
                return null;
            }
        }
    }

    public static String convertToVal(String place, Object obj){
        if(place.indexOf(".") == -1){
            if(place.equals(BaseAspectConstants.PRIMARY_KEY)){
                AtomicReference<String> primaryKey = new AtomicReference<>("");
                Arrays.asList(obj.getClass().getDeclaredFields()).stream().filter(f -> f.getAnnotation(PrimaryKey.class) != null).forEach(f -> {
                    primaryKey.set(f.getName());
                });
                place = primaryKey.get();
            }
            return String.valueOf(ReflectionUtil.getFieldValue(obj, place));
        }else{
            obj = ReflectionUtil.getFieldValue(obj, place.substring(place.indexOf(".")));
            return convertToVal(place.substring(place.indexOf(".") + 1), obj);
        }
    }

    /**
     *  正则获取两个符号之间的内容   
     */
    public static List<String> getPlace(String content, String start, String end) {
        List<String> ret = new ArrayList<>();
        String rgex = start + "(.*?)" + end;
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher m = pattern.matcher(content);
        while (m.find()) {
            ret.add(m.group(1));
        }
        return ret;
    }
}
