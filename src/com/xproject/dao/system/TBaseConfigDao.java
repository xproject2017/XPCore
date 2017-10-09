package com.xproject.dao.system;

import	com.xproject.bean.dao.system.TBaseConfig;

import java.util.HashMap;
import java.util.List;
/**
 ****标准DAO
 *此Dao接口由TBaseConfig类自动生成
 *@author zhouxx
 *@since 2016-11-02 15:35:34
 */
public interface TBaseConfigDao{
    public TBaseConfig	getTBaseConfig(TBaseConfig	bean);
    public List<TBaseConfig>	getTBaseConfigList(TBaseConfig	bean);
    public HashMap	getTBaseConfigGroup(TBaseConfig	bean);
    public int	addTBaseConfig(TBaseConfig	bean);
    public int	updateTBaseConfig(TBaseConfig	bean);
    public int	deleteTBaseConfig(TBaseConfig	bean);

}