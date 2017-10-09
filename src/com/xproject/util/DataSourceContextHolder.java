package com.xproject.util;

/**
 * Created by Administrator on 2017/1/5.
 */
public class DataSourceContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static void setDBType(String dbType) {
        contextHolder.set(dbType);
    }

    public static String getDBType() {
        return ((String) contextHolder.get());
    }

    public static void clearDBType() {
        contextHolder.remove();
    }
}