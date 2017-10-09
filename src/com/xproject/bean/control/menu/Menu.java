package com.xproject.bean.control.menu;

import com.xproject.bean.dao.system.TSysMenu;
import com.xproject.bean.dao.system.TSysRole;

import java.util.List;

/**
 * Created by Fiona on 2017/2/4.
 */
public class Menu extends TSysMenu {
    private String hasChildren;
    private Integer childrenSize;
    private TSysRole role;//对应角色bean
    private String ztreesstr;//所有菜单对应的信息
    private Integer roleid;//角色id
    private List<Menu> menus;

    public String getHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(String hasChildren) {
        this.hasChildren = hasChildren;
    }

    public Integer getChildrenSize() {
        return childrenSize;
    }

    public void setChildrenSize(Integer childrenSize) {
        this.childrenSize = childrenSize;
    }

    public TSysRole getRole() {
        return role;
    }

    public void setRole(TSysRole role) {
        this.role = role;
    }

    public String getZtreesstr() {
        return ztreesstr;
    }

    public void setZtreesstr(String ztreesstr) {
        this.ztreesstr = ztreesstr;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }
}
