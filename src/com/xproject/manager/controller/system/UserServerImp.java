package com.xproject.manager.controller.system;

import com.xproject.bean.control.UserInfo;
import com.xproject.bean.dao.system.*;
import com.xproject.bean.manager.system.MMenuInfo;
import com.xproject.manager.ManagerException;
import com.xproject.manager.tx.system.TSysMenuManager;
import com.xproject.manager.tx.system.TSysSessionManager;
import com.xproject.manager.tx.system.TSysStaffManager;
import com.xproject.util.DataSourceContextHolder;
import com.xproject.util.PropertyFileter.JSONUtil;
import com.xproject.util.sys.*;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */
@Service
public class UserServerImp implements UserServer{
    private static Logger logger = Logger.getLogger(UserServerImp.class);

    @Autowired
    private TSysStaffManager tSysStaffManager;
    @Autowired
    private TSysSessionManager tSysSessionManager;
    @Autowired
    private TSysMenuManager tSysMenuManager;


//    @Value("#{configProperties['data.city.addstr']}")
//    HashMap dbkeyMap;

    @Value("#{baseinfoMap}")
    private HashMap<String, String>  baseinfoMap;



    @Override
    public TSysSession checkSysSession(TSysSession bean) {
        try {
            DataSourceContextHolder.setDBType(baseinfoMap.get("casystem.pid.prefix"));
            TSysSession where = new TSysSession();
            where.setSessionid(bean.getSessionid());
            where = tSysSessionManager.getTSysSession(where);
            if (!where.getSessiontoken().equals(bean.getToken())) {
                bean.setResult(ResultInfo4Contorl.Fail);
                bean.setMessge(ResultInfo4Contorl.ErrorTokenStr);
                return bean;
            }
            //token没有超时机制，业务需求，永不超时。。。。
            //如需引入超时机制，则修改此处
            bean.setResult(ResultInfo4Contorl.Success);
            return bean;
        } catch (Exception e) {
            logger.error(e.getMessage());
            bean.setResult(ResultInfo4Contorl.Fail);
            bean.setMessge(ResultInfo4Contorl.ErrorTokenStr);
        }
        return bean;
    }

    @Override
    public UserInfo loginapi(UserInfo userinfo) {
        try {

            //切换数据库 --默认省局数据库 （权限管理全部都上移到省局控制）
            DataSourceContextHolder.setDBType(baseinfoMap.get("casystem.pid.prefix"));

            TSysStaff tSysStaff=tSysStaffManager.getTSysStaff(userinfo);
            if(tSysStaff.getResult()!= ResultInfo4Manager.Success){
                userinfo.setResult(ResultInfo4Contorl.Fail);
                userinfo.setMessge(ResultInfo4Contorl.ErrorLogin);
                return userinfo;
            }else {
                if (tSysStaff.getStatus() == Constants.STATUS_DIABLED){
                    userinfo.setResult(ResultInfo4Contorl.Fail);
                    userinfo.setMessge(ResultInfo4Contorl.ErrorLoginStatusDisabled);
                    return userinfo;
                }
                int clientflag = userinfo.getClientflag();
                BeanUtils.copyProperties(tSysStaff,userinfo);
                userinfo.setClientflag(clientflag);
                if (tSysStaff.getUsertype() != Constants.USER_TYPE_FACTORY) {
                    TSysRole role = new TSysRole();
                    role.setRoleid(userinfo.getRoleid());
                    userinfo.setRolename(tSysStaffManager.getTSysRole(role).getRolename());
                }else {
                    userinfo.setRolename(Constants.ROLENAMEFACTORYMAIN);
                }
            }
            //0局方账号，1企业账号
            if(tSysStaff.getUsertype()== Constants.USER_TYPE_FACTORY) {

            }

            //token信息--待补充
            //根据userId动态生成token
            String token = tSysSessionManager.generateSessionId(tSysStaff.getUserid());
            Date dateNow = new Date(System.currentTimeMillis());
            TSysSession usersession = new TSysSession(dateNow, token, tSysStaff.getUserid(), Constants.SessionValid_0);
            //为用户添加token
            tSysSessionManager.addTSysSession(usersession);  //表里存储的token和session的token不一样
            //数据库中的格式：token
//session中的格式：token流水号|token|局方id|USERTYPE|用户id|clientType
//token结构：SESSIONID|SESSIONTOKEN|PID|USERTYPE|Userid!clientType
//        SESSIONID：主键
//        SESSIONTOKEN：32位不重复串
//        PID：省局id （0--255）  默认(-1)
//        USERTYPE：用户类型    默认(-1)
//        Userid：用户id（0--31）    默认(-1)
//        clientType:登录请求来源：1：业务层，2：移动端，3：工控端，4：对外接口，如优特网
            userinfo.setToken(usersession.getSessionid() + TokenUtil.sp + token + TokenUtil.sp + baseinfoMap.get("casystem.pid") + TokenUtil.sp + tSysStaff.getUsertype() + TokenUtil.sp + tSysStaff.getUserid() + TokenUtil.sp + userinfo.getClientflag());

            //填充转义信息

            userinfo.setResult(ResultInfo4Contorl.Success);
            return userinfo;
        }  catch (Exception e) {
            logger.error(e.getMessage());
            userinfo.setResult(ResultInfo4Contorl.Fail);
            userinfo.setMessge(ResultInfo4Contorl.ErrorLoginFail);
        }
        return userinfo;
    }

    @Override
    public UserInfo addUser(UserInfo bean) {
        try {
            DataSourceContextHolder.setDBType(baseinfoMap.get("xproject.pid.prefix"));
            UserInfo user = new UserInfo();
//            String[] suffix = bean.getUsername().split("@");
            user.setUsername(bean.getUsername());
            TSysStaff retUser = tSysStaffManager.getTSysStaff(user);

            if (retUser != null&&retUser.getResult()==0) {
                bean.setResult(ResultInfo4Contorl.Fail);
                bean.setMessge(ResultInfo4Contorl.ErrorUserNameExist);//用户名已经存在
                return bean;
            } else {
//                bean.setUsername(baseinfoMap.get("casystem.pid.prefix") + "@" + bean.getUsername());//添加前缀
                bean.setProvinceid(Integer.parseInt(baseinfoMap.get("xproject.pid")));
                TSysRole role = new TSysRole();
                role.setRolename(Constants.ROLENAMEFACTORYSUB);
                TSysRole retRole = tSysStaffManager.getTSysRole(role);
                bean.setRoleid(retRole.getRoleid());
                bean.setStatus(Constants.STATUS_ENABLED);
                bean.setCompanyid(bean.getCompanyid());
                bean.setFatherid(TokenUtil.getUseridByToken(bean.getToken()));
                bean.setUserstatus(Constants.USER_STATUS_ONLINE);
                bean.setUsertype(Constants.USER_TYPE_FACTORY);
                bean.setSendflag("000");
                bean.setUflag(Constants.USER_UFLAG_ENABLED);//正常
                if (bean.getVendorids()!=null&&!"".equals(bean.getVendorids())) {
                    bean.setVendorids(dealVendorids(bean.getVendorids()));//把vendorid,vendorid处理成[vendorid],[vendorid]
                }
                if (bean.getMenusstr()!=null&&!"".equals(bean.getMenusstr())){
                    String[] menuArr = bean.getMenusstr().replaceAll(",$","").split(",");
                    List<TSysStaffMenu> menus = new ArrayList<>();
                    for (String menu:menuArr){
                        TSysStaffMenu staffmenu = new TSysStaffMenu();
                        staffmenu.setMenuid(Integer.parseInt(menu));
                        menus.add(staffmenu);
                    }
                    bean.setMenus(menus);
                }
                bean.setNusertype(Constants.NUSER_TYPE_SUB);
                TSysStaff retstaff = tSysStaffManager.addTSysStaff(bean);
                bean.setResult(retstaff.getResult());
                bean.setMessge(retstaff.getMessge());
            }
        }catch (Exception e) {
            logger.error(e.getMessage());
            bean.setResult(ResultInfo4Contorl.Fail);
            bean.setMessge(ResultInfo4Contorl.ErrorLoginFail);
        }
        return bean;
    }

    @Override
    public UserInfo updateUser(UserInfo bean) {

        try {
            DataSourceContextHolder.setDBType(baseinfoMap.get("xproject.pid.prefix"));
            if (bean.getVendorids() != null && !"".equals(bean.getVendorids())) {
                bean.setVendorids(dealVendorids(bean.getVendorids()));//把vendorid,vendorid处理成[vendorid],[vendorid]
            }
            if (bean.getMenusstr() != null && !"".equals(bean.getMenusstr())) {
                String[] menuArr = bean.getMenusstr().replaceAll(",$", "").split(",");
                List<TSysStaffMenu> menus = new ArrayList<>();
                for (String menuid : menuArr) {
                    TSysStaffMenu staffMenu = new TSysStaffMenu();
                    staffMenu.setUserid(bean.getUserid());
                    staffMenu.setMenuid(Integer.parseInt(menuid));
                    menus.add(staffMenu);
                }
                bean.setMenus(menus);
                tSysStaffManager.updateTSysStaffMenu(bean);
            }
//            bean.setSavetime(new Date(bean.getSavetimemills()));
            TSysStaff retuser = tSysStaffManager.updateTSysStaff(bean);
            bean.setResult(retuser.getResult());
            bean.setMessge(retuser.getMessge());
        }catch (ManagerException e){
            bean.setResult(ResultInfo4Manager.Fail);
            bean.setMessge(e.getMessage());
        }catch (Exception e){
            logger.error(e.getMessage());
            bean.setResult(ResultInfo4Manager.Fail);
            bean.setMessge(e.getMessage());
        }
        return bean;
    }

    @Override
    public List<UserInfo> getUserList(UserInfo bean) {
        DataSourceContextHolder.setDBType(baseinfoMap.get("xproject.pid.prefix"));
        List<UserInfo> retList = new ArrayList<>();
        List<TSysStaff> retstafflist = tSysStaffManager.getTSysStaffList(bean);
        for (TSysStaff staff:retstafflist){
            UserInfo userInfo = new UserInfo();
            BeanUtils.copyProperties(staff,userInfo);
            retList.add(userInfo);
        }
        return retList;
    }

    @Override
    public UserInfo getUserInfo(UserInfo bean) {
        DataSourceContextHolder.setDBType(baseinfoMap.get("xproject.pid.prefix"));
        TSysStaff retuser = tSysStaffManager.getTSysStaff(bean);
        BeanUtils.copyProperties(retuser,bean);
        if (retuser.getVendorids()!=null&&!"".equals(retuser.getVendorids())) {

        }
//        bean.setUflagname(uflagMap.get(bean.getUflag()));
        bean.setSavetimemills(bean.getSavetime().getTime());
        List<TSysStaffMenu> menus = tSysStaffManager.getTSysStaffMenu(bean);
        //监管企业暂时没做，企业功能完成后完善
        bean.setMenus(menus);
        return bean;
    }

    //把vendorid,vendorid处理成[vendorid],[vendorid]
    private String dealVendorids(String vendoridstr){
        String[] vendorids = vendoridstr.replaceAll(",$","").split(",");
        StringBuffer sb = new StringBuffer();
        for (String vendorid : vendorids) {
            sb.append("[").append(vendorid).append("],");
        }
        return sb.toString().replaceAll(",$","");
    }

    @Override
    public List<MMenuInfo> getMenuListByAuthrole(TSysRole bean) {
        DataSourceContextHolder.setDBType(baseinfoMap.get("xproject.pid.prefix"));
        List<TSysMenu> menus = tSysStaffManager.getTSysMenuListByAuth(bean);
        List<MMenuInfo> firstmenuList = MenuUtil.processMenu(menus);
        return firstmenuList;
    }

    @Override
    public List<TSysStaffMenu> getTSysStaffMenuByUser(UserInfo bean) {
        DataSourceContextHolder.setDBType(baseinfoMap.get("xproject.pid.prefix"));
        return tSysStaffManager.getTSysStaffMenu(bean);
    }

    @Override
    public List<MMenuInfo> getTSysMenuByUser(UserInfo bean) {
        DataSourceContextHolder.setDBType(baseinfoMap.get("xproject.pid.prefix"));
        TSysStaff staff = new TSysStaff();
        staff.setUserid(bean.getUserid());
        TSysRole roleparam = new TSysRole();
        roleparam.setRolename(bean.getRolename());
        TSysRole currRole = tSysStaffManager.getTSysRole(roleparam);
        staff.setRolepopedom(currRole.getRolepopedom());
        List<TSysStaffMenu> menus =  tSysStaffManager.getTSysStaffMenu(staff);
        if (menus!=null&&menus.size()>0) {
            StringBuffer menuids = new StringBuffer();
            for (TSysStaffMenu menu : menus) {
                menuids.append(menu.getMenuid()).append(",");
            }
            TSysRole role = new TSysRole();
            role.setRolepopedom(menuids.toString().replaceAll(",$", ""));
            List<TSysMenu> menulist = tSysMenuManager.getTSysMenuListByRole(role);
            List<MMenuInfo> firstmenuList = MenuUtil.processMenu(menulist);
            return firstmenuList;
        }else {
            return new ArrayList<>();
        }

    }

    @Override
    public List<MMenuInfo> getTSysMenuByFatherUser(UserInfo bean) {
        DataSourceContextHolder.setDBType(baseinfoMap.get("xproject.pid.prefix"));
        TSysStaff staff = new TSysStaff();
        staff.setUserid(bean.getUserid());
        List<TSysStaffMenu> menus =  tSysStaffManager.getTSysStaffMenu(staff);
        if (menus!=null&&menus.size()>0) {
            StringBuffer menuids = new StringBuffer();
            for (TSysStaffMenu menu : menus) {
                menuids.append(menu.getMenuid()).append(",");
            }
            TSysRole role = new TSysRole();
            role.setRolepopedom(menuids.toString().replaceAll(",$", ""));
            List<TSysMenu> menulist = tSysMenuManager.getTSysMenuListByRole(role);
            List<MMenuInfo> firstmenuList = MenuUtil.processMenu(menulist);
            return firstmenuList;
        }else {
            return new ArrayList<>();
        }

    }

    @Override
    public TSysStaff getUserInfoByToken(TSysSession bean) {
        DataSourceContextHolder.setDBType(baseinfoMap.get("xproject.pid.prefix"));
        TSysStaff r = new TSysStaff();
        String token=bean.getToken();
        if(!TokenUtil.checkTokenFormat(token)){
            r.setResult(ResultInfo4Contorl.Fail);
            r.setMessge(ResultInfo4Contorl.ErrorTokenStr);
            return r;
        }
        int userid = TokenUtil.getUseridByToken(token);
        UserInfo result = new UserInfo();
        result.setUserid(userid);
        r = tSysStaffManager.getTSysStaff(result);
        return r;
    }
}
