package com.xproject.manager.tx.system;

import com.xproject.bean.BaseBean;
import com.xproject.bean.dao.system.TSysMenu;
import com.xproject.bean.dao.system.TSysRole;
import com.xproject.bean.dao.system.TSysStaff;
import com.xproject.bean.dao.system.TSysStaffMenu;
import com.xproject.dao.system.TSysMenuDao;
import com.xproject.dao.system.TSysStaffDao;
import com.xproject.manager.ManagerException;
import com.xproject.util.sys.ResultInfo4Manager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/11/2.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
@Transactional
public class TSysStaffManagerImp implements TSysStaffManager {
    private static Logger logger = Logger.getLogger(TSysStaffManagerImp.class);

    @Autowired
    private TSysStaffDao tSysStaffDao;
    @Autowired
    private TSysMenuDao tSysMenuDao;


    private void setPageInfo(BaseBean baseBean,HashMap hashMap) {
        if (baseBean.getCurrentpage() == null) { //检查当前页
            baseBean.setCurrentpage(0);
        }
        if (baseBean.getPagesize() == null) { //检查每页数量
            baseBean.setPagesize(20);
        }
        baseBean.setCurrentsize(baseBean.getCurrentpage() * baseBean.getPagesize());
        baseBean.setTotalnum(Integer.valueOf(String.valueOf(hashMap.get("CNT"))));
    }

    @Override
    public TSysStaff getTSysStaff(TSysStaff bean) {
        TSysStaff result =null;
        try {
            result = tSysStaffDao.getTSysStaff(bean);
            if (result == null) {
                result = new TSysStaff();
                result.setResult(ResultInfo4Manager.NullOutput);
                result.setMessge(ResultInfo4Manager.ErrorNoDataFound);
                return result;
            }
            //写入token，并返回token
            result.setResult(ResultInfo4Manager.Success);
            return result;
        }catch (Exception e){
            result = new TSysStaff();
            result.setResult(ResultInfo4Manager.Fail);
            logger.error(e.getMessage());
            result.setMessge(ResultInfo4Manager.ErrorQuery);
            return result;
        }
    }

    @Override
    public TSysStaff getTSysStaffLikeUsername(TSysStaff bean) {
        TSysStaff result =null;
        try {
            result = tSysStaffDao.getTSysStaffLikeUsername(bean);
            if (result == null) {
                result = new TSysStaff();
                result.setResult(ResultInfo4Manager.NullOutput);
                result.setMessge(ResultInfo4Manager.ErrorNoDataFound);
                return result;
            }
            //写入token，并返回token
            result.setResult(ResultInfo4Manager.Success);
            return result;
        }catch (Exception e){
            result = new TSysStaff();
            result.setResult(ResultInfo4Manager.Fail);
            logger.error(e.getMessage());
            result.setMessge(ResultInfo4Manager.ErrorQuery);
            return result;
        }
    }

    @Override
    public List<TSysStaff> getTSysStaffList(TSysStaff bean) {
        List<TSysStaff> list=null;
        try {
            if (bean.getPageflag() != null && bean.getPageflag() == 1) {//分页
                HashMap map = tSysStaffDao.getTSysStaffGroup(bean);
                setPageInfo(bean, map);
            }
            list = tSysStaffDao.getTSysStaffList(bean); //查询不到数据返回null
            if(list!=null) {
                if (list.size() > 0) {
                    list.get(0).setResult(ResultInfo4Manager.Success);
                    list.get(0).setCurrentpage(bean.getCurrentpage());
                    list.get(0).setPagesize(bean.getPagesize());
                    list.get(0).setTotalnum(bean.getTotalnum());
                }else {
                    list=new ArrayList<TSysStaff>();
                    bean.setResult(ResultInfo4Manager.NullOutput);
                    bean.setMessge(ResultInfo4Manager.ErrorNoDataFound);
                    list.add(bean);
                }
            }
            return list;
        }catch (Exception e){
            logger.error(e.getMessage());
            list=new ArrayList<TSysStaff>();
            bean.setResult(ResultInfo4Manager.Fail);
            bean.setMessge(ResultInfo4Manager.ErrorQuery);
            list.add(bean);
            return list;
        }
    }

    @Override
    public TSysStaff getTSysStaffGroup(TSysStaff bean) {
        try{
            HashMap map = tSysStaffDao.getTSysStaffGroup(bean);
            Integer CNT = Integer.valueOf(String.valueOf(map.get("CNT")));
            bean.setTotalnum(CNT);
            if (bean.getPageflag() == null || bean.getPageflag() == 0) {
                bean.setCurrentpage(0);
                bean.setPagesize(CNT);
                bean.setTotalpage(1);
                return bean;
            }
            if (bean.getPageflag() == null) {
                bean.setPagesize(5);
            } else {
                if (bean.getPageflag()== 0) {
                    bean.setPagesize(5);
                }
            }
            if (bean.getCurrentpage() == null) {
                bean.setCurrentpage(0);
            }

            if (bean.getTotalnum() % bean.getPagesize() == 0) {
                bean.setTotalpage(bean.getTotalnum() / bean.getPagesize());
            } else {
                bean.setTotalpage(bean.getTotalnum() / bean.getPagesize() + 1);
            }
        }catch (Exception e) {
            logger.error(e.getMessage());
            return bean;
        }
        return bean;
    }

    @Override
    public TSysStaff addTSysStaff(TSysStaff bean) {
        //DML操作不要进行异常捕捉，异常统一由Spring管理，进行事务处理
        TSysStaff result  = new TSysStaff();
        //是否存在，最好是由前端的ajax校验
        int cnt=tSysStaffDao.addTSysStaff(bean);
        result.setDbcnt(cnt);
        if(cnt!=1){
            throw new ManagerException(ResultInfo4Manager.ErrorDBOperation);
        }
        //插staffmenu表
        if (bean.getMenus()!=null&&bean.getMenus().size()>0){
            for (TSysStaffMenu staffmenu:bean.getMenus()){
                staffmenu.setUserid(bean.getUserid());
                int cnt1 = tSysStaffDao.addTSysStaffMenu(staffmenu);
                if (cnt1!=1){
                    throw new ManagerException(ResultInfo4Manager.ErrorDBOperation);
                }
            }
        }
        result.setResult(ResultInfo4Manager.Success);
        return result;
    }

    @Override
    public TSysStaff updateTSysStaff(TSysStaff bean) {
        //DML操作不要进行异常捕捉，异常统一由Spring管理，进行事务处理
        TSysStaff result  = new TSysStaff();
        //是否存在，最好是由前端的ajax校验
        int cnt=tSysStaffDao.updateTSysStaff(bean);
        result.setDbcnt(cnt);
        result.setResult(ResultInfo4Manager.Success);
        return result;
    }

    @Override
    public TSysStaff deleteTSysStaff(TSysStaff bean) {
        //DML操作不要进行异常捕捉，异常统一由Spring管理，进行事务处理
        TSysStaff result  = new TSysStaff();
        //是否存在，最好是由前端的ajax校验
        int cnt=tSysStaffDao.deleteTSysStaff(bean);
        result.setDbcnt(cnt);
        result.setResult(ResultInfo4Manager.Success);
        return result;
    }

    @Override
    public TSysRole getTSysRole(TSysRole bean) {
        TSysRole result =null;
        try {
            result = tSysStaffDao.getTSysRole(bean);
            if (result == null) {
                result = new TSysRole();
                result.setResult(ResultInfo4Manager.NullOutput);
                result.setMessge(ResultInfo4Manager.ErrorNoDataFound);
                return result;
            }
            //写入token，并返回token
            result.setResult(ResultInfo4Manager.Success);
            return result;
        }catch (Exception e){
            result = new TSysRole();
            result.setResult(ResultInfo4Manager.Fail);
            logger.error(e.getMessage());
            result.setMessge(ResultInfo4Manager.ErrorQuery);
            return result;
        }
    }

    @Override
    public TSysRole updateTSysRole(TSysRole bean) {
        int cnt = tSysStaffDao.updateTSysRole(bean);
        if (cnt!=1){
            throw new ManagerException(ResultInfo4Manager.ErrorDBOperation);
        }
        bean.setResult(ResultInfo4Manager.Success);
        return bean;
    }

    @Override
    public TSysStaff updateTSysStaffMenu(TSysStaff bean) {
        if (bean.getMenus()!=null&&bean.getMenus().size()>0){
            tSysStaffDao.deleteTSysStaffMenu(bean);
            for (TSysStaffMenu staffMenu:bean.getMenus()) {
                int cnt = tSysStaffDao.addTSysStaffMenu(staffMenu);
                if (cnt!=1){
                    throw new ManagerException(ResultInfo4Manager.ErrorDBOperation);
                }
            }
        }
        bean.setResult(ResultInfo4Manager.Success);
        return bean;
    }

    @Override
    public List<TSysStaffMenu> getTSysStaffMenu(TSysStaff bean) {
        return tSysStaffDao.getTSysStaffMenuList(bean);
    }


    @Override
    public List<TSysMenu> getTSysMenuListByAuth(TSysRole bean) {
        List<TSysMenu> list=null;
        try {
            TSysRole retRole = tSysStaffDao.getTSysRole(bean);
            list = tSysMenuDao.getTSysMenuListByRole(retRole); //查询不到数据返回null
            return list;
        }catch (Exception e){
            logger.error(e.getMessage());
            list=new ArrayList<>();
            TSysMenu menu = new TSysMenu();
            menu.setResult(ResultInfo4Manager.Fail);
            menu.setMessge(ResultInfo4Manager.ErrorQuery);
            list.add(menu);
            return list;
        }
    }

    @Override
    public List<TSysMenu> getTSysMenuListByRole(TSysRole bean) {
        List<TSysMenu> list=null;
        try {
            list = tSysMenuDao.getTSysMenuListByRole(bean); //查询不到数据返回null
            return list;
        }catch (Exception e){
            logger.error(e.getMessage());
            list=new ArrayList<>();
            TSysMenu menu = new TSysMenu();
            menu.setResult(ResultInfo4Manager.Fail);
            menu.setMessge(ResultInfo4Manager.ErrorQuery);
            list.add(menu);
            return list;
        }
    }

    @Override
    public List<TSysRole> getTSysRoleList(TSysRole bean) {
        List<TSysRole> list=null;
        try {
            list = tSysStaffDao.getTSysRoleList(bean); //查询不到数据返回null
            return list;
        }catch (Exception e){
            logger.error(e.getMessage());
            list=new ArrayList<>();
            TSysRole role = new TSysRole();
            role.setResult(ResultInfo4Manager.Fail);
            role.setMessge(ResultInfo4Manager.ErrorQuery);
            list.add(role);
            return list;
        }
    }
}
