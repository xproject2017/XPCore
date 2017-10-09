package com.xproject.util.PropertyFileter;

/**
 * Created by Administrator on 2017/1/19.
 */
public class Property {
    /*只给业务平台使用，不能用于app的筛选过滤*/
    private String key;
    private String title;
    private String type; //type字段：0字符，1数字，2图片，3日期（年），4日期（年-月），5日期（年-月-日），6字符，7超链接，8图片，9文件
    private String flag;  //详情页面控制 flag字段：1必填，2不必填，3不显示
    private String appChecked;//app控制 1显示，2不显示
    private String value;
    private String url;
    private String entitle;
    private String text;
    private String maxlength;//每个字段的最长值

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getAppChecked() {
        return appChecked;
    }

    public void setAppChecked(String appChecked) {
        this.appChecked = appChecked;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEntitle() {
        return entitle;
    }

    public void setEntitle(String entitle) {
        this.entitle = entitle;
    }

    public String getMaxlength() {
        return maxlength;
    }

    public void setMaxlength(String maxlength) {
        this.maxlength = maxlength;
    }
}
