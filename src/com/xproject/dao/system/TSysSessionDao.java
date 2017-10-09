package com.xproject.dao.system;

import	com.xproject.bean.dao.system.TSysSession;
import java.util.HashMap;
import java.util.List;
/**
 ****标准DAO
 *此Dao接口由TSysSession类自动生成
 *@author zhouxx
 *@since 2016-11-02 16:32:08
 */

public interface TSysSessionDao{
    public TSysSession	getTSysSession(TSysSession	bean);
    public List<TSysSession>	getTSysSessionList(TSysSession	bean);
    public HashMap	getTSysSessionGroup(TSysSession	bean);
    public int	addTSysSession(TSysSession	bean);
    public int	updateTSysSession(TSysSession	bean);
    public int	deleteTSysSession(TSysSession	bean);

}
