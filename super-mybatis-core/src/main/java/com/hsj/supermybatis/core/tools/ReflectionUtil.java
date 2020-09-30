package com.hsj.supermybatis.core.tools;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.StringUtils;

public class ReflectionUtil {

    /**
     * 调用Getter方法
     * 
     * @param object 对象
     * @param propertyName 属性名称
     */
    public static Object invokeGetterMethod(Object object, String propertyName) {
        String getterMethodName = "get" + StringUtils.capitalize(propertyName);
        try {
            Method getterMethod = object.getClass().getMethod(getterMethodName);
            return getterMethod.invoke(object);
        } catch (Exception e) {
            // e.printStackTrace();
            return null;
        }
    }

    /**
     * 调用Setter方法
     * 
     * @param object 对象
     * @param propertyName 属性名称
     * @param propertyValue 属性值
     */
    public static void invokeSetterMethod(Object object, String propertyName, Object propertyValue) {
        Class<?> setterMethodClass = propertyValue.getClass();
        // object 传过来的对象 ,propertyName参数名 以及参数值，和参数所属的对象
        invokeSetterMethod(object, propertyName, propertyValue, setterMethodClass);
    }

    /**
     * 根据用户传入对象和参数类型，组装对象set方法来执行
     * 
     * @param object 对象
     * @param propertyName 属性名称
     * @param propertyValue 属性值
     * @param setterMethodClass 参数类型
     */
    public static void invokeSetterMethod(Object object, String propertyName, Object propertyValue,
                                          Class<?> setterMethodClass) {

        String setterMethodName = "set" + StringUtils.capitalize(propertyName);
        try {
            Method setterMethod = object.getClass().getMethod(setterMethodName, setterMethodClass);
            setterMethod.invoke(object, propertyValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取对象属性值,无视private/protected/getter
     * 
     * @param object 对象
     * @param fieldName 属性名称
     */
    public static Object getFieldValue(Object object, String fieldName) {
        Field field = getAccessibleField(object, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("Could not find field " + fieldName);
        }
        Object result = null;
        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {

        }
        return result;
    }

    /**
     * 设置对象属性值,无视private/protected/setter
     * 
     * @param object 对象
     * @param fieldName 属性名称
     */
    public static void setFieldValue(Object object, String fieldName, Object value) {
        Field field = getAccessibleField(object, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("Could not find field " + fieldName);
        }
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {

        }
    }

    private static Field getAccessibleField(final Object object, final String fieldName) {
        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {

            }
        }
        return null;
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     * @param object : 子类对象
     * @return 父类中的属性对象
     */
    public static Field[] getDeclaredField(Object object){
        if(object == null) return new Field[]{};
        List<Field> ret = new ArrayList<>();
        Class<?> clazz = object.getClass() ;
        for(; clazz != Object.class ; clazz = clazz.getSuperclass()) {
            try {
                ret.addAll(Arrays.asList(clazz.getDeclaredFields()));
            } catch (Exception e) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
            }
        }
        return ret.toArray(new Field[ret.size()]);
    }


    /**
     * 循环向上转型, 获取对象的 DeclaredField
     *
     * @param object : 子类对象
     * @param fieldName : 父类中的属性名
     * @return 父类中的属性对象
     */

    public static Field getDeclaredField(Object object, String fieldName) {
        Field field = null;

        Class<?> clazz = object.getClass();

        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (Exception e) {
                // 这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                // 如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了

            }
        }

        return null;
    }
}
