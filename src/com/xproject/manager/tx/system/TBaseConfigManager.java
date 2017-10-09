package com.xproject.manager.tx.system;

import com.xproject.bean.dao.system.TBaseConfig;

import java.util.HashMap;
import java.util.List;

/**
 ****标准Manager
 *此Manager接口由TBaseConfig类自动生成
 *@author zhouxx
 *@since 2016-11-02 17:06:08
 */

public interface TBaseConfigManager {
    public TBaseConfig	getTBaseConfig(TBaseConfig bean);
    public List<TBaseConfig>	getTBaseConfigList(TBaseConfig bean);
    public HashMap	getTBaseConfigGroup(TBaseConfig bean);
    public TBaseConfig	addTBaseConfig(TBaseConfig bean);
    public TBaseConfig	updateTBaseConfig(TBaseConfig bean);
    public TBaseConfig	deleteTBaseConfig(TBaseConfig bean);

}
