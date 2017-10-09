package com.xproject.util.sys;

import com.xproject.bean.dao.system.TSysMenu;
import com.xproject.bean.manager.system.MMenuInfo;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fiona on 2017/1/18.
 */
public class MenuUtil {
    //传入菜单列表，返回处理成一二级关系的一级菜单列表
    public static List<MMenuInfo> processMenu(List<TSysMenu> menus) {
        List<MMenuInfo> firstmenuList = new ArrayList<>();
        List<TSysMenu> secmenuList = new ArrayList<>();
        for (TSysMenu menu:menus){
            if (menu.getNodelevel() == 1) {
                MMenuInfo newmenu = new MMenuInfo();
                BeanUtils.copyProperties(menu,newmenu);
                firstmenuList.add(newmenu);
            }else if (menu.getNodelevel() == 2){
                secmenuList.add(menu);
            }
        }

        for (MMenuInfo firstmenu:firstmenuList){
            List<TSysMenu> sonmenus = new ArrayList<>();
            for (TSysMenu secmenu:secmenuList){
                if (secmenu.getFnodeid().equals(firstmenu.getNodeid())){
                    sonmenus.add(secmenu);
                }
            }
            firstmenu.setSonmenus(sonmenus);
        }
        return firstmenuList;
    }
}
