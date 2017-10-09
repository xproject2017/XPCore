package com.xproject.manager.tx.system;

import com.xproject.bean.dao.system.TSysMenu;
import com.xproject.bean.dao.system.TSysRole;
import	com.xproject.bean.dao.system.TSysStaff;
import com.xproject.bean.dao.system.TSysStaffMenu;

import java.util.List;
/**
 ****标准Manager
 *此Manager接口由TSysStaff类自动生成
 *@author zhouxx
 *@since 2016-11-02 17:06:08
 */

public interface TSysStaffManager{
    public TSysStaff	getTSysStaff(TSysStaff	bean);
    //查询用户名为@aaa的用户是否存在
    public TSysStaff	getTSysStaffLikeUsername(TSysStaff	bean);
    public List<TSysStaff>	getTSysStaffList(TSysStaff	bean);
    public TSysStaff	getTSysStaffGroup(TSysStaff	bean);
    public TSysStaff	addTSysStaff(TSysStaff	bean);
    public TSysStaff	updateTSysStaff(TSysStaff	bean);
    public TSysStaff	deleteTSysStaff(TSysStaff	bean);

    //获取角色的操作
    public TSysRole getTSysRole(TSysRole bean);
    //更新子局角色对应菜单
    public TSysRole updateTSysRole(TSysRole bean);
    //角色表的操作
    public List<TSysRole> getTSysRoleList(TSysRole bean);

    //根据用户菜单
    public List<TSysStaffMenu> getTSysStaffMenu(TSysStaff bean);
    public TSysStaff updateTSysStaffMenu(TSysStaff bean);
    public List<TSysMenu> getTSysMenuListByAuth(TSysRole bean);
    public List<TSysMenu> getTSysMenuListByRole(TSysRole bean);
}
