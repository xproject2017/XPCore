package com.xproject.util;

import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: SeleneFox
 * Date: 13-4-28
 * Time: 下午4:11
 * To change this template use File | Settings | File Templates.
 */
public class DateUtil {
    private static Logger logger = Logger.getLogger(DateUtil.class);
    /**
     * 默认的日期格式
     */
    public static String DEFAULT_DATE_FORMAT = "yyyy/MM/dd";
    public static String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    public static String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
    public static String DATE_FORMAT1 = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_FORMAT1_1 = "yyyy-MM-dd";
    public static String DATE_FORMAT1_2 = "yyyy.MM.dd";
    public static String DATE_YEAR_MONTH = "yyyy-MM";
    public static String DATE_MINUTES_FORMAT = "yyyy/MM/dd HH:mm";
    public static String DATE_YEAR= "yyyy";
    public static String DATE_MONTH= "MM";
    public static String DATE_FORMAT_1= "yyyyMMddHHmmss";
    public static String DATE_FORMAT_2= "yyyyMMdd";
    public static String DATE_FORMAT_3= "yyyyMM";
    public static String DATE_FORMAT_4= "yyyy年MM月";
    public static String DATE_FORMAT_5= "yyyy年MM月dd日";
    // 保存每个月的天数
    private static final int[] DAYS_OF_MONTH = { 31, 28, 31, 30, 31, 30, 31,
            31, 30, 31, 30, 31 };

    // 日历的起始年份
    public static final int START_YEAR = 1900;

    // 日历的结束年份
    public static final int END_YEAR = 2100;

    /**
     * 取得当前日期
     *
     * @return Date 当前日期
     */
    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 返回当前日期对应的默认格式的字符串
     *
     * @return String 当前日期对应的字符串
     */
    public static String getCurrentStringDate() {
        return convertDate2String(getCurrentDate(), DEFAULT_DATE_FORMAT);
    }

    /**
     * 返回当前日期对应的指定格式的字符串
     *
     * @param dateFormat - 日期格式
     * @return String 当前日期对应的字符串`
     */
    public static String getCurrentStringDate(String dateFormat) {
        return convertDate2String(getCurrentDate(), dateFormat);
    }

    /**
     * 将日期转换成指定格式的字符串
     *
     * @param date       - 要转换的日期
     * @param dateFormat - 日期格式
     * @return String 日期对应的字符串
     */
    public static String convertDate2String(Date date, String dateFormat) {
        SimpleDateFormat sdf;
        if (dateFormat != null && !dateFormat.equals("")) {
            try {
                sdf = new SimpleDateFormat(dateFormat);
            } catch (Exception e) {
                logger.error(e);
                sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            }
        } else {
            sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        }
        if (date != null) {
            return sdf.format(date);
        }
        return null;
    }

    /**
     * 从java.sql.Date 转换到 java.util.Date
     *
     * @param date java.sql.Date类型的时间对象
     * @return java.util.Date类型的时间对象
     */
    public static Date converDate2JavaDate(java.sql.Date date) {
        if (date != null) {
            return new Date(date.getTime());
        }
        return null;
    }

    /**
     * 从java.util.Date转换到java.sql.Date
     *
     * @param date java.util.Date类型的时间对象
     * @return java.sql.Date类型的时间对象
     */
    public static java.sql.Date converDate2SqlDate(Date date) {
        if (date != null) {
            return new java.sql.Date(date.getTime());
        }
        return null;
    }

    /**
     * 将字符串转换成日期
     *
     * @param stringDate - 要转换的字符串格式的日期
     * @return Date 字符串对应的日期
     */
    public static Date convertString2Date(String stringDate) {
        return convertString2Date(stringDate, DEFAULT_DATE_FORMAT);
    }

    /**
     * 将字符串转换成日期
     *
     * @param stringDate - 要转换的字符串格式的日期
     * @param dateFormat - 要转换的字符串对应的日期格式
     * @return Date 字符串对应的日期
     */
    public static Date convertString2Date(String stringDate, String dateFormat) {
        SimpleDateFormat sdf;
        if (dateFormat != null && !dateFormat.equals("")) {
            try {
                sdf = new SimpleDateFormat(dateFormat);
            } catch (Exception e) {
                logger.error(e);
                sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            }
        } else {
            sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        }
        try {
            return sdf.parse(stringDate);
        } catch (ParseException pe) {
            return new Date();
        }
    }

    /**
     * 将一种格式的日期字符串转换成默认格式的日期字符串
     *
     * @param oldStringDate - 要格式化的日期字符串
     * @param oldFormat     - 要格式化的日期的格式
     * @return String 格式化后的日期字符串
     */
    public static String formatStringDate(String oldStringDate, String oldFormat) {
        return convertDate2String(convertString2Date(oldStringDate, oldFormat), DEFAULT_DATE_FORMAT);
    }

    /**
     * 将一种格式的日期字符串转换成另一种格式的日期字符串
     *
     * @param oldStringDate - 要格式化的日期字符串
     * @param oldFormat     - 要格式化的日期的格式
     * @param newFormat     - 格式化后的日期的格式
     * @return String 格式化后的日期字符串
     */
    public static String formatStringDate(String oldStringDate, String oldFormat, String newFormat) {
        return convertDate2String(convertString2Date(oldStringDate, oldFormat), newFormat);
    }


    public static int getDaysByMonth(int year, int month) {
        Calendar c = new GregorianCalendar(year, month, 1);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getNowyear() {
        return Integer.valueOf(convertDate2String(getCurrentDate(), "yyyy"));
    }

    public static int compDate(Date date1, Date date2) {
        Calendar c = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c.clear();
        c.setTime(date1);
        c2.clear();
        c2.setTime(date2);
        return c.compareTo(c2);
    }

    /**
     * 得到 T 天 前的日期
     *
     * @param t 天数
     * @return 日期
     */
    public String getDateString(int t) {
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, -1 * t);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        return df.format(monday) + " 00:00:00";
    }

    public static String getFormatDate(String time){
        if(null!=time && !"".equals(time)){
            return time.substring(0,time.lastIndexOf("."));
        }
        return "";
    }

    public static Date getDateByOneDay(Date date){
        String newdate = convertDate2String(date,"yyyy-MM")+"-01 00:00:00";
        return convertString2Date(newdate,DATE_FORMAT);
    }

    /**
     *
     * @param date
     * @param days
     * @return
     */
    public static Date addDate(Date date,int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_WEEK,days);
        return calendar.getTime();
    }

    /**
     *
     * @param date
     * @param month
     * @return
     */
    public static Date addMonth(Date date,int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendar.MONTH,month);
        return calendar.getTime();
    }

    /**
     * 计算两个日期之间相差的天数
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws java.text.ParseException
     */
    public static int daysBetween(Date smdate,Date bdate,String format) {
        long between_days = 0;
        try {
            if (format == null){
                format = DateUtil.DATE_FORMAT;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            long time2 = cal.getTimeInMillis();
            between_days = (time2 - time1) / (1000 * 3600 * 24);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 计算两个日期之间相差的月份
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差月份
     * @throws java.text.ParseException
     */
    public static int monthsBetween(Date smdate,Date bdate,String format) {
        int between = 0,month=0;
        try {
            if (format == null){
                format = DateUtil.DATE_FORMAT;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            int time1 = cal.get(Calendar.MONTH);
            int year1 = cal.get(Calendar.YEAR);
            cal.setTime(bdate);
            int time2 = cal.get(Calendar.MONTH);
            int year2 = cal.get(Calendar.YEAR);
            between = time2 - time1;
            month =  (year2 - year1) * 12;
        }catch (Exception e){
            e.printStackTrace();
        }
        return Math.abs(month + between);
    }

    /***************************************************************************
     * 匹配日期格式 yyyyMMdd 并验证日期是否合法
     *
     * @param date 日期字符串
     * @return true 日期合法 false 日期非法
     */
    public static boolean isValidDate(String date){
        // 1、 \\d{4} 年，\\d{2}月，\\d{2}日匹配
        Pattern p = Pattern.compile("^\\d{4}\\d{2}\\d{2}$");
        Matcher match = p.matcher(date);
        if (date != null){
            if (match.matches()){
                int year = Integer.parseInt(date.substring(0, 4)); // 年
                int month = Integer.parseInt(date.substring(4, 6)); // 月
                int day = Integer.parseInt(date.substring(6, 8)); // 日
                if (!isValidYear((year))){
                    return false; // 年份不在有效年份中
                }
                if (!isValidMonth(month)){
                    return false; // 月份不合法
                }
                if (!isValidDay(year, month, day)){
                    return false; // 日期不合法
                }
                return true;
            }

            return false;
        }
        else{
            return false;
        }
        // return Pattern.matches("", date);
    }

    /**
     * 检查year是否在有效的年份范围内 此处验证大于1900年 小于2101年
     *
     * @param year
     * @return
     */
    public static boolean isValidYear(int year){
        return year >= START_YEAR && year <= END_YEAR;
    }

    /**
     * 验证月份是否在有效月份内
     *
     * @param month
     * @return
     */
    public static boolean isValidMonth(int month){
        return month >= 1 && month <= 12;
    }

    /**
     * 检查天数是否在有效的范围内，因为天数会根据年份和月份的不同而不同 所以必须依赖年份和月份进行检查
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static boolean isValidDay(int year, int month, int day){
        if (month == 2 && isLeapYear(year)){
            return day >= 1 && day <= 29;
        }
        return day >= 1 && day <= DAYS_OF_MONTH[month - 1];// 其他月份
    }

    /**
     * 验证是否是闰年
     *
     * @param year
     * @return
     */
    public static boolean isLeapYear(int year){
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    public static List<Integer> gen10year(){
        List<Integer> years = new ArrayList<Integer>();
        Calendar c = Calendar.getInstance();
        Integer year = c.get(Calendar.YEAR);
        for(int i=10;i>0;i--){
            int temp = year-i;
            years.add(temp);
        }

        for(int i=0;i<10;i++){
            int temp = year+i;
            years.add(temp);
        }
        return years;
    }

    //获取一个月最后一天
    public static String getLastDayOfMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH,1);
        cal.add(Calendar.DAY_OF_MONTH,-1);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT1_1);
        String lastDayOfMonth = sdf.format(cal.getTime());

        return lastDayOfMonth ;
    }
}
