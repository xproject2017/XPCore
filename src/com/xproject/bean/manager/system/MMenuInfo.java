package com.xproject.bean.manager.system;

import com.xproject.bean.dao.system.TSysMenu;

import java.util.List;

/**
 * Created by Fiona on 2017/1/14.
 */
public class MMenuInfo extends TSysMenu {
    private List<TSysMenu> sonmenus;

    public List<TSysMenu> getSonmenus() {
        return sonmenus;
    }

    public void setSonmenus(List<TSysMenu> sonmenus) {
        this.sonmenus = sonmenus;
    }
}
