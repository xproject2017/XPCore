package com.xproject.bean.dao.system;

import com.xproject.bean.BaseBean;
import java.util.Date;

	/**
	*此类由MySQL2Bean工具自动生成
	*备注(数据表的comment字段)：角色表
	*@author zhouxx
	*@since 2016-11-02 15:45:22
	*/

	public class TSysRole	 extends BaseBean{
	private Integer roleid;//角色id,主键，自增
		private Integer authid;//子局id
		private Integer provinceid;//省局id
	private String rolename;//角色名(管理员，省局，子局，进口企业，出口企业，普通企业)
	private String rolepopedom;//菜单id,逗号分隔
	private Date createtime;//入库时间
	private Date savetime;//最后一次更新时间
		private String aaids;//多个子局id,
	public Integer getRoleid(){
		return this.roleid;
	}
	public void setRoleid(Integer roleid){
		this.roleid=roleid;
	}
	public String getRolename(){
		return this.rolename;
	}
	public void setRolename(String rolename){
		this.rolename=rolename;
	}
	public String getRolepopedom(){
		return this.rolepopedom;
	}
	public void setRolepopedom(String rolepopedom){
		this.rolepopedom=rolepopedom;
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

		public String getAaids() {
			return aaids;
		}

		public void setAaids(String aaids) {
			this.aaids = aaids;
		}

		public Integer getAuthid() {
			return authid;
		}

		public void setAuthid(Integer authid) {
			this.authid = authid;
		}

		public Integer getProvinceid() {
			return provinceid;
		}

		public void setProvinceid(Integer provinceid) {
			this.provinceid = provinceid;
		}
	}