package com.hsj.supermybatis.core.mapper;

import com.hsj.supermybatis.core.parser.*;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公用Mapper类，提供最基本的CURD功能
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/21/7:18
 */
@Mapper
public interface BaseMapper {

    /**
     * 插入数据
     *
     * @return ID
     */
    @InsertProvider(type= InsertSqlProviderParser.class,method="execute")
    @Options(useGeneratedKeys = true, keyProperty = SqlProviderConstants.PRIMARY_KEY_VALUE)
    void insert(Map<String, Object> paramMap);


    /**
     * 批量插入数据
     *
     * @return ID
     */
    @InsertProvider(type= BatchInsertSqlProviderParser.class,method="execute")
    void batchInsert(Map<String, Object> paramMap);

    /**
     * 根据ID获取实体对象
     *
     * @return 实体对象
     */
    @SelectProvider(type= GetSqlProviderParser.class,method="execute")
    HashMap<String, Object> get(Map<String, Object> paramMap);

    /**
     * 获取所有实体对象集合
     *
     * @return 实体对象集合
     */
    @SelectProvider(type= AllListSqlProviderParser.class,method="execute")
    List<HashMap<String, Object>> getAllList(Map<String, Object> paramMap);


    /**
     * 根据ID删除实体对象
     *
     */
    @DeleteProvider(type= DeleteSqlProviderParser.class,method="execute")
    Long delete(Map<String, Object> paramMap);

    /**
     * 更新实体对象
     *
     */
    @UpdateProvider(type= UpdateSqlProviderParser.class,method="execute")
    Long update(Map<String, Object> paramMap);
//
//    /**
//     * 批量更新实体对象
//     *
//     */
//    void batchUpdate(List<Map<String, Object>> list);

    /**
     * 获取分页数据
     *
     * @param paramMap
     * @return
     */
    @SelectProvider(type= PagerSqlProviderParser.class,method="execute")
    List<HashMap<String, Object>> getPager(Map<String, Object> paramMap);

    /**
     * 获取分页数据
     *
     * @param paramMap
     * @return
     */
    @SelectProvider(type= SelectObjectCountSqlProviderParser.class,method="execute")
    Long getPagerCount(Map<String, Object> paramMap);

//    /**
//     * 自定义查询条件获取对象
//     *
//     * @param paramMap
//     * @return
//     */
//    @SelectProvider(type= SelectObjectCountSqlProviderParser.class,method="execute")
//    HashMap<String, Object> getObject(Map<String, Object> paramMap);

    /**
     * 自定义查询条件获取对象列表
     *
     * @param paramMap
     * @return
     */
    @SelectProvider(type= SelectObjectSqlProviderParser.class,method="execute")
    List<HashMap<String, Object>> getObjectList(Map<String, Object> paramMap);

    /**
     * 自定义查询条件获取对象列表集合大小
     *
     * @param paramMap
     * @return
     */
    @SelectProvider(type= SelectObjectCountSqlProviderParser.class,method="execute")
    Long getObjectListCount(Map<String, Object> paramMap);

    /**
     * 执行sql查询数据，返回对象集合
     */
    List<Object> queryBySql(Map<String, Object> paramMap);

    /**
     * 直接执行sql语句
     */
    Long executeBySql(Map<String, Object> paramMap);
}
