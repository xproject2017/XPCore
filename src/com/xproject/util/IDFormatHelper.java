package com.xproject.util;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * Created by SevilinMa on 2014/9/24.
 */
public class IDFormatHelper {
    private String LOGISTICS_ID = "L00000000";
    private String INSPECT_ID = "I00000000";
    private String TYPE_ID = "T00000000";
    private String BATCH_ID = "B00000000";
    private String APPLY_BATCH_ID = "A00000000";
    private String PRECHECK_ID = "P00000000";
    public static final String BATCH_PREFIX = "B";
    public static final String MATERIAL_BATCH_PREFIX = "M";
    public static final String CHECK_PREFIX = "C";
    public static final String THIRD_CHECK_PREFIX = "T";

    private DecimalFormat l = new DecimalFormat(LOGISTICS_ID);
    private DecimalFormat i = new DecimalFormat(INSPECT_ID);
    private DecimalFormat t = new DecimalFormat(TYPE_ID);
    private DecimalFormat b = new DecimalFormat(BATCH_ID);
    private DecimalFormat a = new DecimalFormat(APPLY_BATCH_ID);
    private DecimalFormat p = new DecimalFormat(PRECHECK_ID);

    public enum FormatType{
        LOGISTICS_ID,
        INSPECT_ID,
        TYPE_ID,
        BATCH_ID,
        PRECHECK_ID,
        APPLY_BATCH_ID
    }


    public String format(Long id,FormatType type){
        String strid = id.toString();
        switch (type){
            case LOGISTICS_ID:
                strid = l.format(id);
                break;
            case INSPECT_ID:
                strid = i.format(id);
                break;
            case TYPE_ID:
                strid = t.format(id);
                break;
            case BATCH_ID:
                strid = b.format(id);
                break;
            case PRECHECK_ID:
                strid = p.format(id);
                break;
            case APPLY_BATCH_ID:
                strid = a.format(id);
        }
        return strid;
    }

    //系统序号生成规则 例如批次序号B120800001
    public String format(Long id,Date createtime,String type ) {
        StringBuffer sb = new StringBuffer(type);
        sb.append(DateUtil.convertDate2String(createtime,"yyMM"));
        String idstr = id.toString();
        if(idstr.length()>5){
            sb.append(idstr.substring(idstr.length()-6,idstr.length()-1));
        }else{
            sb.append(StringUtil.fillString(idstr,5));
        }
        return sb.toString();
    }

}
