package com.xproject.util.mysql;

import com.mchange.v2.c3p0.DataSources;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Created by Administrator on 2016/2/3.
 */
public class MySQLConnUtil {
    private static Logger logger = Logger.getLogger(MySQLConnUtil.class);

    public static Map<String, String> dbkey = new HashMap<String, String>();
    public static Map<String, String> dbname = new HashMap<String, String>();
    private static Map<String, DataSource> ds_pooled = new HashMap<String, DataSource>();

    /**
     *  加载数据库连接的配置文件和驱动
     */
    static {
        InputStream fis = null;

        Properties env = new Properties();
        try {
//            String projectpath=System.getProperty("user.dir");
//            String classpath=Class.class.getClass().getResource("/").getPath().substring(1);
//            String binpath= System.getProperty("java.class.path");
//            URL xmlpath =  MySQLConnUtil.class.getClass().getClassLoader().getResource("tool.properties");
//            System.out.println(xmlpath);
//            xmlpath =  MySQLConnUtil.class.getClass().getClassLoader().getResource("systemConfig.properties");
//            System.out.println(xmlpath);
//
//            System.out.println(projectpath);
//            System.out.println(classpath);
//            System.out.println(binpath);
            //fis = new FileInputStream("dbconfig.properties");
            fis = MySQLConnUtil.class.getClassLoader().getResourceAsStream("tool.properties");

            //加载属性文件中的数据库配置信息
            //以=左边作为key值，右边作为value值
            env.load(fis);

            //设置dbkey，要与配置文件中的命名一致
            //			dbkey.put("bjhidb", "bjhi");
            //			dbkey.put("bjhddb", "bjhd");
            //			dbkey.put("bjtzdb", "bjtz");
            //			dbkey.put("bjyzdb", "bjyz");
            //			dbkey.put("bjpgdb", "bjpg");
            String strKey = null;
            String strKey1 = null;
            String strValue = null;
            Enumeration enum1 = env.propertyNames();//得到配置文件的名字
            while (enum1.hasMoreElements()) {
                strKey = (String) enum1.nextElement();
                strValue = env.getProperty(strKey);
                System.out.println(strKey + "=" + strValue);
                if (strKey.endsWith(".authname") && strKey.startsWith("jdbc.")) {
                    strKey1 = strKey.replaceAll(".authname", "").replaceAll("jdbc.", "");
                    logger.info("strKey1==" + strKey1);
                    dbkey.put(strKey1, strValue);
                }
            }
            logger.info(dbkey);

            //1. 加载驱动类
            Class.forName(env.getProperty("jdbc.driver"));
            DataSource ds_unpooled = null;
            Map<String, Object> pool_conf = new HashMap<String, Object>();
            int initialPoolSize = Integer.valueOf(env.getProperty("jdbc.initialPoolSize").trim());
            int maxPoolSize = Integer.valueOf(env.getProperty("jdbc.maxPoolSize").trim());
            int minPoolSize = Integer.valueOf(env.getProperty("jdbc.minPoolSize").trim());
            int maxIdleTime = Integer.valueOf(env.getProperty("jdbc.maxIdleTime").trim());
            pool_conf.put("initialPoolSize", initialPoolSize);
            pool_conf.put("maxPoolSize", maxPoolSize);
            pool_conf.put("minPoolSize", minPoolSize);
            pool_conf.put("maxIdleTime", maxIdleTime);

            Iterator it = MySQLConnUtil.dbkey.keySet().iterator();
            while (it.hasNext()) {
                final String key;
                key = it.next().toString();

                //取到数据库名称
                dbname.put(key, env.getProperty("jdbc." + key + ".dbname").trim());

                //设置连接数据库的配置信息
                String url = env.getProperty("jdbc." + key + ".url").trim();
                String username = env.getProperty("jdbc." + key + ".username").trim();
                String pwd = env.getProperty("jdbc." + key + ".pwd").trim();
                ds_unpooled = DataSources.unpooledDataSource(url, username, pwd);
                DataSource dbs = DataSources.pooledDataSource(ds_unpooled, pool_conf);
                //System.out.println(dbs.getConnection().getClientInfo());
                ds_pooled.put(key, dbs);
                logger.info("初始化连接对象。:key=" + key);

            }
            logger.info(dbname);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error(e);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            logger.error(e);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }
    }

    /**
     * 获取连接对象
     */
    public static Connection getConnection(String key) throws SQLException {
        logger.info("获取连接对象。:key=" + key);
        return ds_pooled.get(key).getConnection();
    }

    /**
     * 释放连接池资源
     */
    public static void clearup(String key) {
        if (ds_pooled != null) {
            try {
                DataSources.destroy(ds_pooled.get(key));
                logger.info("释放连接池资源。:key=" + key);
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error(e);
            }
        }
    }


    /**
     * 资源关闭
     *
     * @param rs
     * @param stmt
     * @param conn
     */
    public static void close(ResultSet rs, Statement stmt, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error(e);
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error(e);
            }
        }

        if (conn != null) {
            try {
                conn.close();
                logger.info("释放数据库连接。");
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error(e);
            }
        }
    }
}
