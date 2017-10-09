package com.xproject.util.PropertyFileter;

import com.xproject.util.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by Administrator on 2017/1/20.
 */
public class JSONUtil {
    public static final String[]   netContentUnit={"","g","kg","mL","L"};
    public static final String[]   shelfLifeUnit={"","年","月","日"};
    public static final String[]   productCountUnit={"","瓶","盒","件","个","粒","升","台","桶"};
    public static final String[]   productWeightUnit={"","克","千克","毫升","升","夸脱"};
    public static final String[]   productValuetUnit={"","人民币","美元","欧元","日元","英镑","澳元","港币","新加坡元"};
    public static final String[]   productPackageUnit={"","木箱","纸箱","桶装","散装","托盘","包","其他"};
    //{'key':'brand','title':'品牌','type':'0','flag':'2','appChecked':'1','value':''}
    public static JSONObject GenDisplayInfo4Plamt(String key, String title,String type, String value, String flag) {
        JSONObject jo = new JSONObject();
        jo.put("key", key);
        jo.put("title", title);
        jo.put("type", type);
        jo.put("value", value);
        jo.put("flag", flag);
        return jo;
    }
    public static JSONObject GenDisplayInfo4Plamt(String key, String title,String type, String value, String flag,String url,String maxlength) {
        JSONObject jo = new JSONObject();
        jo.put("key", key);
        jo.put("title", title);
        jo.put("type", type);
        jo.put("value", value);
        jo.put("flag", flag);
        jo.put("url", url);
        jo.put("maxlength", maxlength);
        return jo;
    }

    public static JSONObject GenDisplayInfo(String key, String text, String value, String type) {
        JSONObject jo = new JSONObject();
        jo.put("key", key);
        jo.put("text", text);
        jo.put("value", value);
        jo.put("type", type);
        return jo;
    }

    public static JSONObject GenDisplayInfoColor(String key, String text, String value, String type, String color) {
        JSONObject jo = new JSONObject();
        jo.put("key", key);
        jo.put("text", text);
        jo.put("value", value);
        jo.put("type", type);
        jo.put("color", color);
        return jo;
    }

    public static JSONObject GenDisplayInfo(String key, String text, String value, String type, String url) {
        JSONObject jo = new JSONObject();
        jo.put("key", key);
        jo.put("text", text);
        jo.put("value", value);
        jo.put("type", type);
        if (url != null) {
            if (!"".equals(url.trim())) {
                jo.put("url", url);
            }
        }
        return jo;
    }

    public static JSONArray filterNoDisplayProp(List<Property> props) {
        JSONArray jsonProps = new JSONArray();
        if(props!=null) {
            for (Property e : props) {
                if(e.getFlag()!=null) {
                    if("1".equals(e.getFlag()) || "2".equals(e.getFlag())) {
                        jsonProps.add(JSONUtil.GenDisplayInfo4Plamt(e.getKey(), e.getTitle(), e.getType(), e.getValue(), e.getFlag(), e.getUrl(),e.getMaxlength()));
                    }
                }
            }
        }
        return jsonProps;
    }

    public static String bean2Json(Object obj) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter sw = new StringWriter();
        JsonGenerator gen = new JsonFactory().createJsonGenerator(sw);
        mapper.writeValue(gen, obj);
        gen.close();
        return sw.toString();
    }

    public static JSONArray map2json(Map map) {
        JSONArray array = new JSONArray();
        for (Object key:map.keySet()){
            JSONObject obj = new JSONObject();
            if (key instanceof Integer) {
                obj.put("value", (Integer)key);
            }else{
                obj.put("value", key.toString());
            }
            obj.put("label",map.get(key));
            array.add(obj);
        }
        return array;
    }

    public static <T> T json2Bean(String jsonStr, Class<T> objClass)
            throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonStr, objClass);
    }

    /**
     * 给模板附加属性填充对应值
     * @param template 模板的json串
     * @param propValue 值的json串
     * @return
     */
    public static List<ExtraPropertyBean> fillTemplate(String template, String propValue){
        List<ExtraPropertyBean> retAppChecked = new ArrayList<ExtraPropertyBean>();
        if (template == null || "".equals(template)||propValue==null||"".equals(propValue)){
            return retAppChecked;
        }
        JSONArray tjsonArray = JSONArray.fromObject("[" + template + "]");
        JSONArray pjsonArray = JSONArray.fromObject(propValue);
        List listExtra = JSONArray.toList((JSONArray) ((net.sf.json.JSONObject) tjsonArray.get(0)).get("extra"), new Property(), new JsonConfig());
        List listValue = JSONArray.toList(pjsonArray, new ExtraPropertyBean(), new JsonConfig());
        List<Property> extraProperty = new ArrayList<Property>();
        for (int i = 0;i < listExtra.size();i++) {
            Property p = (Property) listExtra.get(i);
            if ("1".equals(p.getAppChecked())){
                extraProperty.add(p);
            }
        }

        List<ExtraPropertyBean> extraPropertyValue = new ArrayList<ExtraPropertyBean>();
        for (int i = 0;i < listValue.size();i++) {
            extraPropertyValue.add((ExtraPropertyBean) listValue.get(i));
        }
        for (Property e : extraProperty){
            for (ExtraPropertyBean v : extraPropertyValue){
                if (e.getKey().equals(v.getKey())){
                    retAppChecked.add(v);
                }
            }
        }

        return retAppChecked;

    }

    public static List<ExtraPropertyBean> fillTemplate(String template,String propValue,String lang){
        List<ExtraPropertyBean> retAppChecked = new ArrayList<ExtraPropertyBean>();
        if (template == null || "".equals(template)||propValue==null||"".equals(propValue)){
            return retAppChecked;
        }
        JSONArray tjsonArray = JSONArray.fromObject("[" + template + "]");
        JSONArray pjsonArray = JSONArray.fromObject( propValue);
        List listExtra = JSONArray.toList((JSONArray) ((net.sf.json.JSONObject) tjsonArray.get(0)).get("extra"), new Property(), new JsonConfig());
        List listValue = JSONArray.toList(pjsonArray, new ExtraPropertyBean(), new JsonConfig());
        List<Property> extraProperty = new ArrayList<Property>();
        for (int i = 0;i < listExtra.size();i++) {
            Property p = (Property) listExtra.get(i);
            if ("1".equals(p.getAppChecked())){
                extraProperty.add(p);
            }
        }

        List<ExtraPropertyBean> extraPropertyValue = new ArrayList<ExtraPropertyBean>();
        for (int i = 0;i < listValue.size();i++) {
            try {
                ExtraPropertyBean p = (ExtraPropertyBean) listValue.get(i);
                if (p.getValue() != null && !p.getValue().equals("")) {
                    extraPropertyValue.add(p);
                }
            }catch (Exception e){

            }
        }
        for (Property e : extraProperty){
            for (ExtraPropertyBean v : extraPropertyValue){
                if (e.getKey().equals(v.getKey())){
                    if("en_US".equals(lang)){
                        v.setText(e.getEntitle());
                    }
                    retAppChecked.add(v);
                }
            }
        }

        return retAppChecked;

    }

    /**
     * 给模板类填充基本属性对应值
     * @param template 模板的json串
     * @param obj 具体实例对象，如商品备案需传入productinfo（已经有值的）
     * @return
     */
    public static List<ExtraPropertyBean> fillTemplate(String template,Object obj){
        JSONArray tjsonArray = JSONArray.fromObject("[" + template + "]");
        List listBasic = JSONArray.toList((JSONArray) ((net.sf.json.JSONObject) tjsonArray.get(0)).get("basic"), new Property(), new JsonConfig());
        List<Property> basicProperty = new ArrayList<Property>();
        Class clazz = obj.getClass();
        for (int i = 0;i < listBasic.size();i++) {
            basicProperty.add((Property) listBasic.get(i));
        }
        List<ExtraPropertyBean> basicProperties = new ArrayList<ExtraPropertyBean>();
        for (Property e : basicProperty){
            if (e != null && "1".equals(e.getAppChecked())) {
                ExtraPropertyBean extraProperty = new ExtraPropertyBean();
                extraProperty.setText(e.getTitle());
                processKey(extraProperty,e.getKey());
                try {
                    String key = e.getKey();
                    if (!"2".equals(e.getType()) && !"".equals(e.getType())&&!"8".equals(e.getType())) {
                        String className = "get" + key.replaceFirst(key.substring(0, 1), key.substring(0, 1).toUpperCase());
                        Method method = clazz.getMethod(className, null);
                        if ("0".equals(e.getType()) || "1".equals(e.getType())) {
                            extraProperty.setType("1");
                            String value = "";
                            try {
                                value = (String) method.invoke(obj, null);
                            }catch (ClassCastException ee){
                                value = ((Integer)method.invoke(obj,null)).toString();
                            }
                            value = filterColumn(clazz,className,value,key,obj);
                            extraProperty.setValue(value);

                        }else if ("3".equals(e.getType())) {
                            extraProperty.setType("1");
                            Date value = (Date) method.invoke(obj, null);
                            extraProperty.setValue(DateUtil.convertDate2String(value, DateUtil.DATE_YEAR));
                        } else if ("4".equals(e.getType())) {
                            extraProperty.setType("1");
                            Date value = (Date) method.invoke(obj, null);
                            extraProperty.setValue(DateUtil.convertDate2String(value, DateUtil.DATE_YEAR_MONTH));
                        } else if ("5".equals(e.getType())) {
                            extraProperty.setType("1");
                            Date value = (Date) method.invoke(obj, null);
                            extraProperty.setValue(DateUtil.convertDate2String(value, DateUtil.DEFAULT_DATE_FORMAT));

                        } else if ("9".equals(e.getType())){
                            extraProperty.setType("1");
                        }
                    }else if ("".equals(e.getType())){
                        extraProperty.setType("1");
                        if ("Urgent".equals(key)){
                            String className = "get" + key.replaceFirst(key.substring(0, 1), key.substring(0, 1).toUpperCase());
                            Method method = clazz.getMethod(className, null);
                            String value = "";
                            try {
                                value = (String) method.invoke(obj, null);
                            }catch (ClassCastException ee){
                                value = ((Integer)method.invoke(obj,null)).toString();
                            }
                            extraProperty.setValue(value);
                        }
                    }else if ("2".equals(e.getType())||"8".equals(e.getType())) {
                        extraProperty.setType("2");
                    }else {
                        extraProperty.setType("1");
                    }
                    if (extraProperty.getValue() == null){
                        extraProperty.setValue("");
                    }
                    basicProperties.add(extraProperty);
                } catch (NoSuchMethodException e1) {
                    e1.printStackTrace();
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }

            }
        }

        return basicProperties;

    }



    public static List<ExtraPropertyBean> fillTemplate(String template,Object obj,String lang){
        JSONArray tjsonArray = JSONArray.fromObject("[" + template + "]");
        List listBasic = JSONArray.toList((JSONArray) ((net.sf.json.JSONObject) tjsonArray.get(0)).get("basic"), new Property(), new JsonConfig());
        List<Property> basicProperty = new ArrayList<Property>();
        Class clazz = obj.getClass();
        for (int i = 0;i < listBasic.size();i++) {
            basicProperty.add((Property) listBasic.get(i));
        }
        List<ExtraPropertyBean> basicProperties = new ArrayList<ExtraPropertyBean>();
        for (Property e : basicProperty){
            if (e != null && "1".equals(e.getAppChecked())) {
                ExtraPropertyBean extraProperty = new ExtraPropertyBean();
                if("en_US".equals(lang)&&e.getEntitle()!=null&&!"".equals(e.getEntitle())){
                    extraProperty.setText(e.getEntitle());
                }else{
                    extraProperty.setText(e.getTitle());
                }
                processKey(extraProperty,e.getKey());
                try {
                    String key = e.getKey().toLowerCase();
                    if (!"2".equals(e.getType()) && !"".equals(e.getType())&&!"8".equals(e.getType())&&!"9".equals(e.getType())) {
                        String className = "get" + key.replaceFirst(key.substring(0, 1), key.substring(0, 1).toUpperCase());
                        Method method = clazz.getMethod(className, null);
                        if ("0".equals(e.getType()) || "1".equals(e.getType())|| "6".equals(e.getType())) {
                            extraProperty.setType("1");
                            String value = "";
                            try {
                                value = (String) method.invoke(obj, null);
                            }catch (ClassCastException ee){
                                try {
                                    value = ((Integer) method.invoke(obj, null)).toString();
                                }catch (ClassCastException eee){
                                    value = method.invoke(obj, null).toString();
                                }
                            }
                            value = filterColumn(clazz,className,value,key,obj);
                            extraProperty.setValue(value);

                        }else if ("3".equals(e.getType())) {
                            extraProperty.setType("1");
                            Date value = (Date) method.invoke(obj, null);
                            extraProperty.setValue(DateUtil.convertDate2String(value, DateUtil.DATE_YEAR));
                        } else if ("4".equals(e.getType())) {
                            extraProperty.setType("1");
                            Date value = (Date) method.invoke(obj, null);
                            extraProperty.setValue(DateUtil.convertDate2String(value, DateUtil.DATE_YEAR_MONTH));
                        } else if ("5".equals(e.getType())) {
                            extraProperty.setType("1");
                            Date value = (Date) method.invoke(obj, null);
                            extraProperty.setValue(DateUtil.convertDate2String(value, DateUtil.DEFAULT_DATE_FORMAT));

                        }
                    }else if ("".equals(e.getType())){
                        extraProperty.setType("1");
                        if ("Urgent".equals(key)){
                            String className = "get" + key.replaceFirst(key.substring(0, 1), key.substring(0, 1).toUpperCase());
                            Method method = clazz.getMethod(className, null);
                            String value = "";
                            try {
                                value = (String) method.invoke(obj, null);
                            }catch (ClassCastException ee){
                                value = ((Integer)method.invoke(obj,null)).toString();
                            }
                            extraProperty.setValue(value);
                        }
                    }else if ("2".equals(e.getType())||"8".equals(e.getType())) {
                        extraProperty.setType("2");
                    }else {
                        extraProperty.setType("1");
                    }
                    if (extraProperty.getValue() == null){
                        extraProperty.setValue("");
                    }
                    basicProperties.add(extraProperty);
                } catch (NoSuchMethodException e1) {
                    e1.printStackTrace();
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }

            }
        }

        return basicProperties;

    }

    public static List<ExtraPropertyBean> fillOtherTemplate(String template,Object obj,String lang){
        JSONArray tjsonArray = JSONArray.fromObject("[" + template + "]");
        List listBasic = JSONArray.toList((JSONArray) ((net.sf.json.JSONObject) tjsonArray.get(0)).get("basic"), new Property(), new JsonConfig());
        List<Property> basicProperty = new ArrayList<Property>();
        Class clazz = obj.getClass();
        for (int i = 0;i < listBasic.size();i++) {
            basicProperty.add((Property) listBasic.get(i));
        }
        List<ExtraPropertyBean> basicProperties = new ArrayList<ExtraPropertyBean>();
        for (Property e : basicProperty){
            if (e != null && "1".equals(e.getAppChecked())) {
                ExtraPropertyBean extraProperty = new ExtraPropertyBean();
                if("en_US".equals(lang)&&e.getEntitle()!=null&&!"".equals(e.getEntitle())){
                    extraProperty.setText(e.getEntitle());
                }else{
                    extraProperty.setText(e.getTitle());
                }
                processKey(extraProperty,e.getKey());
                try {
                    String key = e.getKey().toLowerCase();
                    if (!"2".equals(e.getType()) && !"".equals(e.getType())&&!"8".equals(e.getType())&&!"9".equals(e.getType())) {
                        String className = "get" + key.replaceFirst(key.substring(0, 1), key.substring(0, 1).toUpperCase());
                        Method method = clazz.getMethod(className, null);
                        if ("0".equals(e.getType()) || "1".equals(e.getType())|| "6".equals(e.getType())) {
                            extraProperty.setType("1");
                            String value = "";
                            try {
                                value = (String) method.invoke(obj, null);
                            }catch (ClassCastException ee){
                                try {
                                    value = ((Integer) method.invoke(obj, null)).toString();
                                }catch (ClassCastException eee){
                                    value = method.invoke(obj, null).toString();
                                }
                            }
                            value = filterColumn(clazz,className,value,key,obj);
                            extraProperty.setValue(value);

                        }else if ("3".equals(e.getType())) {
                            extraProperty.setType("1");
                            Date value = (Date) method.invoke(obj, null);
                            extraProperty.setValue(DateUtil.convertDate2String(value, DateUtil.DATE_YEAR));
                        } else if ("4".equals(e.getType())) {
                            extraProperty.setType("1");
                            Date value = (Date) method.invoke(obj, null);
                            extraProperty.setValue(DateUtil.convertDate2String(value, DateUtil.DATE_YEAR_MONTH));
                        } else if ("5".equals(e.getType())) {
                            extraProperty.setType("1");
                            Date value = (Date) method.invoke(obj, null);
                            extraProperty.setValue(DateUtil.convertDate2String(value, DateUtil.DEFAULT_DATE_FORMAT));

                        }
                    }else if ("".equals(e.getType())){
                        extraProperty.setType("1");
                        if ("Urgent".equals(key)){
                            String className = "get" + key.replaceFirst(key.substring(0, 1), key.substring(0, 1).toUpperCase());
                            Method method = clazz.getMethod(className, null);
                            String value = "";
                            try {
                                value = (String) method.invoke(obj, null);
                            }catch (ClassCastException ee){
                                value = ((Integer)method.invoke(obj,null)).toString();
                            }
                            extraProperty.setValue(value);
                        }else {
                            String className = "get" + key.replaceFirst(key.substring(0, 1), key.substring(0, 1).toUpperCase());
                            Method method = clazz.getMethod(className, null);
                            extraProperty.setType("1");
                            String value = "";
                            try {
                                value = (String) method.invoke(obj, null);
                            }catch (ClassCastException ee){
                                try {
                                    value = ((Integer) method.invoke(obj, null)).toString();
                                }catch (ClassCastException eee){
                                    value = method.invoke(obj, null).toString();
                                }
                            }
                            value = filterColumn(clazz,className,value,key,obj);
                            extraProperty.setValue(value);
                        }
                    }else if ("2".equals(e.getType())||"8".equals(e.getType())) {
                        extraProperty.setType("2");
                    }else {
                        extraProperty.setType("1");
                    }
                    if (extraProperty.getValue() == null){
                        extraProperty.setValue("");
                    }
                    if (e.getValue()!=null&&e.getValue().startsWith("0") && e.getKey().equals("Samplecount")){
                        continue;
                    }
                    basicProperties.add(extraProperty);
                } catch (NoSuchMethodException e1) {
                    e1.printStackTrace();
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }

            }
        }

        return basicProperties;

    }

    private static String filterColumn(Class clazz, String className,String value,String key,Object obj){
        String retValue = null;
        try {
            if (value != null && !value.equals("")) {
                if ("netcontent".equals(key)) {
                    String newclassName = className + "unit";
                    Method method = clazz.getMethod(newclassName, null);
                    Integer evalue = (Integer) method.invoke(obj, null);
                    retValue = value + "(" + netContentUnit[evalue] + ")";
                } else if ("shelflife".equals(key)) {
                    String newclassName = className + "unit";
                    Method method = clazz.getMethod(newclassName, null);
                    Integer evalue = (Integer) method.invoke(obj, null);
                    retValue = value + "(" + shelfLifeUnit[evalue] + ")";
                } else if ("productcount".equals(key)) {
                    String newclassName = className + "unit";
                    Method method = clazz.getMethod(newclassName, null);
                    String evalue = (String) method.invoke(obj, null);
                    retValue = value + "(" + evalue + ")";
                } else if ("productweight".equals(key)) {
                    String newclassName = className + "unit";
                    Method method = clazz.getMethod(newclassName, null);
                    Integer evalue = (Integer) method.invoke(obj, null);
                    retValue = value + "(" + productWeightUnit[evalue] + ")";
                } else if ("productvalue".equals(key)) {
                    String newclassName = className + "unit";
                    Method method = clazz.getMethod(newclassName, null);
                    Integer evalue = (Integer) method.invoke(obj, null);
                    retValue = value + "(" + productValuetUnit[evalue] + ")";
                } else if ("productpackage".equals(key)) {
                    String newclassName = className + "unit";
                    Method method = clazz.getMethod(newclassName, null);
                    Integer evalue = (Integer) method.invoke(obj, null);
                    retValue = value + "(" + productPackageUnit[evalue] + ")";
                } else {
                    retValue = value;
                }
            }
        }catch (Exception e){
            retValue = value;
        }
        return retValue;

    }

    private static void processKey(ExtraPropertyBean extra, String key){
        String text = key.substring(0,1).toUpperCase() + key.substring(1);
        if ("productPic".equals(key)){
            text = "LabelProof";
        }else if ("productSample".equals(key)){
            text = "CNProofSample";
        }else if ("shelfLife".equals(key)){
            text = "ShelfLife";
        }
        extra.setKey(text);
    }
}
