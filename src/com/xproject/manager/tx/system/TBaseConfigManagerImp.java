package com.xproject.manager.tx.system;

import com.xproject.bean.BaseBean;
import com.xproject.bean.dao.system.TBaseConfig;
import com.xproject.dao.system.TBaseConfigDao;
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
public class TBaseConfigManagerImp implements TBaseConfigManager {
    private static Logger logger = Logger.getLogger(TBaseConfigManagerImp.class);

    @Autowired
    private TBaseConfigDao tBaseConfigDao;

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
    public TBaseConfig getTBaseConfig(TBaseConfig bean) {
        TBaseConfig result =null;
        try {
            result = tBaseConfigDao.getTBaseConfig(bean);
            if (result == null) {
                result = new TBaseConfig();
                result.setResult(ResultInfo4Manager.NullOutput);
                result.setMessge(ResultInfo4Manager.ErrorNoDataFound);
                return result;
            }
            result.setResult(ResultInfo4Manager.Success);
            return result;
        }catch (Exception e){
            result = new TBaseConfig();
            result.setResult(ResultInfo4Manager.Fail);
            logger.error(e.getMessage());
            result.setMessge(ResultInfo4Manager.ErrorQuery);
            return result;
        }
    }

    @Override
    public List<TBaseConfig> getTBaseConfigList(TBaseConfig bean) {
        List<TBaseConfig> list=null;
        try {
            if (bean.getPageflag() != null && bean.getPageflag() == 1) {//分页
                HashMap map = tBaseConfigDao.getTBaseConfigGroup(bean);
                setPageInfo(bean, map);
            }
            list = tBaseConfigDao.getTBaseConfigList(bean); //查询不到数据返回null
            if(list!=null) {
                if (list.size() > 0) {
                    list.get(0).setResult(ResultInfo4Manager.Success);
                    list.get(0).setCurrentpage(bean.getCurrentpage());
                    list.get(0).setPagesize(bean.getPagesize());
                    list.get(0).setTotalnum(bean.getTotalnum());
                }else {
                    list=new ArrayList<TBaseConfig>();
                    bean.setResult(ResultInfo4Manager.NullOutput);
                    bean.setMessge(ResultInfo4Manager.ErrorNoDataFound);
                    list.add(bean);
                }
            }
            return list;
        }catch (Exception e){
            logger.error(e.getMessage());
            list=new ArrayList<TBaseConfig>();
            bean.setResult(ResultInfo4Manager.Fail);
            bean.setMessge(ResultInfo4Manager.ErrorQuery);
            list.add(bean);
            return list;
        }
    }

    @Override
    public HashMap getTBaseConfigGroup(TBaseConfig bean) {
        HashMap map =null;
        try{
            map = tBaseConfigDao.getTBaseConfigGroup(bean);
            return map;
        }catch (Exception e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
            return map;
        }
    }

    @Override
    public TBaseConfig addTBaseConfig(TBaseConfig bean) {
        //DML操作不要进行异常捕捉，异常统一由Spring管理，进行事务处理
        TBaseConfig result  = new TBaseConfig();
        //是否存在，最好是由前端的ajax校验
        int cnt=tBaseConfigDao.addTBaseConfig(bean);
        result.setDbcnt(cnt);
        if(cnt!=1){
            throw new ManagerException(ResultInfo4Manager.ErrorDBOperation);
        }
        result.setResult(ResultInfo4Manager.Success);
        return result;
    }

    @Override
    public TBaseConfig updateTBaseConfig(TBaseConfig bean) {
        //DML操作不要进行异常捕捉，异常统一由Spring管理，进行事务处理
        TBaseConfig result  = new TBaseConfig();
        //是否存在，最好是由前端的ajax校验
        int cnt=tBaseConfigDao.updateTBaseConfig(bean);
        result.setDbcnt(cnt);
        result.setResult(ResultInfo4Manager.Success);
        return result;
    }

    @Override
    public TBaseConfig deleteTBaseConfig(TBaseConfig bean) {
        //DML操作不要进行异常捕捉，异常统一由Spring管理，进行事务处理
        TBaseConfig result  = new TBaseConfig();
        //是否存在，最好是由前端的ajax校验
        int cnt=tBaseConfigDao.deleteTBaseConfig(bean);
        result.setDbcnt(cnt);
        result.setResult(ResultInfo4Manager.Success);
        return result;
    }
}
