package com.cjxch.supermybatis.base.enu;

/**
 * 属性查询模式
 */
public enum MatchMode {

    FULL_MATCH, //全词匹配
    LEFT_MATCH, //左匹配 比如 like 'abc%'
    RIGHT_MATCH, //右匹配 比如 like '%abc'
    CENTER_MATCH, //中间匹配 比如 like '%abc%'
    GT, //大于
    LT, //小于
    GE, //大于等于
    LE, //小于等于
}
