package com.xproject.util.PropertyFileter;

/**
 * Created by Administrator on 2017/1/19.
 */
public class ExtraPropertyBean {
    /*只给app使用，不能用于业务平台的筛选过滤*/
    private String text;
    private String value;
    private String type;
    private String key;
    private String url;

    public ExtraPropertyBean() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
