package com.xproject.manager.tx.system;

import com.xproject.bean.dao.system.TSysSession;

import java.util.HashMap;
import java.util.List;

/**
 ****标准Manager
 *此Manager接口由TSysSession类自动生成
 *@author zhouxx
 *@since 2016-11-02 17:06:08
 */

public interface TSysSessionManager {
    public TSysSession	getTSysSession(TSysSession bean);
    public List<TSysSession>	getTSysSessionList(TSysSession bean);
    public HashMap	getTSysSessionGroup(TSysSession bean);
    public TSysSession	addTSysSession(TSysSession bean);
    public TSysSession	updateTSysSession(TSysSession bean);
    public TSysSession	deleteTSysSession(TSysSession bean);
    public String generateSessionId(Integer userId);
}
