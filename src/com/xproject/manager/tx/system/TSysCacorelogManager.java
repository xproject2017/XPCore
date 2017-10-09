package com.xproject.manager.tx.system;

import com.xproject.bean.dao.system.TSysCacorelog;

import java.util.HashMap;
import java.util.List;

/**
 ****标准Manager
 *此Manager接口由TSysCacorelog类自动生成
 *@author zhouxx
 *@since 2016-11-02 17:06:08
 */

public interface TSysCacorelogManager {
    public TSysCacorelog	getTSysCacorelog(TSysCacorelog bean);
    public List<TSysCacorelog>	getTSysCacorelogList(TSysCacorelog bean);
    public HashMap	getTSysCacorelogGroup(TSysCacorelog bean);
    public TSysCacorelog	addTSysCacorelog(TSysCacorelog bean);
    public TSysCacorelog	updateTSysCacorelog(TSysCacorelog bean);
    public TSysCacorelog	deleteTSysCacorelog(TSysCacorelog bean);

}
