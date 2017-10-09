package com.xproject.bean.dao.system;

import com.xproject.bean.BaseBean;
import java.util.Date;
import java.util.List;

/**
	*此类由MySQL2Bean工具自动生成
	*备注(数据表的comment字段)：用户表
	*@author zhouxx
	*@since 2016-11-02 15:45:22
	*/

	public class TSysStaff	 extends BaseBean{
	private Integer userid;//用户id,主键，自增长
	private Integer roleid;//	角色id
	private Integer authid;//子局id
	private Integer provinceid;//省局id
	private Integer rolelevel;//0：科长，1：检验员
	private Integer status;//0：不启用，1：启用
	private Long companyid;//所属企业id
	private Integer fatherid;//父账号id
	private String useremail;//email
	private String usermobilephone;//联系手机
	private String username;//登录名
	private String userpassword;//登录密码（MD5密文）
	private Integer userstatus;//1在线，2离线
	private String usertelephone;//联系电话
	private Integer usertype;//0局方账号，1企业账号
	private String vendorids;//企业id,逗号分隔
	private Date savetime;//最后一次更新时间
	private String jobnumber;//员工号
	private String department;//所属部门
	private String sendflag;//提醒标志：100备案，010报检，001召回
	private Integer uflag;//0正常，1注销
	private String uname;//员工姓名
	private String ufax;//传真
	private Integer nusertype;//0:主账号，1:子账号
	private Date createtime;//入库时间
	private List<TSysStaffMenu> menus;//账户管理的菜单列表
	private String rolepopedom;//角色对应的菜单，角色没有该菜单时，要过滤掉 不显示

	public List<TSysStaffMenu> getMenus() {
		return menus;
	}

	public void setMenus(List<TSysStaffMenu> menus) {
		this.menus = menus;
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

	public Integer getUserid(){
		return this.userid;
	}
	public void setUserid(Integer userid){
		this.userid=userid;
	}
	public Integer getRoleid(){
		return this.roleid;
	}
	public void setRoleid(Integer roleid){
		this.roleid=roleid;
	}
	public Integer getRolelevel(){
		return this.rolelevel;
	}
	public void setRolelevel(Integer rolelevel){
		this.rolelevel=rolelevel;
	}
	public Integer getStatus(){
		return this.status;
	}
	public void setStatus(Integer status){
		this.status=status;
	}
	public Long getCompanyid(){
		return this.companyid;
	}
	public void setCompanyid(Long companyid){
		this.companyid=companyid;
	}
	public Integer getFatherid(){
		return this.fatherid;
	}
	public void setFatherid(Integer fatherid){
		this.fatherid=fatherid;
	}
	public String getUseremail(){
		return this.useremail;
	}
	public void setUseremail(String useremail){
		this.useremail=useremail;
	}
	public String getUsermobilephone(){
		return this.usermobilephone;
	}
	public void setUsermobilephone(String usermobilephone){
		this.usermobilephone=usermobilephone;
	}
	public String getUsername(){
		return this.username;
	}
	public void setUsername(String username){
		this.username=username;
	}
	public String getUserpassword(){
		return this.userpassword;
	}
	public void setUserpassword(String userpassword){
		this.userpassword=userpassword;
	}
	public Integer getUserstatus(){
		return this.userstatus;
	}
	public void setUserstatus(Integer userstatus){
		this.userstatus=userstatus;
	}
	public String getUsertelephone(){
		return this.usertelephone;
	}
	public void setUsertelephone(String usertelephone){
		this.usertelephone=usertelephone;
	}
	public Integer getUsertype(){
		return this.usertype;
	}
	public void setUsertype(Integer usertype){
		this.usertype=usertype;
	}
	public String getVendorids(){
		return this.vendorids;
	}
	public void setVendorids(String vendorids){
		this.vendorids=vendorids;
	}
	public Date getSavetime(){
		return this.savetime;
	}
	public void setSavetime(Date savetime){
		this.savetime=savetime;
	}
	public String getJobnumber(){
		return this.jobnumber;
	}
	public void setJobnumber(String jobnumber){
		this.jobnumber=jobnumber;
	}
	public String getDepartment(){
		return this.department;
	}
	public void setDepartment(String department){
		this.department=department;
	}
	public String getSendflag(){
		return this.sendflag;
	}
	public void setSendflag(String sendflag){
		this.sendflag=sendflag;
	}
	public Integer getUflag(){
		return this.uflag;
	}
	public void setUflag(Integer uflag){
		this.uflag=uflag;
	}
	public String getUname(){
		return this.uname;
	}
	public void setUname(String uname){
		this.uname=uname;
	}
	public String getUfax(){
		return this.ufax;
	}
	public void setUfax(String ufax){
		this.ufax=ufax;
	}
	public Integer getNusertype(){
		return this.nusertype;
	}
	public void setNusertype(Integer nusertype){
		this.nusertype=nusertype;
	}
	public Date getCreatetime(){
		return this.createtime;
	}
	public void setCreatetime(Date createtime){
		this.createtime=createtime;
	}

	public String getRolepopedom() {
		return rolepopedom;
	}

	public void setRolepopedom(String rolepopedom) {
		this.rolepopedom = rolepopedom;
	}
}