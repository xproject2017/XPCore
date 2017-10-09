package com.xproject.dao.system;

import	com.xproject.bean.dao.system.TSysCacorelog;
import java.util.HashMap;
import java.util.List;
/**
 ****标准DAO
 *此Dao接口由TSysCacorelog类自动生成
 *@author zhouxx
 *@since 2016-11-02 16:22:28
 */

public interface TSysCacorelogDao{
    public TSysCacorelog	getTSysCacorelog(TSysCacorelog	bean);
    public List<TSysCacorelog>	getTSysCacorelogList(TSysCacorelog	bean);
    public HashMap	getTSysCacorelogGroup(TSysCacorelog	bean);
    public int	addTSysCacorelog(TSysCacorelog	bean);
    public int	updateTSysCacorelog(TSysCacorelog	bean);
    public int	deleteTSysCacorelog(TSysCacorelog	bean);

}
