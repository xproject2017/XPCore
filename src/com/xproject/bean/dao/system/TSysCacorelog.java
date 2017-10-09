package com.xproject.bean.dao.system;

import com.xproject.bean.BaseBean;
import java.util.Date;

	/**
	*此类由MySQL2Bean工具自动生成
	*备注(数据表的comment字段)：日志记录表
	*@author zhouxx
	*@since 2016-11-02 15:45:22
	*/

	public class TSysCacorelog	 extends BaseBean{
	private Long id;//自增长流水号
	private Long userid;//操作用户ID
	private String input;//操作输入
	private String output;//操作输出
	private Integer type;//操作模块类型编号
	private Long typeid;//对应类型ID
	private Date createdate;//操作时间
	private String url;//访问URL
	public Long getId(){
		return this.id;
	}
	public void setId(Long id){
		this.id=id;
	}
	public Long getUserid(){
		return this.userid;
	}
	public void setUserid(Long userid){
		this.userid=userid;
	}
	public String getInput(){
		return this.input;
	}
	public void setInput(String input){
		this.input=input;
	}
	public String getOutput(){
		return this.output;
	}
	public void setOutput(String output){
		this.output=output;
	}
	public Integer getType(){
		return this.type;
	}
	public void setType(Integer type){
		this.type=type;
	}
	public Long getTypeid(){
		return this.typeid;
	}
	public void setTypeid(Long typeid){
		this.typeid=typeid;
	}
	public Date getCreatedate(){
		return this.createdate;
	}
	public void setCreatedate(Date createdate){
		this.createdate=createdate;
	}
	public String getUrl(){
		return this.url;
	}
	public void setUrl(String url){
		this.url=url;
	}

}