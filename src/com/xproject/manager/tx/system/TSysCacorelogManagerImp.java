package com.xproject.manager.tx.system;

import com.xproject.bean.BaseBean;
import com.xproject.bean.dao.system.TSysCacorelog;
import com.xproject.dao.system.TSysCacorelogDao;
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
public class TSysCacorelogManagerImp implements TSysCacorelogManager {
    private static Logger logger = Logger.getLogger(TSysCacorelogManagerImp.class);

    @Autowired
    private TSysCacorelogDao tSysCacorelogDao;


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
    public TSysCacorelog getTSysCacorelog(TSysCacorelog bean) {
        TSysCacorelog result =null;
        try {
            result = tSysCacorelogDao.getTSysCacorelog(bean);
            if (result == null) {
                result = new TSysCacorelog();
                result.setResult(ResultInfo4Manager.NullOutput);
                result.setMessge(ResultInfo4Manager.ErrorNoDataFound);
                return result;
            }
            result.setResult(ResultInfo4Manager.Success);
            return result;
        }catch (Exception e){
            result = new TSysCacorelog();
            result.setResult(ResultInfo4Manager.Fail);
            logger.error(e.getMessage());
            result.setMessge(ResultInfo4Manager.ErrorQuery);
            return result;
        }
    }

    @Override
    public List<TSysCacorelog> getTSysCacorelogList(TSysCacorelog bean) {
        List<TSysCacorelog> list=null;
        try {
            if (bean.getPageflag() != null && bean.getPageflag() == 1) {//分页
                HashMap map = tSysCacorelogDao.getTSysCacorelogGroup(bean);
                setPageInfo(bean, map);
            }
            list = tSysCacorelogDao.getTSysCacorelogList(bean); //查询不到数据返回null
            if(list!=null) {
                if (list.size() > 0) {
                    list.get(0).setResult(ResultInfo4Manager.Success);
                    list.get(0).setCurrentpage(bean.getCurrentpage());
                    list.get(0).setPagesize(bean.getPagesize());
                    list.get(0).setTotalnum(bean.getTotalnum());
                }else {
                    list=new ArrayList<TSysCacorelog>();
                    bean.setResult(ResultInfo4Manager.NullOutput);
                    bean.setMessge(ResultInfo4Manager.ErrorNoDataFound);
                    list.add(bean);
                }
            }
            return list;
        }catch (Exception e){
            logger.error(e.getMessage());
            list=new ArrayList<TSysCacorelog>();
            bean.setResult(ResultInfo4Manager.Fail);
            bean.setMessge(ResultInfo4Manager.ErrorQuery);
            list.add(bean);
            return list;
        }
    }

    @Override
    public HashMap getTSysCacorelogGroup(TSysCacorelog bean) {
        HashMap map =null;
        try{
            map = tSysCacorelogDao.getTSysCacorelogGroup(bean);
            return map;
        }catch (Exception e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
            return map;
        }
    }

    @Override
    public TSysCacorelog addTSysCacorelog(TSysCacorelog bean) {
        //DML操作不要进行异常捕捉，异常统一由Spring管理，进行事务处理
        TSysCacorelog result  = new TSysCacorelog();
        //是否存在，最好是由前端的ajax校验
        int cnt=tSysCacorelogDao.addTSysCacorelog(bean);
        result.setDbcnt(cnt);
        if(cnt!=1){
            throw new ManagerException(ResultInfo4Manager.ErrorDBOperation);
        }
        result.setResult(ResultInfo4Manager.Success);
        return result;
    }

    @Override
    public TSysCacorelog updateTSysCacorelog(TSysCacorelog bean) {
        //DML操作不要进行异常捕捉，异常统一由Spring管理，进行事务处理
        TSysCacorelog result  = new TSysCacorelog();
        //是否存在，最好是由前端的ajax校验
        int cnt=tSysCacorelogDao.updateTSysCacorelog(bean);
        result.setDbcnt(cnt);
        result.setResult(ResultInfo4Manager.Success);
        return result;
    }

    @Override
    public TSysCacorelog deleteTSysCacorelog(TSysCacorelog bean) {
        //DML操作不要进行异常捕捉，异常统一由Spring管理，进行事务处理
        TSysCacorelog result  = new TSysCacorelog();
        //是否存在，最好是由前端的ajax校验
        int cnt=tSysCacorelogDao.deleteTSysCacorelog(bean);
        result.setDbcnt(cnt);
        result.setResult(ResultInfo4Manager.Success);
        return result;
    }
}
