package com.cjxch.supermybatis.core.tools.query;

public enum CriteriaType {
    eq(" = "),
    ge(" >= "),
    le(" <= "),
    gt(" > "),
    lt(" < "),
    like(" LIKE "),
    isNull(" IS NULL "),
    isNotNull(" IS NOT NULL "),
    in(" IN ");

    private String operator;

    CriteriaType(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
}
