package com.xproject.util;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by Administrator on 2016/11/9.
 */
public class MultipleDataSource extends AbstractRoutingDataSource {
//    private static final ThreadLocal<String> dataSourceKey = new InheritableThreadLocal<String>();
//
//    public static void setDataSourceKey(String dataSource) {
//        dataSourceKey.set(dataSource);
//    }
//
//    @Override
//    protected Object determineCurrentLookupKey() {
//        return dataSourceKey.get();
//    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDBType();
    }
}
