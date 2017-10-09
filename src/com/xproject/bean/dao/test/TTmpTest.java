package com.xproject.bean.dao.test;

import com.xproject.bean.BaseBean;
import java.util.Date;

	/**
	*此类由MySQL2Bean工具自动生成
	*备注(数据表的comment字段)：测试表
	*@author zhouxx
	*@since 2016-11-08 17:12:23
	*/

	public class TTmpTest	 extends BaseBean{
	private Integer id;//主键-自增流水
	private String title;//标题
	private String news;//新闻内容
	private Date savetime;//最后一次更新时间
	private Date createtime;//入库时间
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id=id;
	}
	public String getTitle(){
		return this.title;
	}
	public void setTitle(String title){
		this.title=title;
	}
	public String getNews(){
		return this.news;
	}
	public void setNews(String news){
		this.news=news;
	}
	public Date getSavetime(){
		return this.savetime;
	}
	public void setSavetime(Date savetime){
		this.savetime=savetime;
	}
	public Date getCreatetime(){
		return this.createtime;
	}
	public void setCreatetime(Date createtime){
		this.createtime=createtime;
	}

}