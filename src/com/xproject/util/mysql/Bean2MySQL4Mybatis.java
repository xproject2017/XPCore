package com.xproject.util.mysql;


import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2015/12/24.
 */
public class Bean2MySQL4Mybatis {

   /*
   <typeAlias type="com.casystem.common.mode.tag.TagBeanWly" alias="TagBeanWly"/>


    import com.casystem.common.mode.appserver.base.TSysStaff;

    import java.util.HashMap;
    import java.util.List;

    /*
     * Created by zhouxx on 2015-3-4.
     */
  /*  public interface UserDao {
        /*权限--系统用户*/
 /*       public TSysStaff getUserInfo(TSysStaff usersBean);
        public List<TSysStaff> getUserList(TSysStaff usersBean);
        public HashMap getUserListGroups(TSysStaff usersBean);  //返回count,max,min
        public int addUsers(TSysStaff usersBean);
        public int updateUsers(TSysStaff usersBean);
        public int deleteUsers(TSysStaff usersBean);

    }
    */
   static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String args[]) {
        try {
            Class c = Bean2MySQL4Mybatis.class;
            //String TableName="T_SYS_CACORELOG";
            String packagename=c.getName();
            String classname=c.getSimpleName().trim();//packagename.substring(packagename.lastIndexOf(".")+1);
            String mybatis_typeAlias=classname;
            String TableName=getTablename(classname);
            System.out.println(packagename+" , "+classname+","+TableName+"\n\n");
            String comments="";

            //manager接口
            comments="标准Manager";
            managerInterface(c, comments, classname);

            //dao接口
            comments="标准DAO";
            daoInterface(c,comments,classname);
            mybatisAlias(c,mybatis_typeAlias);

            //dao的XML文件
            daoXmlBegin(c,comments,classname);
            selectSql(c, TableName, classname, mybatis_typeAlias);
            selectSql_MySQL_pageGroups(c, TableName, classname, mybatis_typeAlias); //返回分页查询总记录数，用于前台计算页数
            selectSql_MySQL_page(c, TableName, classname, mybatis_typeAlias);
            addSql(c, TableName, classname, mybatis_typeAlias); //修改savetime和createtime的默认值now() ，返回主键自增值
            updateSql(c, TableName, classname, mybatis_typeAlias); //修改savetime的默认值now()  ,createtime不允许修改  ;并发控制，增加savetime条件
            deleteSql(c, TableName, classname, mybatis_typeAlias); //并发控制，增加savetime条件
            daoXmlEnd(c);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void managerInterface(Class c, String comments, String classname) {
        System.out.println("//------------新增" + c.getName() + "对象Manager接口--------------");

        String packagename=c.getName();
        String daoname=classname.substring(0,1).toUpperCase()+classname.substring(1)+"Manager";
        String[] packagename_arr=packagename.split("\\.");
        String pname=packagename_arr[packagename_arr.length-2];
        pname=pname+"."+ daoname;
        // System.out.println(packagename+" , "+endwithfunname);
        System.out.println("\n// 对应dao xml路径：com.xproject.manager.tx."+pname+".java ");

        StringBuilder classInfo=new StringBuilder();

        classInfo.append("import\t"+packagename+";\r\n");
        classInfo.append("import java.util.HashMap;\r\n");
        classInfo.append("import java.util.List;\r\n");

        classInfo.append("\t/**\r\n");
        classInfo.append("\t****" + comments + "\r\n");
        classInfo.append("\t*此Manager接口由" + classname + "类自动生成\r\n");
        classInfo.append("\t*@author zhouxx\r\n");
        classInfo.append("\t*@since ");
        classInfo.append(sdf.format(new Date()));
        classInfo.append("\r\n\t*/\r\n\r\n");
        classInfo.append("\tpublic interface ").append(daoname).append("{\r\n");

        classInfo.append("\t\tpublic "+classname+"\t"+"get"+classname+"("+classname+"\tbean);\r\n");
        classInfo.append("\t\tpublic List<"+classname+">\t"+"get"+classname+"List("+classname+"\tbean);\n");
        classInfo.append("\t\tpublic HashMap\t"+"get"+classname+"Group("+classname+"\tbean);\n");
        classInfo.append("\t\tpublic "+classname+"\t"+"add"+classname+"("+classname+"\tbean);\n");
        classInfo.append("\t\tpublic "+classname+"\t"+"update"+classname+"("+classname+"\tbean);\n");
        classInfo.append("\t\tpublic "+classname+"\t"+"delete"+classname+"("+classname+"\tbean);\n");

        classInfo.append("\r\n");
        classInfo.append("}");

        System.out.println(classInfo.toString());
        System.out.println("//------------新增" + c.getName() + "对象Dao接口结束--------------\n");
    }

    /*
        数据库对象名
        本系统数据库中所有对象名均为大写，命名规则如下：
        名称	规则	最大长度（字符数）
        数据库名	大写字母组合	8
        用户名	大写字母组合	8
        表名	T_{SYS|BASE|STAT|INTF|BUSI}_{表名简写[单词]和数字组合}	30
        视图名	V_{SYS|BASE|STAT|INTF|BUSI}_{视图名简写[单词]和数字组合}	30
        存储过程名	PRO_{功能名简写[单词]}	30
        函数名	FUN_{功能名简写[单词]}	30
        触发器	TRG_{功能名简写[单词]}	30
        主键	PK_{表名简写[单词]和数字组合}_{字段}	30
        唯一索引	UIDX_{表名简写[单词]和数字组合}_{字段}	30
        外键	FK_{表名简写[单词]和数字组合}_{字段}	30
        其他索引	IDX_{表名简写[单词]和数字组合}_{字段}	30
    */
private static String getTablename(String classname) {
    if (classname == null) {
        return "";
    }
    if ("".equals(classname.trim())) {
        return "";
    }
    char[] classname_chr = classname.toCharArray();
    StringBuilder sb=new StringBuilder();

    for (int i = 0; i < classname_chr.length; i++) {
        if (classname_chr[i] >= 'A' && classname_chr[i] <= 'Z' && i==0) {
            sb.append(classname_chr[i]);
            //sb.append("_");
        }else {
            if (classname_chr[i] >= 'A' && classname_chr[i] <= 'Z') {
                sb.append("_");
                sb.append(classname_chr[i]);
            }else {
                sb.append(classname_chr[i]);
            }
        }
    }
    return sb.toString().toUpperCase();
}

    private static void daoXmlBegin(Class c,String comments,String endwithfunname) {
        String head="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n" +
                "<mapper namespace=\"com.xproject.dao.MyTPP\">\n" ;

        String packagename=c.getName();
        String classname=c.getSimpleName().trim();
        String daoname=endwithfunname.substring(0,1).toUpperCase()+endwithfunname.substring(1)+"Dao";
        String[] packagename_arr=packagename.split("\\.");
        String pname=packagename_arr[packagename_arr.length-2];
        pname=pname+"."+ daoname;
        //System.out.println(packagename+" , "+endwithfunname+","+daoname+","+pname);
        System.out.println("\n<!-- 对应dao xml路径：com.xproject.mapper."+pname.replace("Dao",".xml") +" -->");
        System.out.println("\n"+head.replace("MyTPP",pname));
    }
    private static void daoXmlEnd(Class c) {
        System.out.println("\n</mapper>\n");
    }

    public static void daoInterface(Class c,String comments,String classname) {
        System.out.println("//------------新增" + c.getName() + "对象Dao接口--------------");

        String packagename=c.getName();
        //String classname=c.getSimpleName().trim();
        String daoname=classname.substring(0,1).toUpperCase()+classname.substring(1)+"Dao";
        String[] packagename_arr=packagename.split("\\.");
        String pname=packagename_arr[packagename_arr.length-2];
        pname=pname+"."+ daoname;
       // System.out.println(packagename+" , "+endwithfunname);
        System.out.println("\n// 对应dao xml路径：com.xproject.dao."+pname+".java ");

        StringBuilder classInfo=new StringBuilder();

        classInfo.append("import\t"+packagename+";\r\n");
        classInfo.append("import java.util.HashMap;\r\n");
        classInfo.append("import java.util.List;\r\n");

        classInfo.append("\t/**\r\n");
        classInfo.append("\t****" + comments + "\r\n");
        classInfo.append("\t*此Dao接口由" + classname + "类自动生成\r\n");
        classInfo.append("\t*@author zhouxx\r\n");
        classInfo.append("\t*@since ");
        classInfo.append(sdf.format(new Date()));
        classInfo.append("\r\n\t*/\r\n\r\n");
        classInfo.append("\tpublic interface ").append(daoname).append("{\r\n");

        classInfo.append("\t\tpublic "+classname+"\t"+"get"+classname+"("+classname+"\tbean);\r\n");
        classInfo.append("\t\tpublic List<"+classname+">\t"+"get"+classname+"List("+classname+"\tbean);\n");
        classInfo.append("\t\tpublic HashMap\t"+"get"+classname+"Group("+classname+"\tbean);\n");
        classInfo.append("\t\tpublic int\t"+"add"+classname+"("+classname+"\tbean);\n");
        classInfo.append("\t\tpublic int\t"+"update"+classname+"("+classname+"\tbean);\n");
        classInfo.append("\t\tpublic int\t"+"delete"+classname+"("+classname+"\tbean);\n");

        classInfo.append("\r\n");
        classInfo.append("}");

        System.out.println(classInfo.toString());
        System.out.println("//------------新增" + c.getName() + "对象Dao接口结束--------------\n");
    }
    public static void    mybatisAlias(Class c,String mybatis_typeAlias){
       // <typeAlias type="com.casystem.common.mode.tag.TagBeanWly" alias="TagBeanWly"/>
        System.out.println("<!-- 新增" + c.getName() + "对象MyBatis的别名 -->");
        StringBuilder typeAlias=new StringBuilder();
        typeAlias.append("<typeAlias \t");
        typeAlias.append("type=\""+c.getName()+"\" \t");
        typeAlias.append("alias=\""+mybatis_typeAlias+"\" \t");
        typeAlias.append("/>\r\n");
        System.out.println(typeAlias.toString());
        System.out.println("<!-- 新增" + c.getName() + "对象MyBatis的别名 -->");
    }
    public static void addSql(Class c,String TableName,String endwithfunname,String mybatis_typeAlias) {
        //System.out.println("------------新增" + c.getName() + "对象sql语句------(单条insert)--------");
        Field[] fields = c.getDeclaredFields();
        String addSql = "<insert id=\"add"+endwithfunname+"\" useGeneratedKeys=\"true\" keyProperty=\"primaryKey\" parameterType=\""+mybatis_typeAlias+"\">\r\nINSERT INTO " + TableName + " (";
        for (Field f : fields) {
            addSql += f.getName().toUpperCase() + ",";
        }
        addSql = addSql.substring(0, addSql.length() - 1) + ")\r\nvalues(";
        for (Field f : fields) {
            if ("CREATETIME".equals(f.getName().toUpperCase())||"SAVETIME".equals(f.getName().toUpperCase())) {
                addSql += ("NOW(),");
            } else {
                addSql += ("#{" + f.getName() + "},");
            }
        }
        addSql = addSql.substring(0, addSql.length() - 1) + ")\r\n</insert>";
        System.out.println(addSql);
        //System.out.println("------------新增" + c.getName() + "对象sql语句结束-----------");
    }

    public static void addSql(Class c,String TableName) {
        System.out.println("------------新增" + c.getName() + "对象sql语句--------------");
        Field[] fields = c.getDeclaredFields();
        String addSql = "<insert id=\"MedhodName\" useGeneratedKeys=\"true\" keyProperty=\"primaryKey\" parameterType=\"BeanName\">\r\ninsert into " + TableName + " (";
        for (Field f : fields) {
            addSql += f.getName().toUpperCase() + ",";
        }
        addSql = addSql.substring(0, addSql.length() - 1) + ") \r\nvalues(";
        for (Field f : fields) {
            if ("CREATETIME".equals(f.getName().toUpperCase())||"SAVETIME".equals(f.getName().toUpperCase())) {
                addSql += ("NOW(),");
            } else {
                addSql += ("#{" + f.getName() + "},");
            }
        }
        addSql = addSql.substring(0, addSql.length() - 1) + ")</insert>";
        System.out.println(addSql);
        System.out.println("------------新增" + c.getName() + "对象sql语句结束-----------");
    }

    public static void updateSql(Class c,String TableName,String endwithfunname,String mybatis_typeAlias) {
        //System.out.println("------------更新" + c.getName() + "对象sql语句--------------");
        Field[] fields = c.getDeclaredFields();
        String updateSql = "<update id=\"update"+endwithfunname+"\" parameterType=\""+mybatis_typeAlias+"\">\r\n" +
                "update "+ TableName+"\n<set>\n";
        String variableName ="";
        for (Field f : fields) {
            if ("SAVETIME".equals(f.getName().toUpperCase())) {
                updateSql += ("<if test=\"" + 1 + "==1\">" + "" + "SAVETIME=NOW(),</if>\r\n");
            } else {
                if (!"CREATETIME".equals(f.getName().toUpperCase())) {
                    variableName = f.getName();
                    updateSql += ("<if test=\"" + variableName + "!=null\">" + variableName.toUpperCase() + "=#{" + variableName + "},</if>\r\n");
                }

            }
        }

        for (Field f : fields) {
            if ("SAVETIME".equals(f.getName().toUpperCase())) {
                variableName = f.getName();
            }
        }
        updateSql += "</set>\n where primarykey = #{primarykey}\n" +
                " AND SAVETIME=#{" + variableName + "} \r\n</update>";
        System.out.println(updateSql);
        //System.out.println("------------更新" + c.getName() + "对象sql语句结束----------\r\n");
    }

    public static void updateSql(Class c,String TableName) {
        System.out.println("------------更新" + c.getName() + "对象sql语句--------------");
        Field[] fields = c.getDeclaredFields();
        String updateSql = "<update id=\"MethodName\" parameterType=\"BeanName\">\r\n" +
                "update "+ TableName+"\n<set>\n";
        String variableName ="";
        for (Field f : fields) {
            if ("SAVETIME".equals(f.getName().toUpperCase())) {
                updateSql += ("<if test=\"" + 1 + "==1\">" + "" + "SAVETIME=NOW(),</if>\r\n");
            } else {
                if (!"CREATETIME".equals(f.getName().toUpperCase())) {
                    variableName = f.getName();
                    updateSql += ("<if test=\"" + variableName + "!=null\">" + variableName.toUpperCase() + "=#{" + variableName + "},</if>\r\n");
                }

            }
        }

        for (Field f : fields) {
            if ("SAVETIME".equals(f.getName().toUpperCase())) {
                variableName = f.getName();
            }
        }
        updateSql += "</set>\n where primarykey = #{primarykey}\n" +
                " AND SAVETIME=#{" + variableName + "} \r\n</update>";
        System.out.println(updateSql);
        System.out.println("------------更新" + c.getName() + "对象sql语句结束----------\n");
    }
    public static void deleteSql(Class c,String TableName,String endwithfunname,String mybatis_typeAlias) {
        //System.out.println("------------删除" + c.getName() + "对象sql语句--------------");
        Field[] fields = c.getDeclaredFields();
        String variableName ="";
        for (Field f : fields) {
            if ("SAVETIME".equals(f.getName().toUpperCase())) {
                variableName = f.getName();
            }
        }
        String updateSql = "<delete id=\"delete"+endwithfunname+"\" parameterType=\""+mybatis_typeAlias+"\">\r\n" +
                "delete from "+ TableName+"\n where primarykey = #{primarykey} AND SAVETIME=#{" + variableName + "}" +
                "\r\n</delete>";
        System.out.println(updateSql);
        //System.out.println("------------删除" + c.getName() + "对象sql语句结束----------\n");
    }

    public static void deleteSql(Class c,String TableName) {
        System.out.println("------------删除" + c.getName() + "对象sql语句--------------");
        Field[] fields = c.getDeclaredFields();
        String variableName ="";
        for (Field f : fields) {
            if ("SAVETIME".equals(f.getName().toUpperCase())) {
                variableName = f.getName();
            }
        }
        String updateSql = "<delete id=\"MethodName\" parameterType=\"BeanName\">\r\n" +
                "delete from "+ TableName+"\n where primarykey = #{primarykey} AND SAVETIME=#{" + variableName + "}   \n" +
                "</delete>";
        System.out.println(updateSql);
        System.out.println("------------删除" + c.getName() + "对象sql语句结束----------\n");
    }
    public static void selectSql(Class c,String TableName ,String endwithfunname,String mybatis_typeAlias) {
       // System.out.println("------------查询" + c.getName() + "对象sql语句--------------");
        Field[] fields = c.getDeclaredFields();
        String selectSql = "<select id=\"get"+endwithfunname+"\" parameterType=\""+mybatis_typeAlias+"\" resultType=\""+mybatis_typeAlias+"\">\r\nselect * from "+ TableName+"\r\n<where>\r\n";
        String variableName = fields[0].getName();
        selectSql += ("<if test=\"" + variableName + "!=null\"> \r\n" + variableName.toUpperCase() + "=#{" + variableName + "}</if>\r\n");
        for (int i = 1; i < fields.length; i++) {
            if(!"SAVETIME".equals(fields[i].getName().toUpperCase())){
                if("CREATETIME".equals(fields[i].getName().toUpperCase())){
                    variableName = fields[i].getName();
                    selectSql += ("<if test=\"" + variableName + "!=null\"> \r\nAND " + variableName.toUpperCase() + ">=#{" + variableName + "}</if>\r\n");
                }else{
                    variableName = fields[i].getName();
                    selectSql += ("<if test=\"" + variableName + "!=null\"> \r\nAND " + variableName.toUpperCase() + "=#{" + variableName + "}</if>\r\n");
                }
            }
        }
        selectSql += "\n</where>\r\n</select>";
        System.out.println(selectSql);
        //System.out.println("------------查询" + c.getName() + "对象sql语句结束----------\n");
    }

    public static void selectSql(Class c,String TableName ) {
        System.out.println("------------查询" + c.getName() + "对象sql语句--------------");
        Field[] fields = c.getDeclaredFields();
        String selectSql = "<select id=\"MethodName\" parameterType=\"BeanName\" resultType=\"BeanName\">\r\nselect * from "+ TableName+"\r\n<where>\r\n";
        String variableName = fields[0].getName();
        selectSql += ("<if test=\"" + variableName + "!=null\"> \r\n" + variableName.toUpperCase() + "=#{" + variableName + "}</if>\r\n");
        for (int i = 1; i < fields.length; i++) {
            if(!"SAVETIME".equals(fields[i].getName().toUpperCase())){
                if("CREATETIME".equals(fields[i].getName().toUpperCase())){
                    variableName = fields[i].getName();
                    selectSql += ("<if test=\"" + variableName + "!=null\"> \r\nAND " + variableName.toUpperCase() + ">=#{" + variableName + "}</if>\r\n");
                }else{
                    variableName = fields[i].getName();
                    selectSql += ("<if test=\"" + variableName + "!=null\"> \r\nAND " + variableName.toUpperCase() + "=#{" + variableName + "}</if>\r\n");
                }
            }
        }
        selectSql += "\n</where>\r\n</select>";
        System.out.println(selectSql);
        System.out.println("------------查询" + c.getName() + "对象sql语句结束----------\n");
    }

    public static void selectSql_MySQL_page(Class c,String TableName,String endwithfunname,String mybatis_typeAlias) {
       // System.out.println("------------分页查询" + c.getName() + "对象sql语句--------------");
        System.out.println("<!-- 分页查询sql语句：入库时间CREATETIME字段上要有索引，且分页的where条件中一定要带有CREATETIME条件 -->");
        System.out.println("<!-- 分页查询sql语句：最后一次更新时间SAVETIME字段用于查询结果的排序 -->");

        Field[] fields = c.getDeclaredFields();
        String selectSql = "<select id=\"get"+endwithfunname+"List\" parameterType=\""+mybatis_typeAlias+"\" resultType=\""+mybatis_typeAlias+"\">\r\nselect * from "+ TableName+"\r\n<where>\r\n";
        String variableName = fields[0].getName();
        selectSql += ("<if test=\"" + variableName + "!=null\"> \r\n" + variableName.toUpperCase() + "=#{" + variableName + "}</if>\r\n");
        for (int i = 1; i < fields.length; i++) {
            if(!"SAVETIME".equals(fields[i].getName().toUpperCase())){
                if("CREATETIME".equals(fields[i].getName().toUpperCase())){
                    variableName = fields[i].getName();
                    selectSql += ("<if test=\"" + variableName + "!=null\"> \r\nAND " + variableName.toUpperCase() + ">=#{" + variableName + "}</if>\r\n");
                }else{
                    variableName = fields[i].getName();
                    selectSql += ("<if test=\"" + variableName + "!=null\"> \r\nAND " + variableName.toUpperCase() + "=#{" + variableName + "}</if>\r\n");
                }
            }
        }
        selectSql += "</where> ORDER BY CREATETIME DESC \r\n " +
                "<if test=\"currentpage!=null and pagesize!=null\">\n" +
                "            <![CDATA[ LIMIT #{currentsize}, #{pagesize} ]]>\n" +
                "        </if> \n </select>";
        System.out.println(selectSql);
        //System.out.println("------------分页查询" + c.getName() + "对象sql语句结束----------\n");
    }
    public static void selectSql_MySQL_page(Class c,String TableName) {
        System.out.println("------------分页查询" + c.getName() + "对象sql语句--------------");
        System.out.println("------------分页查询sql语句：入库时间CREATETIME字段上要有索引，且分页的where条件中一定要带有CREATETIME条件--------------");
        System.out.println("------------分页查询sql语句：最后一次更新时间SAVETIME字段用于查询结果的排序--------------");

        Field[] fields = c.getDeclaredFields();
        String selectSql = "<select id=\"MethodName\" parameterType=\"BeanName\" resultType=\"BeanName\">\r\nselect * from "+ TableName+"\r\n<where>\r\n";
        String variableName = fields[0].getName();
        selectSql += ("<if test=\"" + variableName + "!=null\"> \r\n" + variableName.toUpperCase() + "=#{" + variableName + "}</if>\r\n");
        for (int i = 1; i < fields.length; i++) {
            if(!"SAVETIME".equals(fields[i].getName().toUpperCase())){
                if("CREATETIME".equals(fields[i].getName().toUpperCase())){
                    variableName = fields[i].getName();
                    selectSql += ("<if test=\"" + variableName + "!=null\"> \r\nAND " + variableName.toUpperCase() + ">=#{" + variableName + "}</if>\r\n");
                }else{
                    variableName = fields[i].getName();
                    selectSql += ("<if test=\"" + variableName + "!=null\"> \r\nAND " + variableName.toUpperCase() + "=#{" + variableName + "}</if>\r\n");
                }
            }
        }
        selectSql += "</where> ORDER BY SAVETIME DESC \r\n " +
                "<if test=\"currentPage!=null and pageSize!=null\">\n" +
                "            <![CDATA[ LIMIT #{currentSize}, #{pageSize} ]]>\n" +
                "        </if></select>";
        System.out.println(selectSql);
        System.out.println("------------分页查询" + c.getName() + "对象sql语句结束----------");
    }
    public static void selectSql_MySQL_pageGroups(Class c,String TableName,String endwithfunname,String mybatis_typeAlias) {
       // System.out.println("------------分页查询" + c.getName() + "对象sql语句--------------");
        System.out.println("<!-- 分页查询sql语句：入库时间CREATETIME字段上要有索引，且分页的where条件中一定要带有CREATETIME条件 -->");
        System.out.println("<!-- 分页查询sql语句：最后一次更新时间SAVETIME字段用于查询结果的排序 -->");

        Field[] fields = c.getDeclaredFields();
        String selectSql = "<select id=\"get"+endwithfunname+"Group\" parameterType=\""+mybatis_typeAlias+"\" resultType=\"hashMap\">\r\nselect IFNULL(COUNT(ID),0) CNT,IFNULL(MAX(ID),0) MAXID,IFNULL(MIN(ID),0) MINID from "+ TableName+"\r\n<where>\r\n";
        String variableName = fields[0].getName();
        selectSql += ("<if test=\"" + variableName + "!=null\"> \r\n" + variableName.toUpperCase() + "=#{" + variableName + "}</if>\r\n");
        for (int i = 1; i < fields.length; i++) {
            if(!"SAVETIME".equals(fields[i].getName().toUpperCase())){
                if("CREATETIME".equals(fields[i].getName().toUpperCase())){
                    variableName = fields[i].getName();
                    selectSql += ("<if test=\"" + variableName + "!=null\"> \r\nAND " + variableName.toUpperCase() + ">=#{" + variableName + "}</if>\r\n");
                }else{
                    variableName = fields[i].getName();
                    selectSql += ("<if test=\"" + variableName + "!=null\"> \r\nAND " + variableName.toUpperCase() + "=#{" + variableName + "}</if>\r\n");
                }
            }
        }
        selectSql += "\r\n</where>\r\n</select>";
        System.out.println(selectSql);
        //System.out.println("------------分页查询" + c.getName() + "对象sql语句结束----------\n");
    }
    public static void selectSql_MySQL_pageGroups(Class c,String TableName) {
        System.out.println("------------分页查询" + c.getName() + "对象sql语句--------------");
        System.out.println("------------分页查询sql语句：入库时间CREATETIME字段上要有索引，且分页的where条件中一定要带有CREATETIME条件--------------");
        System.out.println("------------分页查询sql语句：最后一次更新时间SAVETIME字段用于查询结果的排序--------------");

        Field[] fields = c.getDeclaredFields();
        String selectSql = "<select id=\"MethodNameGroups\" parameterType=\"BeanName\" resultType=\"hashMap\">\r\nselect IFNULL(COUNT(ID),0) CNT,IFNULL(MAX(ID),0) MAXID,IFNULL(MIN(ID),0) MINID from "+ TableName+"\r\n<where>\r\n";
        String variableName = fields[0].getName();
        selectSql += ("<if test=\"" + variableName + "!=null\"> \r\n" + variableName.toUpperCase() + "=#{" + variableName + "}</if>\r\n");
        for (int i = 1; i < fields.length; i++) {
            if(!"SAVETIME".equals(fields[i].getName().toUpperCase())){
                if("CREATETIME".equals(fields[i].getName().toUpperCase())){
                    variableName = fields[i].getName();
                    selectSql += ("<if test=\"" + variableName + "!=null\"> \r\nAND " + variableName.toUpperCase() + ">=#{" + variableName + "}</if>\r\n");
                }else{
                    variableName = fields[i].getName();
                    selectSql += ("<if test=\"" + variableName + "!=null\"> \r\nAND " + variableName.toUpperCase() + "=#{" + variableName + "}</if>\r\n");
                }
            }
        }
        selectSql += "</where> </select>";
        System.out.println(selectSql);
        System.out.println("------------分页查询" + c.getName() + "对象sql语句结束----------\n");
    }

//    public static void roll(Class c) {
//        try {
//
//            Class c = null;
//            c = Role.class;
//
//            int batch_insert_cnt = 20000;
//            int cnt = 51000;
//            int p = 0;
//            if (cnt % batch_insert_cnt == 0) {
//                p = cnt / batch_insert_cnt;
//            } else {
//                p = Math.round(cnt / batch_insert_cnt) + 1;      //10w条记录分批次insert，每次最多2w.
//            }
//            Long cnt_p = 0l;
//            System.out.println("p=" + p);
//            for (int i = 1; i <= p; i++) {
//                if (i == p) {
//                    cnt_p = (long) (cnt - (p - 1) * batch_insert_cnt);
//                    System.out.println("i=" + i + ",cnt_p=" + cnt_p);
//                } else {
//                    cnt_p = (long) batch_insert_cnt;
//                    System.out.println("i=" + i + ",cnt_p=" + (cnt_p));
//                }
//            }
//
////            addSql(c);
////            updateSql(c);
////            selectSql(c);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
