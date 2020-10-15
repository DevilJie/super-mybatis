package com.cjxch.supermybatis.core.mapper;

import com.cjxch.supermybatis.core.parser.*;
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
     * 根据属性查询
     * @return
     */
    @SelectProvider(type= SelectObjectSqlProviderParser.class,method="execute")
    List<HashMap<String, Object>> getListByColumn(Map<String, Object> paramMap);

    /**
     * 根据属性查询
     * @return
     */
    @SelectProvider(type= SelectObjectCountSqlProviderParser.class,method="execute")
    Long getListCountByColumn(Map<String, Object> paramMap);



    /**
     * 自定义查询条件获取对象列表集合大小
     *
     * @param paramMap
     * @return
     */
    @SelectProvider(type= SelectObjectCountSqlProviderParser.class,method="execute")
    Long getObjectListCount(Map<String, Object> paramMap);

    /**
     * 个性化sql执行，获取列表
     */
    @SelectProvider(type= SelectBySqlProviderParser.class,method="execute")
    List<HashMap<String, Object>> queryBySql(Map<String, Object> paramMap);

    /**
     * 个性化sql执行，分页查询
     */
    @SelectProvider(type= SelectPagerBySqlProviderParser.class,method="execute")
    List<HashMap<String, Object>> queryPagerBySql(Map<String, Object> paramMap);

    /**
     * 个性化sql执行，分页查询
     */
    @SelectProvider(type= SelectPagerCountBySqlProviderParser.class,method="execute")
    Long queryPagerCountBySql(Map<String, Object> paramMap);

    /**
     * 个性化sql执行，更新语句
     */
    @UpdateProvider(type= UpdateBySqlProviderParser.class,method="execute")
    Long updateBySql(Map<String, Object> paramMap);
}
