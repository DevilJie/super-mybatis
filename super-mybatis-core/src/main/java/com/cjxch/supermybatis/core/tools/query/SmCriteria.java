package com.cjxch.supermybatis.core.tools.query;

import java.util.ArrayList;
import java.util.List;

/**
 * Super-Mybatis查询构造器
 * 
 * @author Caizongyou
 * 
 */
public class SmCriteria {

    private List<Object> criterionList = new ArrayList<Object>();

    private String[] customerRetColumn;

	private SmCriteria() {
    };

    public static class SmCriteriaBuild{
        private List<Object> criterionList = new ArrayList<Object>();
        private String[] customerRetColumn;
        public SmCriteria build(){
            SmCriteria ia = new SmCriteria();
            ia.criterionList = criterionList;
            ia.customerRetColumn = customerRetColumn;
            return ia;
        }
        public SmCriteriaBuild add(Object c) {
            criterionList.add(c);
            return this;
        }

        public SmCriteriaBuild customerRetColumn(String customerRetColumn){
            this.customerRetColumn = new String[]{customerRetColumn};
            return this;
        }

        public SmCriteriaBuild customerRetColumn(String[] customerRetColumn){
            this.customerRetColumn = customerRetColumn;
            return this;
        }
    }

    public List<Object> getCriterionList() {
        return criterionList;
    }

    public String[] getCustomerRetColumn() {
        return customerRetColumn;
    }
}
