package com.cjxch.supermybatis.core.tools.query;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Caizongyou
 */
public class SmCriterionArrays {

    private List<SmCriterion> smCriterionList;

    /**
     * 连接符
     */
    private CriteriaConnector connector;

    private SmCriterionArrays(SmCriterionArraysBuild build){
        this.smCriterionList = build.getSmCriterionList();
    }

    public static class SmCriterionArraysBuild{
        private List<SmCriterion> smCriterionList = new ArrayList<>();
        public SmCriterionArrays build(CriteriaConnector connector){
            SmCriterionArrays sca = new SmCriterionArrays(this);
            sca.connector = connector;
            return sca;
        }

        public List<SmCriterion> getSmCriterionList() {
            return smCriterionList;
        }

        public void setSmCriterionList(List<SmCriterion> smCriterionList) {
            this.smCriterionList = smCriterionList;
        }

        /**
         * 相等
         *
         * @param key
         * @param value
         * @return
         */
        public SmCriterionArraysBuild eq(String key, Object value) {
            this.smCriterionList.add(new SmCriterion(key, value, CriteriaType.eq));
            return this;
        }

        /**
         * 大于等于
         *
         * @param key
         * @param value
         * @return
         */
        public SmCriterionArraysBuild ge(String key, Object value) {
            this.smCriterionList.add(new SmCriterion(key, value, CriteriaType.ge));
            return this;
        }

        /**
         * 小于等于
         *
         * @param key
         * @param value
         * @return
         */
        public SmCriterionArraysBuild le(String key, Object value) {
            this.smCriterionList.add(new SmCriterion(key, value, CriteriaType.le));
            return this;
        }

        /**
         * 大于
         *
         * @param key
         * @param value
         * @return
         */
        public SmCriterionArraysBuild gt(String key, Object value) {
            this.smCriterionList.add(new SmCriterion(key, value, CriteriaType.gt));
            return this;
        }

        /**
         * 小于
         *
         * @param key
         * @param value
         * @return
         */
        public SmCriterionArraysBuild lt(String key, Object value) {
            this.smCriterionList.add(new SmCriterion(key, value, CriteriaType.lt));
            return this;
        }

        /**
         * 模糊匹配
         *
         * @param key
         * @param value
         * @return
         */
        public SmCriterionArraysBuild like(String key, Object value) {
            this.smCriterionList.add(new SmCriterion(key, value, CriteriaType.like));
            return this;
        }

        /**
         * 为空
         *
         * @param key
         * @return
         */
        public SmCriterionArraysBuild isNull(String key) {
            this.smCriterionList.add(new SmCriterion(key, null, CriteriaType.isNull));
            return this;
        }

        /**
         * 不为空
         *
         * @param key
         * @return
         */
        public SmCriterionArraysBuild isNotNull(String key) {
            this.smCriterionList.add(new SmCriterion(key, null, CriteriaType.isNotNull));
            return this;
        }

        /**
         * in
         *
         * @param key
         * @param value
         * @return
         */
        public SmCriterionArraysBuild in(String key, Object value) {
            this.smCriterionList.add(new SmCriterion(key, value, CriteriaType.in));
            return this;
        }




        /**
         * 相等
         *
         * @param key
         * @param value
         * @return
         */
        public SmCriterionArraysBuild eq(String key, Object value, CriteriaConnector connector) {
            this.smCriterionList.add(new SmCriterion(key, value, CriteriaType.eq, connector));
            return this;
        }

        /**
         * 大于等于
         *
         * @param key
         * @param value
         * @return
         */
        public SmCriterionArraysBuild ge(String key, Object value, CriteriaConnector connector) {
            this.smCriterionList.add(new SmCriterion(key, value, CriteriaType.ge, connector));
            return this;
        }

        /**
         * 小于等于
         *
         * @param key
         * @param value
         * @return
         */
        public SmCriterionArraysBuild le(String key, Object value, CriteriaConnector connector) {
            this.smCriterionList.add(new SmCriterion(key, value, CriteriaType.le, connector));
            return this;
        }

        /**
         * 大于
         *
         * @param key
         * @param value
         * @return
         */
        public SmCriterionArraysBuild gt(String key, Object value, CriteriaConnector connector) {
            this.smCriterionList.add(new SmCriterion(key, value, CriteriaType.gt, connector));
            return this;
        }

        /**
         * 小于
         *
         * @param key
         * @param value
         * @return
         */
        public SmCriterionArraysBuild lt(String key, Object value, CriteriaConnector connector) {
            this.smCriterionList.add(new SmCriterion(key, value, CriteriaType.lt, connector));
            return this;
        }

        /**
         * 模糊匹配
         *
         * @param key
         * @param value
         * @return
         */
        public SmCriterionArraysBuild like(String key, Object value, CriteriaConnector connector) {
            this.smCriterionList.add(new SmCriterion(key, value, CriteriaType.like, connector));
            return this;
        }

        /**
         * 为空
         *
         * @param key
         * @return
         */
        public SmCriterionArraysBuild isNull(String key, CriteriaConnector connector) {
            this.smCriterionList.add(new SmCriterion(key, null, CriteriaType.isNull, connector));
            return this;
        }

        /**
         * 不为空
         *
         * @param key
         * @return
         */
        public SmCriterionArraysBuild isNotNull(String key, CriteriaConnector connector) {
            this.smCriterionList.add(new SmCriterion(key, null, CriteriaType.isNotNull, connector));
            return this;
        }

        /**
         * in
         *
         * @param key
         * @param value
         * @return
         */
        public SmCriterionArraysBuild in(String key, Object value, CriteriaConnector connector) {
            this.smCriterionList.add(new SmCriterion(key, value, CriteriaType.in, connector));
            return this;
        }
    }

    public List<SmCriterion> getSmCriterionList() {
        return smCriterionList;
    }

    public CriteriaConnector getConnector() {
        return connector;
    }
}
