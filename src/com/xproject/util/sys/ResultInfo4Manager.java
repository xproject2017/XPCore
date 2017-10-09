package com.xproject.util.sys;

/**记录Manager中的返回结果信息
 *@author zhouxx
 *@since 2016-11-02 17:06:08
 */
public interface ResultInfo4Manager {
    public static final int Success = 0;//数据库操作成功，如果是查询操作，查询结果不为空
    public static final int Fail = 1;//数据库操作失败
    public static final int NullOutput = 2;                   //查询操作成功，但查询结果为空
    public static final int UpDate_0 = 3;	//UPDATE操作成功，但是未更新到数据  (记录不存在或者未修改)
    public static final int Delete_0 = 4;	//DELETE操作成功，但是未删除到数据   (记录不存在)

    public static final int ErrorToken = 7;                   //无效的Token
    public static final int ErrorTokenTimeout = 8;                   //无效的Token

    public static final String ErrorRecordExist="记录已存在";
    public static final String ErrorNoDataFound="暂无数据";//查询操作成功，但查询结果为空
    public static final String ErrorQuery= "查询失败";//"数据库查询失败";
    public static final String ErrorDBOperation = "操作失败";//"数据库操作失败";
    public static final String ErrorTokenStr ="无效的Token";
    public static final String ErrorTokenTimeoutStr ="Token超时";
    public static final String ErrorParameter ="输入参数有误";
    public static final String ErrorApplyZeroTag = "申请0个标签";
    public static final String ErrorLackofBalance  = "余额不足";


    public static final String ErrorInvalidCode  = "无效的认证信息！";
}
