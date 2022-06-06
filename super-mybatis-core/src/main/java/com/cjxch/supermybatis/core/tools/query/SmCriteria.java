package com.cjxch.supermybatis.core.tools.query;

import java.util.ArrayList;
import java.util.List;

/**
 * MyBatis适配器
 * 
 * @author Caizongyou
 * 
 */
public class SmCriteria {

    private List<Object> criterionList = new ArrayList<Object>();

	private SmCriteria() {
    };

    public static class SmCriteriaBuild{
        private List<Object> criterionList = new ArrayList<Object>();
        public SmCriteria build(){
            SmCriteria ia = new SmCriteria();
            ia.criterionList = criterionList;
            return ia;
        }
        public SmCriteriaBuild add(Object c) {
            criterionList.add(c);
            return this;
        }
    }

    public List<Object> getCriterionList() {
        return criterionList;
    }
}
