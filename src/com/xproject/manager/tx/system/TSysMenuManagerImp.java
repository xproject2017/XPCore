package com.xproject.manager.tx.system;

import com.xproject.bean.control.menu.Menu;
import com.xproject.bean.dao.system.TSysMenu;
import com.xproject.bean.dao.system.TSysRole;
import com.xproject.dao.system.TSysMenuDao;
import com.xproject.dao.system.TSysStaffDao;
import com.xproject.manager.ManagerException;
import com.xproject.util.sys.ResultInfo4Manager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Fiona on 2017/1/23.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
@Transactional
public class TSysMenuManagerImp implements TSysMenuManager {
    private static Logger logger = Logger.getLogger(TSysStaffManagerImp.class);

    @Autowired
    private TSysMenuDao tSysMenuDao;
    @Autowired
    private TSysStaffDao tSysStaffDao;

    @Override
    public TSysMenu getTSysMenu(TSysMenu bean) {
        return tSysMenuDao.getTSysMenu(bean);
    }

    @Override
    public List<TSysMenu> getTSysMenuList(TSysMenu bean) {
        return tSysMenuDao.getTSysMenuList(bean);
    }

    @Override
    public List<TSysMenu> getTSysMenuListByRole(TSysRole bean) {
        return tSysMenuDao.getTSysMenuListByRole(bean);
    }

    @Override
    public HashMap getTSysMenuGroup(TSysMenu bean) {
        return tSysMenuDao.getTSysMenuGroup(bean);
    }

    @Override
    public Menu addTSysMenu(Menu bean) {
        int cnt = tSysMenuDao.addTSysMenu(bean);
        if (cnt!=1){
            throw new ManagerException(ResultInfo4Manager.ErrorDBOperation);
        }
        bean.setResult(ResultInfo4Manager.Success);
        return bean;
    }

    @Override
    public Menu updateTSysMenu(Menu menu) {
        TSysRole role = tSysStaffDao.getTSysRole(menu.getRole());
        for(Menu m:menu.getMenus()){
            if(m.getNodeid()==null){
                m.setMtype(4);
                int  cnt = tSysMenuDao.addTSysMenu(m);
                if (cnt!=1){
                    throw new ManagerException(ResultInfo4Manager.ErrorDBOperation);
                }
                role.setRolepopedom(String.valueOf(role.getRolepopedom()==""?m.getNodeid():role.getRolepopedom()+","+m.getNodeid()));
                int cnt1 = tSysStaffDao.updateTSysRole(role);
            }
            else {
                int cnt1 = tSysMenuDao.updateTSysMenu(m);
            }
        }
        menu.setResult(ResultInfo4Manager.Success);
        return menu;
    }

    @Override
    public TSysMenu deleteTSysMenu(TSysMenu bean) {
        int cnt = tSysMenuDao.deleteTSysMenu(bean);
        if (cnt!=1){
            throw new ManagerException(ResultInfo4Manager.ErrorDBOperation);
        }
        bean.setResult(ResultInfo4Manager.Success);
        return bean;
    }
}
