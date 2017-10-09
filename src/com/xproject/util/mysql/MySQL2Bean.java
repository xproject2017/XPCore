package com.xproject.util.mysql;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/2/26.
 */
public class MySQL2Bean {

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws SQLException {
        MySQL2Bean mySQL2Bean = new MySQL2Bean();
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        conn = MySQLConnUtil.getConnection("bj");

        JavaBeanInfo javaBeanInfo = new JavaBeanInfo();
        javaBeanInfo.setPackname("com.xproject.bean.dao.test");
        javaBeanInfo.setDirstr("");
        javaBeanInfo.setTablename("T_STAT_TOTTAGBYDAY");

        mySQL2Bean.start(javaBeanInfo, conn);

        MySQLConnUtil.close(rs, stmt, conn);
        MySQLConnUtil.close(rs, preStmt, conn);
    }

    /**
     * 本函数用来执行用户传入的sql语句(仅限于select语句)
     *
     * @param sql 传入的sql语句，等待执行
     * @return 返回执行sql语句后的结果集对象
     */
    public ResultSet query(Connection conn, String sql) {
        ResultSet rs = null;
        try {
            // 3、通过连接对象创建一个语句对象stmt，用来执行sql语句
            Statement stmt = conn.createStatement();
            // 4、执行sql语句，得到一个rs（结果集对象）
            rs = stmt.executeQuery(sql);
        } catch (Exception e) { // 错误处理，暂时不用理会
            e.printStackTrace();
        }
        return rs; // 将查询得到的结果集对象返回
    }

    public void start(JavaBeanInfo javaBeanInfo, Connection conn) {

        String packname = javaBeanInfo.getPackname();
        String dirstr = javaBeanInfo.getDirstr();// 空表示当前目录
        String tablename = javaBeanInfo.getTablename();
        packname = null == packname ? "" : packname;

        if (dirstr != null && !dirstr.isEmpty()) {
            if (!dirstr.endsWith("/")) {
                dirstr += "/";   //默认当前工程下
            }
        } else {
            dirstr = "JavaBean";
        }
        File dir = new File(dirstr);
        dir = new File(dirstr + "\\" + packname.replaceAll("\\.", "/"));  //如果不设置则按照包名生成路径
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String outputdir = dir.getAbsolutePath();// bean的生成目录

        try {
            if (tablename.length() > 0) {
                String[] tables = tablename.split(",");
                for (String table : tables) {
                    parseTableByShowCreate(conn, table, packname, outputdir);
                }
            } else {
                parseAllTable(conn, packname, outputdir);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 开始处理生成所有表 如果不传入表名，表示将数据库中所有表生成bean; 可以指定表名生成bean;
     */
    public void parseAllTable(Connection conn, String packname, String outputdir) {

        String sql = "show tables";
        ResultSet rs = query(conn, sql);
        try {
            while (rs.next()) {
                String tablename = rs.getString(1);
                parseTableByShowCreate(conn, tablename, packname, outputdir);
            }
            MySQLConnUtil.close(rs, null, null);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 通过 mysql的 show create table TABLE_NAME逆向生成Bean;
     *
     * @param conn
     * @param tablename
     * @param outputdir
     * @param packname
     */
    private void parseTableByShowCreate(Connection conn, String tablename,
                                        String packname, String outputdir) {
        StringBuilder classInfo = new StringBuilder("import com.xproject.bean.BaseBean;\n");
        classInfo.append("import java.util.Date;\n\n");
        classInfo.append("\t/**\r\n\t*");

        boolean shouldCloseConn = false;

        String sql = "show create table " + tablename;
        ResultSet rs = null;
        try {
            rs = query(conn, sql);
            StringBuilder fields = new StringBuilder();
            StringBuilder methods = new StringBuilder();

            while (rs.next()) {
                String sqlstr = rs.getString(2);
                String lines[] = sqlstr.split("\r\n");
                for (int i = 0; i < lines.length; i++) {
                    String line = lines[i];
                    System.out.println(line);
                    // System.out.println("------------");
                    String regex = "\\s*`([^`]*)`\\s*(\\w+[^ ]*)\\s*(NOT\\s+NULL\\s*)?(AUTO_INCREMENT\\s*)?(DEFAULT\\s*([^ ]*|NULL|'0'|''|CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP)\\s*)?(COMMENT\\s*'([^']*)')?\\s*,\\s*";
                    Pattern p = Pattern.compile(regex);
                    Matcher m = p.matcher(line);
                    while (m.find()) {
                        String field = m.group(1);
                        String type = typeTrans(m.group(2));
                        String cmt = m.group(8);
                        fields.append(getFieldStr(field, type, cmt));
                        methods.append(getMethodStr(field, type));
//						 System.out.println("================="+field);
//						 System.out.println("================="+type);
//						 System.out.println("================="+cmt);
                    }
                    if (i == lines.length - 1) {

                        classInfo.append("此类由" + getClass().getSimpleName()
                                + "工具自动生成\r\n");
                        classInfo.append("\t*备注(数据表的comment字段)：");
                        int index = line.indexOf("COMMENT=");
                        if (index != -1) {
                            String tmp = line.substring(index + 8);
                            classInfo.append(tmp.replace("'", ""));
                        } else {
                            classInfo.append("无备注信息");
                        }
                        classInfo.append("\r\n");
                        classInfo.append("\t*@author zhouxx\r\n");
                        classInfo.append("\t*@since ");
                        classInfo.append(sdf.format(new Date()));
                        classInfo.append("\r\n\t*/\r\n\r\n");
                    }

                }

            }
            classInfo.append("\tpublic class ").append((doTablename(tablename))).append("\t extends BaseBean{\r\n");
            classInfo.append(fields);
            classInfo.append(methods);
            classInfo.append("\r\n");
            classInfo.append("}");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            MySQLConnUtil.close(rs, null, null);
        }

        String packageinfo = "package " + packname + ";\r\n\r\n";
        File file = new File(outputdir, (doTablename(tablename)) + ".java");
        System.out.println(file.getAbsolutePath());
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(packageinfo);
            fw.write(classInfo.toString());
            fw.flush();
            fw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private String doTablename(String tablename) {
        //System.out.println("tablename==="+tablename);
        String[] p = tablename.toLowerCase().split("_");
        StringBuilder get = new StringBuilder();

        for (int i = 0; i < p.length; i++) {
            //System.out.println("p[i]==="+p[i] +" ;len="+p[i].length());

            if (p[i].length() > 1) {
                //System.out.println("===ddddd=="+p[i].substring(0, 1).toUpperCase().concat(p[i].substring(1)));
                get.append(p[i].substring(0, 1).toUpperCase().concat(p[i].substring(1)));
            } else {
                get.append(p[i].substring(0, 1).toUpperCase());
            }
        }
        return get.toString();
    }

    /**
     * @param type
     * @return
     */
    private String getMethodStr(String field, String type) {
        field = field.toLowerCase();

        StringBuilder get = new StringBuilder("\tpublic ");
        get.append(type).append(" ");
        if (type.equals("boolean")) {
            get.append(field);
        } else {
            get.append("get");
            get.append(upperFirestChar(field));
        }
        get.append("(){").append("\r\n\t\treturn this.").append(field)
                .append(";\r\n\t}\r\n");
        StringBuilder set = new StringBuilder("\tpublic void ");

        if (type.equals("boolean")) {
            set.append(field);
        } else {
            set.append("set");
            set.append(upperFirestChar(field));
        }
        set.append("(").append(type).append(" ").append(field)
                .append("){\r\n\t\tthis.").append(field).append("=")
                .append(field).append(";\r\n\t}\r\n");
        get.append(set);
        return get.toString();
    }

    public String upperFirestChar(String src) {
        //return src;
        return src.substring(0, 1).toUpperCase().concat(src.substring(1).toLowerCase());
        //return src.substring(0, 1).toLowerCase().concat(src.substring(1));
    }

    private static String getFieldStr(String field, String type, String cmt) {
        field = field.toLowerCase();

        StringBuilder sb = new StringBuilder();
        sb.append("\t").append("private ").append(type).append(" ")
                .append(field).append(";");
        if (cmt != null) {
            sb.append("//").append(cmt);
        }
        sb.append("\r\n");
        return sb.toString();
    }

    /**
     * mysql的类型转换到java 类型
     */
    public static String typeTrans(String type) {
        //System.out.println("type======" + type);
        if (type.contains("tinyint")) {
            return "Integer";
        } else if (type.contains("decimal") || type.contains("float") || type.contains("double")) {
            return "Double";
        } else if (type.contains("bigint")) {
            return "Long";
        } else if (type.contains("int") && !type.contains("bigint") && !type.contains("tinyint")) {
            return "Integer";
        } else if (type.contains("varchar") || type.contains("text") || type.contains("longtext")
                || type.contains("enum") || type.contains("set")) {
            return "String";
        } else if (type.contains("date")
                || type.contains("time") || type.contains("datetime")
                || type.contains("timestamp")) {
            return "Date";
        } else if (type.contains("binary") || type.contains("blob")) {
            return "byte[]";
        } else {
            return "String";
        }
    }
}

class JavaBeanInfo {
    String packname ; //包的路径
    String dirstr ;// 空表示当前工程目录,如果不设置则按照包名生成路径
    String tablename ; //表名，如果不填则导出库中所有表

    public String getPackname() {
        return packname;
    }

    public void setPackname(String packname) {
        this.packname = packname;
    }

    public String getDirstr() {
        return dirstr;
    }

    public void setDirstr(String dirstr) {
        this.dirstr = dirstr;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }
    public  JavaBeanInfo(){

    }
}
