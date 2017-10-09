package com.xproject.dao.system;

import	com.xproject.bean.dao.system.TSysMenu;
import com.xproject.bean.dao.system.TSysRole;

import java.util.HashMap;
import java.util.List;
/**
 ****标准DAO
 *此Dao接口由TSysMenu类自动生成
 *@author zhouxx
 *@since 2017-01-23 15:01:48
 */

public interface TSysMenuDao{
    public TSysMenu	getTSysMenu(TSysMenu	bean);
    public List<TSysMenu>	getTSysMenuList(TSysMenu	bean);
    public HashMap	getTSysMenuGroup(TSysMenu	bean);
    public int	addTSysMenu(TSysMenu	bean);
    public int	updateTSysMenu(TSysMenu	bean);
    public int	deleteTSysMenu(TSysMenu	bean);
    //根据角色的peopdom获取所有对应菜单列表
    public List<TSysMenu> getTSysMenuListByRole(TSysRole bean);

}
