package com.xproject.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * Created by hay on 2017/2/10.
 */
public class FillAssistUtil {
    private static final String stringValue = "";
    private static final int intValue = 0;
    private static final double doubleValue = 0.0;
    private static final float floatValue = 0.0f;
    private static final Long longValue = 0L;
    private static final Integer integerValue = 0;


    public static Object fill(Object object) {
        Class c = object.getClass();
        Field[] fields = object.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                Method getMethod = c.getMethod(getGetMethodName(field.getName()), null);
                if (getMethod != null) {
                    if (getMethod.invoke(object, null) == null) {
                        Method setMethod = c.getMethod(getSetMethodName(field.getName()), field.getType());
                        setMethod.invoke(object, getDefaultFieldValue(field.getType()));
                    }
                }
            }
            System.out.println("");
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getGetMethodName(String variableName) {
        char firstChar = variableName.charAt(0);
        char secondChar = variableName.charAt(1);
        if (Character.isLowerCase(firstChar) && Character.isUpperCase(secondChar)) {
            return "get" + variableName;
        } else {
            return variableName = "get" + variableName.substring(0, 1).toUpperCase() + variableName.substring(1);
        }
    }

    public static String getSetMethodName(String variableName) {
        char firstChar = variableName.charAt(0);
        char secondChar = variableName.charAt(1);
        if (Character.isLowerCase(firstChar) && Character.isUpperCase(secondChar)) {
            return "set" + variableName;
        } else {
            return variableName = "set" + variableName.substring(0, 1).toUpperCase() + variableName.substring(1);
        }
    }

    public static Object getDefaultFieldValue(Class<?> clazz) {
        if (clazz == String.class) {
            return stringValue;
        } else if (clazz == Long.class || clazz == long.class) {
            return longValue;
        } else if (clazz == int.class) {
            return intValue;
        } else if (clazz == Integer.class) {
            return integerValue;
        } else if (clazz == Date.class) {
            return new Date();
        } else if (clazz == double.class || clazz == Double.class) {
            return doubleValue;
        } else if (clazz == Float.class || clazz == float.class) {
            return floatValue;
        } else {
            return null;
        }
    }
}
