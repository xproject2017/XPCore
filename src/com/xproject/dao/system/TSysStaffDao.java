package com.xproject.dao.system;

import com.xproject.bean.dao.system.TSysRole;
import	com.xproject.bean.dao.system.TSysStaff;
import com.xproject.bean.dao.system.TSysStaffMenu;

import java.util.HashMap;
import java.util.List;
/**
 ****标准DAO
 *此Dao接口由TSysStaff类自动生成
 *@author zhouxx
 *@since 2016-11-02 16:38:11
 */

public interface TSysStaffDao{
    public TSysStaff	getTSysStaff(TSysStaff	bean);
    public TSysStaff	getTSysStaffLikeUsername(TSysStaff	bean);
    public List<TSysStaff>	getTSysStaffList(TSysStaff	bean);
    public HashMap	getTSysStaffGroup(TSysStaff	bean);
    public int	addTSysStaff(TSysStaff	bean);
    public int	updateTSysStaff(TSysStaff	bean);
    public int	deleteTSysStaff(TSysStaff	bean);

    public List<TSysStaffMenu>	getTSysStaffMenuList(TSysStaff bean);
    public int	addTSysStaffMenu(TSysStaffMenu bean);
    public int	deleteTSysStaffMenu(TSysStaff bean);
    public TSysRole getTSysRole(TSysRole	bean);
    public int updateTSysRole(TSysRole	bean);
    public List<TSysRole> getTSysRoleList(TSysRole	bean);
}
