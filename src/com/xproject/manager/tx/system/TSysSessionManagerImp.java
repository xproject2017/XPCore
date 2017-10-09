package com.xproject.manager.tx.system;

import com.xproject.bean.BaseBean;
import com.xproject.bean.dao.system.TSysSession;
import com.xproject.dao.system.TSysSessionDao;
import com.xproject.manager.ManagerException;
import com.xproject.util.sys.ResultInfo4Manager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.*;

/**
 * Created by Administrator on 2016/11/2.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
@Transactional
public class TSysSessionManagerImp implements TSysSessionManager {
    private static Logger logger = Logger.getLogger(TSysSessionManagerImp.class);
    static private final int SessionValid = 0;
    @SuppressWarnings("unused")
    static private final int SessionInvalid = 1;
    // 20 minutes
    static private final int SeesionTimeout = 60 * 60;
    // 30 minutes
    static public final long SessionUpdateInterval = 60 * 30;
    private static Integer PID;//省局ID

//token结构：SESSIONID!SESSIONTOKEN!PID!USERTYPE!Userid!clientType
//        SESSIONID：主键
//        SESSIONTOKEN：32位不重复串
//        PID：省局id （0--255）  默认(-1)
//        USERTYPE：用户类型    默认(-1)
//        Userid：用户id（0--31）    默认(-1)
//        clientType:登录请求来源：1：业务层，2：移动端，3：工控端，4：对外接口，如优特网


    @Autowired
    private TSysSessionDao tSysSessionDao;

    private String byteToHexString(byte[] bytes) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {                                    //转化为16进制字符串
            byte b1 = (byte) ((bytes[i] & 0xf0) >> 4);
            byte b2 = (byte) (bytes[i] & 0x0f);
            if (b1 < 10)
                result.append((char) ('0' + b1));
            else
                result.append((char) ('A' + (b1 - 10)));
            if (b2 < 10)
                result.append((char) ('0' + b2));
            else
                result.append((char) ('A' + (b2 - 10)));
        }
        return result.toString();
    }

    @Override
    public String generateSessionId(Integer userId) {
        Random random = new SecureRandom();
        byte randomBytes[] = new byte[8];
        random.nextBytes(randomBytes);

        Date dateNow = new Date(System.currentTimeMillis());
        String token = byteToHexString(randomBytes) + Long.toHexString(userId | 0xF000000000000000L).substring(12, 16)
                + Long.toHexString(dateNow.getTime() | 0xF000000000000000L).substring(4, 16);
        return token.toUpperCase();
    }

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
    public TSysSession getTSysSession(TSysSession bean) {
        TSysSession result =null;
        try {
            result = tSysSessionDao.getTSysSession(bean);
            if (result == null) {
                result = new TSysSession();
                result.setResult(ResultInfo4Manager.NullOutput);
                result.setMessge(ResultInfo4Manager.ErrorNoDataFound);
                return result;
            }
            result.setResult(ResultInfo4Manager.Success);
            return result;
        }catch (Exception e){
            result = new TSysSession();
            result.setResult(ResultInfo4Manager.Fail);
            logger.error(e.getMessage());
            result.setMessge(ResultInfo4Manager.ErrorQuery);
            return result;
        }
    }

    @Override
    public List<TSysSession> getTSysSessionList(TSysSession bean) {
        List<TSysSession> list=null;
        try {
            if (bean.getPageflag() != null && bean.getPageflag() == 1) {//分页
                HashMap map = tSysSessionDao.getTSysSessionGroup(bean);
                setPageInfo(bean, map);
            }
            list = tSysSessionDao.getTSysSessionList(bean); //查询不到数据返回null
            if(list!=null) {
                if (list.size() > 0) {
                    list.get(0).setResult(ResultInfo4Manager.Success);
                    list.get(0).setCurrentpage(bean.getCurrentpage());
                    list.get(0).setPagesize(bean.getPagesize());
                    list.get(0).setTotalnum(bean.getTotalnum());
                }else {
                    list=new ArrayList<TSysSession>();
                    bean.setResult(ResultInfo4Manager.NullOutput);
                    bean.setMessge(ResultInfo4Manager.ErrorNoDataFound);
                    list.add(bean);
                }
            }
            return list;
        }catch (Exception e){
            logger.error(e.getMessage());
            list=new ArrayList<TSysSession>();
            bean.setResult(ResultInfo4Manager.Fail);
            bean.setMessge(ResultInfo4Manager.ErrorQuery);
            list.add(bean);
            return list;
        }
    }

    @Override
    public HashMap getTSysSessionGroup(TSysSession bean) {
        HashMap map =null;
        try{
            map = tSysSessionDao.getTSysSessionGroup(bean);
            return map;
        }catch (Exception e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
            return map;
        }
    }

    @Override
    public TSysSession addTSysSession(TSysSession bean) {
        //DML操作不要进行异常捕捉，异常统一由Spring管理，进行事务处理
        TSysSession result  = new TSysSession();
        //是否存在，最好是由前端的ajax校验
        int cnt=tSysSessionDao.addTSysSession(bean);
        result.setDbcnt(cnt);
        if(cnt!=1){
            throw new ManagerException(ResultInfo4Manager.ErrorDBOperation);
        }
        result.setResult(ResultInfo4Manager.Success);
        return result;
    }

    @Override
    public TSysSession updateTSysSession(TSysSession bean) {
        //DML操作不要进行异常捕捉，异常统一由Spring管理，进行事务处理
        TSysSession result  = new TSysSession();
        //是否存在，最好是由前端的ajax校验
        int cnt=tSysSessionDao.updateTSysSession(bean);
        result.setDbcnt(cnt);
        result.setResult(ResultInfo4Manager.Success);
        return result;
    }

    @Override
    public TSysSession deleteTSysSession(TSysSession bean) {
        //DML操作不要进行异常捕捉，异常统一由Spring管理，进行事务处理
        TSysSession result  = new TSysSession();
        //是否存在，最好是由前端的ajax校验
        int cnt=tSysSessionDao.deleteTSysSession(bean);
        result.setDbcnt(cnt);
        result.setResult(ResultInfo4Manager.Success);
        return result;
    }
}
