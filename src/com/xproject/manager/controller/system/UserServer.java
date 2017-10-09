package com.xproject.manager.controller.system;

import com.xproject.bean.control.UserInfo;
import com.xproject.bean.dao.system.TSysRole;
import com.xproject.bean.dao.system.TSysSession;
import com.xproject.bean.dao.system.TSysStaff;
import com.xproject.bean.dao.system.TSysStaffMenu;
import com.xproject.bean.manager.system.MMenuInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */
public interface UserServer {
    public TSysSession checkSysSession(TSysSession bean);
    public TSysStaff getUserInfoByToken(TSysSession bean);

    UserInfo loginapi(UserInfo userinfo);

    //用户新增
    UserInfo addUser(UserInfo bean);
    //用户修改
    UserInfo updateUser(UserInfo bean);
    //获取用户列表
    List<UserInfo> getUserList(UserInfo bean);
    //获取用户详情
    UserInfo getUserInfo(UserInfo bean);
    //获取子局对应角色下的全量菜单,封装成一级包含二级的形式
    List<MMenuInfo> getMenuListByAuthrole(TSysRole bean);

    List<TSysStaffMenu> getTSysStaffMenuByUser(UserInfo bean);
    //根据用户id获取该用户的菜单 封装成一二级形式
    public List<MMenuInfo> getTSysMenuByFatherUser(UserInfo bean);
    //根据用户id获取该用户角色下的完整菜单信息
    List<MMenuInfo> getTSysMenuByUser(UserInfo bean);
}
