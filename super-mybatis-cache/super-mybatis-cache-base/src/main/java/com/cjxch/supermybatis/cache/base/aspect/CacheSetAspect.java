package com.cjxch.supermybatis.cache.base.aspect;

import com.alibaba.fastjson.JSON;
import com.cjxch.supermybatis.base.annotation.CacheEnable;
import com.cjxch.supermybatis.base.annotation.CacheSet;
import com.cjxch.supermybatis.base.exception.SuperMybatisException;
import com.cjxch.supermybatis.cache.base.core.SuperMybatisCacheConstants;
import com.cjxch.supermybatis.cache.base.log.PrintLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/10/02/17:22
 * @Email: cjxch@cjxch.com
 */
@Aspect
@Component //标识容器受管的Bean对象
public class CacheSetAspect extends BaseAspect {

    /**
     * 缓存key
     */
    private String key;

    private Object returnVal;

    @Pointcut("@annotation(com.cjxch.supermybatis.base.annotation.CacheSet)")
    private void aspectJMethod(){};

    @Before("aspectJMethod()")
    public void before(JoinPoint joinPoint) throws Exception {
    }

    /**
     * 后置通知
     */
    @AfterReturning (pointcut="aspectJMethod()", returning="result")
    public void afterReturn(Object result){
        if(SuperMybatisCacheConstants.superMybatisCache == null) return;
        if(result == null) return;

        Map<String, Object> hset = new HashMap<>();
        hset.put(SuperMybatisCacheConstants.HSET_ARGS, JSON.toJSONString(pjp.getArgs()));
        hset.put(SuperMybatisCacheConstants.HSET_CLASS, className);
        hset.put(SuperMybatisCacheConstants.HSET_METHOD, method.getName());
        hset.put(SuperMybatisCacheConstants.HSET_RESULT, result);

        SuperMybatisCacheConstants.superMybatisCache.set(key, hset);
        if(expires != null && expires > 0l) SuperMybatisCacheConstants.superMybatisCache.expires(key, expires);

        PrintLog.DEBUG(logger,"Cache successful, Key is " + key + ((expires != null && expires > 0l) ? " expires is " + expires : ""));
    }

    /**
     * 环绕通知
     */
    @Around("aspectJMethod()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        this.pjp = pjp;
        if(SuperMybatisCacheConstants.superMybatisCache == null) return pjp.proceed();
        try {
            key = processKeySet();
            returnVal = SuperMybatisCacheConstants.superMybatisCache.get(key, SuperMybatisCacheConstants.HSET_RESULT);
            if(returnVal != null){
                PrintLog.DEBUG(logger, "Cache hit, the key is " + key);
                return returnVal;
            }
        }catch(SuperMybatisException e){
            if(e.getMessage().equals("continue")) {
                return pjp.proceed();
            }
        }
        return pjp.proceed();
    }

    /**
     * 异常通知
     * 通过pointcut指定切入点
     */
    @AfterThrowing(pointcut = "aspectJMethod()", throwing = "e")
    public void throwableAdvice(Throwable e){
    }

    /**
     * 最终通知
     */
    @After("aspectJMethod()")
    public void after(JoinPoint joinPoint){
    }
}
