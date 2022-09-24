package com.cjxch.supermybatis.core.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;

/**
 * Dynamic datasource handler
 * @author: Czy
 * @date: 2022/9/24 15:28
 **/
public class SuperMybatisDatasourceHandler {

    private static ThreadLocal<String> handlerThredLocal = new ThreadLocal<String>();

    /**
     * Use aop dynamic setting datasouce
     * @author: Czy
     * @date: 2022/9/24 15:27
     * @param: [currentDs]
     * @return: void
     **/
    public static void setCurrentDataSource(String currentDs){
        handlerThredLocal.set(currentDs);
    }

    /**
     * Get current useing datasource
     * @author: Czy
     * @date: 2022/9/24 15:27
     * @param: []
     * @return: current useing datasource
     **/
    public static String getCurrentDataSource(){
        return handlerThredLocal.get();
    }

    /**
     * Useing default
     * @author: Czy
     * @date: 2022/9/24 15:29
     * @param: []
     * @return: void
     **/
    public static void clear(){
        handlerThredLocal.remove();
    }
}
