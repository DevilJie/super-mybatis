package com.cjxch.supermybatis.cache.base.core;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

/**
 * @author litianyi
 * @date 2012-6-7 上午10:57:53
 */
public class SpelParser {
    private static ExpressionParser parser = new SpelExpressionParser();

    public static String getKey(String key, String condition,String[] paramNames, Object[] arguments) {
        try {
            if (!checkCondition(condition, paramNames, arguments)){
                return null;
            }
            Expression expression = parser.parseExpression(key);
            EvaluationContext context = new StandardEvaluationContext();
            int length = paramNames.length;
            if (length > 0) {
                for (int i = 0; i < length; i++) {
                        context.setVariable(paramNames[i], arguments[i]);
                }
            }
            return expression.getValue(context, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
     
    public static boolean checkCondition(String condition,String[] paramNames, Object[] arguments){
        if (StringUtils.isEmpty(condition) || condition.length()<1){
            return true;
        }
        Expression expression = parser.parseExpression(condition);
        EvaluationContext context = new StandardEvaluationContext();
        int length = paramNames.length;
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                context.setVariable(paramNames[i], arguments[i]);
            }
        }
        return expression.getValue(context, boolean.class);
    }
}