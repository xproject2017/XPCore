package com.xproject.util.PropertyFileter;


import com.xproject.util.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/9/9.
 */
public class ToolUtil {

    public static String formatDecimal(String data,int decimal) {
        int index = data.indexOf(".");
        return data.substring(0,index+decimal);
    }

    /**
     * 给模板类填充对应值
     * @param template 模板的json串
     * @param propValue 值的json串
     * @return
     */
    public static List<Property> fillTemplate(String template,String propValue){
        template=template.toLowerCase();
        if (propValue==null)return null;
        JSONArray tjsonArray = JSONArray.fromObject("[" + template + "]");
        JSONArray pjsonArray = JSONArray.fromObject(propValue);
        List listExtra = JSONArray.toList((JSONArray) ((net.sf.json.JSONObject) tjsonArray.get(0)).get("extra"), new Property(), new JsonConfig());
        List listValue = JSONArray.toList(pjsonArray, new Property(), new JsonConfig());
        List<Property> extraProperty = new ArrayList();
        for (int i = 0;i < listExtra.size();i++) {
            Property p = (Property) listExtra.get(i);
            if (!"0".equals(p.getFlag())){
                extraProperty.add(p);
            }
        }
        List<Property> extraPropertyValue = new ArrayList();
        for (int i = 0;i < listValue.size();i++) {
            extraPropertyValue.add((Property) listValue.get(i));
        }
        for (Property e : extraProperty){
            for (Property v : extraPropertyValue){
                if (e.getKey().equals(v.getKey())){
                    e.setValue(v.getValue());
                    e.setUrl(v.getUrl());
                }
            }
        }

        return extraProperty;

    }

    /**
     * 给模板类填充基本属性对应值
     * @param template 模板的json串
     * @param obj 具体实例对象，如商品备案需传入productinfo（已经有值的）
     * @return
     */
    public static List<Property> fillTemplate(String template,Object obj){
        String  template_new=template.toLowerCase();
        JSONArray tjsonArray_old = JSONArray.fromObject("[" + template + "]");
        List listBasic_old = JSONArray.toList((JSONArray) ((net.sf.json.JSONObject) tjsonArray_old.get(0)).get("basic"), new Property(), new JsonConfig());
        JSONArray tjsonArray = JSONArray.fromObject("[" + template_new + "]");
        List listBasic = JSONArray.toList((JSONArray) ((net.sf.json.JSONObject) tjsonArray.get(0)).get("basic"), new Property(), new JsonConfig());

        List<Property> basicProperty = new ArrayList<Property>();
        Class clazz = obj.getClass();
        for (int i = 0;i < listBasic.size();i++) {
            basicProperty.add((Property) listBasic.get(i));
        }
        for (Property e : basicProperty){
            String key = e.getKey();
            //过滤掉不需要的属性
            if (!e.getKey().equals("manyreport") && !e.getKey().equals("inspectcode") && !e.getKey().equals("importgate") && !e.getKey().equals("urgent") && !e.getKey().equals("statusdesc")) {
                String className = "get" + key.replaceFirst(key.substring(0, 1), key.substring(0, 1).toUpperCase());
                try {
                    Method method = clazz.getMethod(className, null);
                    if (className.equals("getProductshipdate") || className.equals("getIntodate")) {
                        method = clazz.getMethod(className + "str", null);
                    }
                    String value = "";
                    try {
                        value = method.invoke(obj, null).toString();
                    } catch (ClassCastException ee) {
                        value = method.invoke(obj, null).toString();
                    } catch (NullPointerException e1){
                        // e1.printStackTrace();
                    }
                    if (value != null) {
                        e.setValue(value.replaceAll("\\r\\n", "</br>"));
                    }

                } catch (NoSuchMethodException e1) {
                   // e1.printStackTrace();
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }

        //
        Property tmp=null;
        if(basicProperty!=null) {
            for (Property e : basicProperty) {
                if(e.getKey()!=null){
                    for (int i = 0;i < listBasic_old.size();i++) {
                        tmp=(Property) listBasic_old.get(i);
                        if(tmp.getKey()!=null) {
                            if (e.getKey().equals(tmp.getKey().toLowerCase())) {
                                e.setKey(tmp.getKey());
                            }
                        }
                    }
                }
            }
        }

        return basicProperty;

    }

    public static List<Property> filterBasic(String template){
        JSONArray tjsonArray = JSONArray.fromObject("[" + template + "]");
        List listExtra = JSONArray.toList((JSONArray) ((net.sf.json.JSONObject) tjsonArray.get(0)).get("basic"), new Property(), new JsonConfig());
        List<Property> property = new ArrayList<Property>();
        for (int i = 0;i < listExtra.size();i++) {
            property.add((Property) listExtra.get(i));
        }
        return property;
    }

    public static List<Property> filterExtra(String template){
        JSONArray tjsonArray = JSONArray.fromObject("[" + template + "]");
        List listExtra = JSONArray.toList((JSONArray) ((net.sf.json.JSONObject) tjsonArray.get(0)).get("extra"), new Property(), new JsonConfig());
        List<Property> property = new ArrayList<Property>();
        for (int i = 0;i < listExtra.size();i++) {
            property.add((Property) listExtra.get(i));
        }
        return property;
    }

    public static String getDateFormat(String template,String _key){
        if(null==template || "".equals(template)){
            return  DateUtil.DATE_FORMAT1_1;
        }
        JSONArray tjsonArray = JSONArray.fromObject("[" + template + "]");
        List listExtra = JSONArray.toList((JSONArray) ((net.sf.json.JSONObject) tjsonArray.get(0)).get("basic"), new Property(), new JsonConfig());

        for (int i = 0;i < listExtra.size();i++) {
            Property p = ((Property) listExtra.get(i));
            if(p.getKey().equals(_key)){
                if(p.getType().equals("3")){
                    return DateUtil.DATE_YEAR;
                }else if(p.getType().equals("4")){
                    return DateUtil.DATE_YEAR_MONTH;
                }else if(p.getType().equals("5")){
                    return DateUtil.DATE_FORMAT1_1;
                }
            }
        }
        return DateUtil.DATE_FORMAT1_1;
    }



    /**
     * 解析备案product的extJson
     * @param extJson
     * @return
     */
    public static List<Property> fillOtherProp(String extJson){
        JSONArray tjsonArray;
        if (extJson.startsWith("[")){
            tjsonArray = JSONArray.fromObject(extJson);
        }else {
            tjsonArray = JSONArray.fromObject("[" + extJson + "]");
        }

        List<Property> retList = JSONArray.toList(tjsonArray,new Property(),new JsonConfig());
        return retList;
    }

    public static String getFormatData(String ship){
        if(ship!=null && !ship.equals("")){
            int size = ship.split("-").length;
            if(size==1){
                ship = ship +"-01-01";
            }else if(size==2){
                ship = ship +"-01";
            }
        }
        return ship;
    }

    public static Map<String,Integer> keyTransferValue(Map<Integer,String> map){
        Map<String,Integer> retmap = new HashMap<>();
        for (Integer key:map.keySet()){
            retmap.put(map.get(key),key);
        }
        return retmap;
    }
}
