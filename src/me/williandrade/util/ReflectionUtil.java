/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.williandrade.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author williandrade.me
 */
class ReflectionUtil {

    protected static <T> List<Method> findAllGetters(T item) {
        List<Method> result = new ArrayList<>();
        Class clazz = item.getClass();

        Method[] methods = clazz.getMethods();

        for (Method method : methods) {
            if (method.getName().contains("get") && !method.getName().equals("getClass")) {
                result.add(method);
            }
        }

        return result;
    }

    protected static <T> Field getFieldFromGetter(T item, String getter) {
        Field result = null;
        Class clazz = item.getClass();

        try {
            String fieldName = getter.substring(3, getter.length());
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);

            result = clazz.getDeclaredField(fieldName);
        } catch (Exception e) {
        }

        return result;
    }

    protected static <T> Object invokeGetter(Method method, T dto) {
        Object result = null;

        try {
            result = method.invoke(dto);
        } catch (Exception e) {
        }

        return result;
    }
}
