package com.xproject.bean.dao.system;

import com.xproject.bean.BaseBean;
import java.util.Date;

	/**
	*此类由MySQL2Bean工具自动生成
	*备注(数据表的comment字段)：参数表
	*@author zhouxx
	*@since 2016-11-02 15:45:22
	*/

	public class TBaseConfig	 extends BaseBean{
	private Integer id;//主键-自增流水
	private String cnfkey;//参数
	private String cnfvalue;//参数值
	private String cnftitle;//参数名称
	private Integer cnftype;//参数类型:0整型，1浮点型，2字符型
	private Integer astatus;//状态，0未启用，1启用
	private Date savetime;//最后一次更新时间
	private String remark;//备注
	private Date createtime;//入库时间
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id=id;
	}
	public String getCnfkey(){
		return this.cnfkey;
	}
	public void setCnfkey(String cnfkey){
		this.cnfkey=cnfkey;
	}
	public String getCnfvalue(){
		return this.cnfvalue;
	}
	public void setCnfvalue(String cnfvalue){
		this.cnfvalue=cnfvalue;
	}
	public String getCnftitle(){
		return this.cnftitle;
	}
	public void setCnftitle(String cnftitle){
		this.cnftitle=cnftitle;
	}
	public Integer getCnftype(){
		return this.cnftype;
	}
	public void setCnftype(Integer cnftype){
		this.cnftype=cnftype;
	}
	public Integer getAstatus(){
		return this.astatus;
	}
	public void setAstatus(Integer astatus){
		this.astatus=astatus;
	}
	public Date getSavetime(){
		return this.savetime;
	}
	public void setSavetime(Date savetime){
		this.savetime=savetime;
	}
	public String getRemark(){
		return this.remark;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}
	public Date getCreatetime(){
		return this.createtime;
	}
	public void setCreatetime(Date createtime){
		this.createtime=createtime;
	}

}