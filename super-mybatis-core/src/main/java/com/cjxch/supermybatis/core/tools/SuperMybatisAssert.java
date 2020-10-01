package com.cjxch.supermybatis.core.tools;

import com.cjxch.supermybatis.base.exception.SuperMybatisException;

/**
 * 断言参数校验
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/21/8:47
 */
public class SuperMybatisAssert {

    /**
     * 参数校验，如果条件不成立，则抛出异常
     * @param condition 条件
     * @param errMsg 异常信息
     * @return
     */
    public static void check(boolean condition, String errMsg){
        if(!condition) throw new SuperMybatisException(errMsg);
    }
}
