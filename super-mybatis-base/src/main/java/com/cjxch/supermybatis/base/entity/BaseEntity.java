package com.cjxch.supermybatis.base.entity;


import java.io.Serializable;
import java.lang.annotation.Documented;

/**
 * 实体类基类
 *
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/19/17:08
 */
public abstract class BaseEntity implements Serializable {

//    public static final String ON_SAVE_METHOD_NAME   = "onSave";            // "保存"方法名称
//
//    public static final String ON_UPDATE_METHOD_NAME = "onUpdate";          // "更新"方法名称
//
//    public static final String ON_DELETE_METHOD_NAME = "onDelete";          // "删除"方法名称

    /**
     * 当执行save方法之前，会调用此方法
     * 一般会在此方法内设置一些实体类参数的初始化
     */
    public abstract void onSave();

    /**
     * 当执行update方法之前，会调用此方法
     */
    public abstract void onUpdate();

    /**
     * 当执行delete方法之前，会调用此方法
     */
    public abstract void onDelete();
}
