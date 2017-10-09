package com.xproject.manager.tx.system;

import com.xproject.bean.control.menu.Menu;
import	com.xproject.bean.dao.system.TSysMenu;
import com.xproject.bean.dao.system.TSysRole;

import java.util.HashMap;
import java.util.List;
/**
 ****标准Manager
 *此Manager接口由TSysMenu类自动生成
 *@author zhouxx
 *@since 2017-01-23 15:01:48
 */

public interface TSysMenuManager{
    public TSysMenu	getTSysMenu(TSysMenu	bean);
    public List<TSysMenu>	getTSysMenuList(TSysMenu	bean);
    public List<TSysMenu>	getTSysMenuListByRole(TSysRole bean);
    public HashMap	getTSysMenuGroup(TSysMenu	bean);
    public Menu	addTSysMenu(Menu bean);
    public Menu	updateTSysMenu(Menu	bean);
    public TSysMenu	deleteTSysMenu(TSysMenu	bean);

}