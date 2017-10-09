package com.xproject.bean.dao.system;

import com.xproject.bean.BaseBean;
import java.util.Date;

	/**
	*此类由MySQL2Bean工具自动生成
	*备注(数据表的comment字段)：菜单表
	*@author zhouxx
	*@since 2016-11-02 15:45:22
	*/

	public class TSysMenu	 extends BaseBean{
	private Integer nodeid;//菜单id,主键
	private Integer nodelevel;//1：一级菜单，2：二级菜单，3：三级菜单
	private String nodecode;//菜单编码
	private String nodetext;//菜单名
	private String path;//菜单URL
	private Integer fnodeid;//父节点菜单id
	private Integer mtype;//0局方菜单，1企业菜单，2运维人员菜单
	private Date createtime;//入库时间
	private Date savetime;//最后一次更新时间
	private String coding;//菜单顺序，默认三级：00 00 00
	private String comment;//备注d
		private boolean checked;
	public Integer getNodeid(){
		return this.nodeid;
	}
	public void setNodeid(Integer nodeid){
		this.nodeid=nodeid;
	}
	public Integer getNodelevel(){
		return this.nodelevel;
	}
	public void setNodelevel(Integer nodelevel){
		this.nodelevel=nodelevel;
	}
	public String getNodecode(){
		return this.nodecode;
	}
	public void setNodecode(String nodecode){
		this.nodecode=nodecode;
	}
	public String getNodetext(){
		return this.nodetext;
	}
	public void setNodetext(String nodetext){
		this.nodetext=nodetext;
	}
	public String getPath(){
		return this.path;
	}
	public void setPath(String path){
		this.path=path;
	}
	public Integer getFnodeid(){
		return this.fnodeid;
	}
	public void setFnodeid(Integer fnodeid){
		this.fnodeid=fnodeid;
	}
	public Integer getMtype(){
		return this.mtype;
	}
	public void setMtype(Integer mtype){
		this.mtype=mtype;
	}
	public Date getCreatetime(){
		return this.createtime;
	}
	public void setCreatetime(Date createtime){
		this.createtime=createtime;
	}
	public Date getSavetime(){
		return this.savetime;
	}
	public void setSavetime(Date savetime){
		this.savetime=savetime;
	}
	public String getCoding(){
		return this.coding;
	}
	public void setCoding(String coding){
		this.coding=coding;
	}
	public String getComment(){
		return this.comment;
	}
	public void setComment(String comment){
		this.comment=comment;
	}

		public boolean isChecked() {
			return checked;
		}

		public void setChecked(boolean checked) {
			this.checked = checked;
		}
	}