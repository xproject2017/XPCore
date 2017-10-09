package com.xproject.bean.control;

//import com.xproject.bean.dao.org.TBusiVendors;
import com.xproject.bean.dao.system.TSysStaff;
import net.sf.json.JSONArray;

import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */
public class UserInfo extends TSysStaff {
    private String vendorname;
    private String usernameprefix;
    private String menusstr;//菜单，逗号分割
    private String usertypename;//用户类型名称
    private String uflagname;//账号状态名称  启用/禁用
    private String newuserpassword;//新密码
    //private List<TBusiVendors> vendorslist;//监管企业列表
    private String rolename;//角色名称
    private Integer onebatchflag;//一批一码标识，0：关闭，1：开启
    private JSONArray tagflagtype;//如果一批一码状态为开启，则填充这个字段，供新增批次时前端使用
    public UserInfo(){

    }
    public UserInfo(TSysStaff tSysStaff){

    }

    public Integer getOnebatchflag() {
        return onebatchflag;
    }

    public void setOnebatchflag(Integer onebatchflag) {
        this.onebatchflag = onebatchflag;
    }

    public String getVendorname() {
        return vendorname;
    }

    public void setVendorname(String vendorname) {
        this.vendorname = vendorname;
    }


    public String getUsernameprefix() {
        return usernameprefix;
    }

    public void setUsernameprefix(String usernameprefix) {
        this.usernameprefix = usernameprefix;
    }

    public String getMenusstr() {
        return menusstr;
    }

    public void setMenusstr(String menusstr) {
        this.menusstr = menusstr;
    }

    public String getUsertypename() {
        return usertypename;
    }

    public void setUsertypename(String usertypename) {
        this.usertypename = usertypename;
    }

    public String getUflagname() {
        return uflagname;
    }

    public void setUflagname(String uflagname) {
        this.uflagname = uflagname;
    }


    public String getNewuserpassword() {
        return newuserpassword;
    }

    public void setNewuserpassword(String newuserpassword) {
        this.newuserpassword = newuserpassword;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public JSONArray getTagflagtype() {
        return tagflagtype;
    }

    public void setTagflagtype(JSONArray tagflagtype) {
        this.tagflagtype = tagflagtype;
    }
}
