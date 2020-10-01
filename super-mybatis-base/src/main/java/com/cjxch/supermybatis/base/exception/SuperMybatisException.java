package com.cjxch.supermybatis.base.exception;

/**
 * Super-Mybatis 自定义异常类
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/19/17:31
 */
public class SuperMybatisException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public SuperMybatisException(String message) {
        super(message);
    }

    public SuperMybatisException(Throwable throwable) {
        super(throwable);
    }

    public SuperMybatisException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
