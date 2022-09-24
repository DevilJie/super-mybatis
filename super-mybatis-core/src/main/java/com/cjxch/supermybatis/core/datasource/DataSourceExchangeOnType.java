package com.cjxch.supermybatis.core.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class DataSourceExchangeOnType {

    @Before("@within(ChooseDataSource) || @annotation(ChooseDataSource)")
    public void before(JoinPoint joinPoint) {
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();
        boolean isMethodAop = method.isAnnotationPresent(ChooseDataSource.class);
        if (isMethodAop) {
            ChooseDataSource datasource = method.getAnnotation(ChooseDataSource.class);
            SuperMybatisDatasourceHandler.setCurrentDataSource(datasource.value());
        } else {
            if (joinPoint.getTarget().getClass().isAnnotationPresent(ChooseDataSource.class)) {
                ChooseDataSource datasource = joinPoint.getTarget().getClass().getAnnotation(ChooseDataSource.class);
                SuperMybatisDatasourceHandler.setCurrentDataSource(datasource.value());
            }
        }
    }

    @Before("@annotation(ChooseDataSource),@within(ChooseDataSource)")
    public void after() {
        SuperMybatisDatasourceHandler.clear();
    }
}