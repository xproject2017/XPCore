package com.xproject.bean;

//import com.xproject.bean.system.core.TSysStaff;

import com.xproject.bean.dao.system.TSysStaff;
import net.sf.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Zhouxx
 * Date: 2017-9-20
 * Time: 下午12:31
 * 基bean
 */
public class BaseBean implements Serializable
{
    private Integer result;//结果字段，0：成功；1：失败
    private String messge;//当result为1时，存储错误信息
    private String act;

    private Integer pageflag;//是否分页标识，0不分页，1分页
    private Integer currentpage; //当前页码
    private Integer currentsize; //   currentSize=currentPage* pageSize      // LIMIT #{currentSize}, #{pageSize}
    private Integer totalpage;//总页数
    private Integer totalnum;//总记录数
    private Integer pagesize;//页数记录数

    private String token;//令牌
    private String from;
    private Integer clientflag;//请求来源

    private String tablename;//表名称
    private String databasename;//数据库名称
    private Integer dbcnt;//返回dml更新的行数
    private String stime;//开始时间 根据实际情况来确定时间格式  建议YYYY-MM-DD hh24:mi:ss
    private String etime;//结束时间
    private Date stimedb;//开始时间 转成日期型，提供给Dao层使用
    private Date etimedb;//结束时间
    private Integer readonly;//是否只读 0：否 1：是
    private TSysStaff user;//用户


    private String createtimestr;//入库时间字符串
    private String savetimestr;//更新时间字符串
    private Long savetimemills;//更新时间毫秒数
    private JSONObject data;//临时变量


    public JSONObject getData() {
        return data;
    }
    public void setData(JSONObject data) {
        this.data = data;
    }


    public String getCreatetimestr() {
        return createtimestr;
    }

    public void setCreatetimestr(String createtimestr) {
        this.createtimestr = createtimestr;
    }

    public String getSavetimestr() {
        return savetimestr;
    }

    public void setSavetimestr(String savetimestr) {
        this.savetimestr = savetimestr;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getMessge() {
        return messge;
    }

    public void setMessge(String messge) {
        this.messge = messge;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public Integer getPageflag() {
        return pageflag;
    }

    public void setPageflag(Integer pageflag) {
        this.pageflag = pageflag;
    }

    public Integer getCurrentpage() {
        return currentpage;
    }

    public void setCurrentpage(Integer currentpage) {
        this.currentpage = currentpage;
    }

    public Integer getCurrentsize() {
        return currentsize;
    }

    public void setCurrentsize(Integer currentsize) {
        this.currentsize = currentsize;
    }

    public Integer getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(Integer totalpage) {
        this.totalpage = totalpage;
    }

    public Integer getTotalnum() {
        return totalnum;
    }

    public void setTotalnum(Integer totalnum) {
        this.totalnum = totalnum;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Integer getClientflag() {
        return clientflag;
    }

    public void setClientflag(Integer clientflag) {
        this.clientflag = clientflag;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getDatabasename() {
        return databasename;
    }

    public void setDatabasename(String databasename) {
        this.databasename = databasename;
    }

    public Integer getDbcnt() {
        return dbcnt;
    }

    public void setDbcnt(Integer dbcnt) {
        this.dbcnt = dbcnt;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public Date getStimedb() {
        return stimedb;
    }

    public void setStimedb(Date stimedb) {
        this.stimedb = stimedb;
    }

    public Date getEtimedb() {
        return etimedb;
    }

    public void setEtimedb(Date etimedb) {
        this.etimedb = etimedb;
    }

    public Integer getReadonly() {
        return readonly;
    }

    public void setReadonly(Integer readonly) {
        this.readonly = readonly;
    }

    public TSysStaff getUser() {
        return user;
    }

    public void setUser(TSysStaff user) {
        this.user = user;
    }

    public Long getSavetimemills() {
        return savetimemills;
    }

    public void setSavetimemills(Long savetimemills) {
        this.savetimemills = savetimemills;
    }

}
