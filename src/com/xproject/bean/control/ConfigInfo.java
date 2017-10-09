package com.xproject.bean.control;

import com.xproject.bean.dao.system.TBaseConfig;

/**
 * Created by LeWonya on 2017/6/27 0027.
 */
public class ConfigInfo extends TBaseConfig {
    private String password;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
