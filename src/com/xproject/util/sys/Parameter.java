package com.xproject.util.sys;

import java.util.Date;

/**
 * Created by Administrator on 2016/3/10.
 */
public class Parameter {
    public static final Integer IntegerType=1;
    public static final Integer LongType=2;
    public static final Integer StringType=3;
    public static final Integer DoubleType=4;
    public static final Integer DateType=5;
    public static final Integer BooleanType=6;


    private Integer IntegerValue;
    private Long LongValue;
    private String StringValue;
    private Double DoubleValue;
    private Date DateValue;
    private Boolean BooleanValue;


    public Integer getIntegerValue() {
        return IntegerValue;
    }

    public void setIntegerValue(Integer integerValue) {
        IntegerValue = integerValue;
    }

    public Long getLongValue() {
        return LongValue;
    }

    public void setLongValue(Long longValue) {
        LongValue = longValue;
    }

    public String getStringValue() {
        return StringValue;
    }

    public void setStringValue(String stringValue) {
        StringValue = stringValue;
    }

    public Double getDoubleValue() {
        return DoubleValue;
    }

    public void setDoubleValue(Double doubleValue) {
        DoubleValue = doubleValue;
    }

    public Date getDateValue() {
        return DateValue;
    }

    public void setDateValue(Date dateValue) {
        DateValue = dateValue;
    }

    public Boolean getBooleanValue() {
        return BooleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        BooleanValue = booleanValue;
    }

}
