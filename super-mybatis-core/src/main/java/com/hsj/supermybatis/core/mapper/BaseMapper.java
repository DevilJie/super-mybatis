package com.hsj.supermybatis.core.mapper;

import com.hsj.supermybatis.core.provider.GetSqlProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

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
     * 根据ID获取实体对象
     *
     * @return 实体对象
     */
    @SelectProvider(type=GetSqlProvider.class,method="execute")
    public HashMap<String, Object> get(Map<String, Object> paramMap);

    /**
     * 获取所有实体对象集合
     *
     * @return 实体对象集合
     */
    public List<HashMap<String, Object>> getAllList(Map<String, Object> paramMap);

    /**
     * 保存实体对象
     *
     * @return ID
     */
    public void save(Map<String, Object> paramMap);


    /**
     * 保存实体对象
     *
     * @return ID
     */
    public void saveAuto(Map<String, Object> paramMap);

    /**
     * 批量保存实体对象
     *
     * @return ID
     */
    public void batchSave(Map<String, Object> paramMap);

    /**
     * 更新实体对象
     *
     */
    public void update(Map<String, Object> paramMap);

    /**
     * 批量更新实体对象
     *
     */
    public void batchUpdate(List<Map<String, Object>> list);

    /**
     * 根据ID删除实体对象
     *
     */
    public void delete(Map<String, Object> paramMap);

    /**
     * 获取分页数据
     *
     * @param paramMap
     * @return
     */
    public List<HashMap<String, Object>> getPager(Map<String, Object> paramMap);

    /**
     * 获取分页数据
     *
     * @param paramMap
     * @return
     */
    public Long getPagerCount(Map<String, Object> paramMap);

    /**
     * 获取分页数据
     *
     * @param paramMap
     * @return
     */
    public List<HashMap<String, Object>> getPagerBySql(Map<String, Object> paramMap);

    /**
     * 获取分页数据
     *
     * @param paramMap
     * @return
     */
    public Long getPagerCountBySql(Map<String, Object> paramMap);

    /**
     * 自定义查询条件获取对象
     *
     * @param paramMap
     * @return
     */
    public HashMap<String, Object> getObject(Map<String, Object> paramMap);

    /**
     * 自定义查询条件获取对象列表
     *
     * @param paramMap
     * @return
     */
    public List<HashMap<String, Object>> getObjectList(Map<String, Object> paramMap);

    /**
     * 自定义查询条件获取对象列表集合大小
     *
     * @param paramMap
     * @return
     */
    public Long getObjectListCount(Map<String, Object> paramMap);

    /**
     * 执行sql查询数据，返回对象集合
     */
    public List<Object> queryBySql(Map<String, Object> paramMap);

    /**
     * 直接执行sql语句
     */
    public Long executeBySql(Map<String, Object> paramMap);
}
