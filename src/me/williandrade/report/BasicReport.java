/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.williandrade.report;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author williandrade.me
 */
public abstract class BasicReport {

    public abstract ByteArrayOutputStream generate() throws Exception;

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

    protected String convertDefaultCaseToNormalString(String normalCase) {
        StringBuilder result = new StringBuilder();

        normalCase = normalCase.substring(0, 1).toUpperCase() + normalCase.substring(1);

        Pattern pattern = Pattern.compile("([A-Z][a-z]+)");
        Matcher matcher = pattern.matcher(normalCase);

        while (matcher.find()) {
            String val = matcher.group(1);
            result.append(" ");
            result.append(val);
        }

        return result.toString().substring(1, result.length());
    }

}
