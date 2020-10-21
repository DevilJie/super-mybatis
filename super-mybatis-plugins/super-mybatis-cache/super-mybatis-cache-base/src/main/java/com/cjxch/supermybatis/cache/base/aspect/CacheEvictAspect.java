package com.cjxch.supermybatis.cache.base.aspect;

import com.cjxch.supermybatis.cache.base.core.SuperMybatisCacheConstants;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/10/02/17:22
 * @Email: cjxch@cjxch.com
 */
@Aspect
@Component //标识容器受管的Bean对象
public class CacheEvictAspect extends BaseAspect{

    @Pointcut("@annotation(com.cjxch.supermybatis.cache.base.annotation.CacheEvict)")
    private void aspectJMethod(){};

    @Before("aspectJMethod()")
    public void before(JoinPoint joinPoint){
    }

    /**
     * 后置通知
     */
    @AfterReturning(value = "aspectJMethod()", returning = "returnVal")
    public void afterReturn(String returnVal){
    }

    /**
     * 环绕通知
     */
    @Around("aspectJMethod()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        this.pjp = pjp;
        String key = "";
        try {
            key = processKeyEvict();
        }catch(Exception e){}
        Object returnVal = pjp.proceed();

        if(SuperMybatisCacheConstants.superMybatisCache != null) {
            SuperMybatisCacheConstants.superMybatisCache.delete(key);
        }

        return returnVal;
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
    public void after(){
    }
}
