package com.xproject.bean.control;

import com.xproject.bean.BaseBean;

/**
 * Created by Administrator on 2017/2/28.
 */
public class ImageBean extends BaseBean {
    String filename;//文件名
    String suffix; //前缀
    Integer w; //宽
    Integer h; //高
    Integer picindex;  //下标
    Integer pvendorid;
    String inspectcode;
    String typeid;
    String prstypeid;
    Integer materialid;
    String checkid;

    public String getCheckid() {
        return checkid;
    }

    public void setCheckid(String checkid) {
        this.checkid = checkid;
    }

    public String getInspectcode() {
        return inspectcode;
    }

    public void setInspectcode(String inspectcode) {
        this.inspectcode = inspectcode;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public   ImageBean(){

    }
    public   ImageBean(String filename,String suffix,Integer w,Integer h,Integer picindex,String inspectcode,String typeid){
          this.filename=filename;
          this.suffix=suffix;
          this.w=w;
          this.h=h;
          this.picindex=picindex;
        this.inspectcode = inspectcode;
        this.typeid = typeid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Integer getW() {
        return w;
    }

    public void setW(Integer w) {
        this.w = w;
    }

    public Integer getH() {
        return h;
    }

    public void setH(Integer h) {
        this.h = h;
    }

    public Integer getPicindex() {
        return picindex;
    }

    public void setPicindex(Integer picindex) {
        this.picindex = picindex;
    }

    public Integer getPvendorid() {
        return pvendorid;
    }

    public void setPvendorid(Integer pvendorid) {
        this.pvendorid = pvendorid;
    }

    public Integer getMaterialid() {
        return materialid;
    }

    public void setMaterialid(Integer materialid) {
        this.materialid = materialid;
    }

    public String getPrstypeid() {
        return prstypeid;
    }

    public void setPrstypeid(String prstypeid) {
        this.prstypeid = prstypeid;
    }
}
