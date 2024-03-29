package com.cjxch.supermybatis.base.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Super-Mybatis提供的分页插件
 *
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/19/17:28
 */
public class Pager<T> implements Serializable {

    private static final long   serialVersionUID = -2707171505887622174L;

    // 排序方式（递增、递减）
    public enum Order {
        asc, desc
    }

    private int     pageNumber = 1;  // 当前页码

    private int     pageSize   = 20; // 每页记录数

    private String  searchBy;        // 查找字段

    private String  keyword;         // 查找关键字

    private String  orderBy;         // 排序字段

    private Order   order;           // 排序方式

    private int     totalCount;      // 总记录数

    private List<?> result;          // 返回结果

    // 获取总页数
    public int getPageCount() {
        int pageCount = totalCount / pageSize;
        if (totalCount % pageSize > 0) {
            pageCount++;
        }
        return pageCount;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        if (pageNumber < 1) {
            pageNumber = 1;
        }
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize < 1) {
            pageSize = 1;
        }
        this.pageSize = pageSize;
    }

    public String getSearchBy() {
        return searchBy;
    }

    public void setSearchBy(String searchBy) {
        this.searchBy = searchBy;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getResult() {
        return (List<T>)result;
    }

    public void setResult(List<?> result) {
        this.result = result;
    }

}
