package com.xproject.bean.dao.system;

import com.xproject.bean.BaseBean;

/**
 * Created by Fiona on 2017/1/13.
 */
public class TSysStaffMenu extends BaseBean {
    private Integer id;
    private Integer userid;
    private Integer menuid;
    private Integer authid;//子局id
    private Integer provinceid;//省局id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getMenuid() {
        return menuid;
    }

    public void setMenuid(Integer menuid) {
        this.menuid = menuid;
    }

    public Integer getAuthid() {
        return authid;
    }

    public void setAuthid(Integer authid) {
        this.authid = authid;
    }

    public Integer getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(Integer provinceid) {
        this.provinceid = provinceid;
    }
}
