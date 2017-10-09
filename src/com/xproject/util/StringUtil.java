package com.xproject.util;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类
 *
 * @author 周祥兴
 * @date 2016年03月03日
 */
public class StringUtil {

    public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String numberChar = "0123456789";

    /*-----------------------------------
  笨方法：String s = "你要去除的字符串";
          1.去除空格：s = s.replace('\\s','');
          2.去除回车：s = s.replace('\n','');
  这样也可以把空格和回车去掉，其他也可以照这样做。
  注：\n 回车(\u000a)
  \t 水平制表符(\u0009)
  \s 空格(\u0008)
  \r 换行(\u000d)
     * @author 周祥兴
     * @return String
     * @date 2016年03月03日
  */
    public static String replaceBlank(String str) {
        String dest = null;
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }


    /**
     * 返回一个定长的随机字符串(只包含大小写字母、数字)
     *
     * @param length
     *            随机字符串长度
     * @return 随机字符串
     */
    public static String generateString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allChar.charAt(random.nextInt(allChar.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机纯字母字符串(只包含大小写字母)
     *
     * @param length
     *            随机字符串长度
     * @return 随机字符串
     */
    public static String generateMixString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allChar.charAt(random.nextInt(letterChar.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机纯大写字母字符串(只包含大小写字母)
     *
     * @param length
     *            随机字符串长度
     * @return 随机字符串
     */
    public static String generateLowerString(int length) {
        return generateMixString(length).toLowerCase();
    }

    /**
     * 返回一个定长的随机纯小写字母字符串(只包含大小写字母)
     *
     * @param length
     *            随机字符串长度
     * @return 随机字符串
     */
    public static String generateUpperString(int length) {
        return generateMixString(length).toUpperCase();
    }

    /**
     * 生成一个定长的纯0字符串
     *
     * @param length
     *            字符串长度
     * @return 纯0字符串
     */
    public static String generateZeroString(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append('0');
        }
        return sb.toString();
    }

    /**
     * 根据数字生成一个定长的字符串，长度不够前面补0
     *
     * @param num
     *            数字
     * @param fixdlenth
     *            字符串长度
     * @return 定长的字符串
     */
    public static String toFixdLengthString(long num, int fixdlenth) {
        StringBuffer sb = new StringBuffer();
        String strNum = String.valueOf(num);
        if (fixdlenth - strNum.length() >= 0) {
            sb.append(generateZeroString(fixdlenth - strNum.length()));
        } else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth
                    + "的字符串发生异常！");
        }
        sb.append(strNum);
        return sb.toString();
    }

    /**
     * 根据数字生成一个定长的字符串，长度不够前面补0
     *
     * @param num
     *            数字
     * @param fixdlenth
     *            字符串长度
     * @return 定长的字符串
     */
    public static String toFixdLengthString(int num, int fixdlenth) {
        StringBuffer sb = new StringBuffer();
        String strNum = String.valueOf(num);
        if (fixdlenth - strNum.length() >= 0) {
            sb.append(generateZeroString(fixdlenth - strNum.length()));
        } else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth
                    + "的字符串发生异常！");
        }
        sb.append(strNum);
        return sb.toString();
    }


    public static Map<String, String[]> getAddstr(String addstr) {
        Map<String, String[]> cityStr = new HashMap<String, String[]>();
        try {
            String str = new String(addstr.getBytes("ISO-8859-1"), "utf-8");
            String st[] = str.split(",");
            cityStr.put("city", st);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return cityStr;
    }

    //投诉举报中生成编号，网络投诉a+8位数字，电话投诉b+8位数字
    //type:1网络 2电话 例：电话投诉输入id为1，方法返回b00000001
    public static String generateCompNo(Integer id,Integer type) {
        if (id == null) return null;
        String sb = id.toString();
        int len = id.toString().length();
        if (len<8) {
            for (int i = 8 - len; i > 0; i--) {
                sb = "0" + sb;
            }
        }
        if (type == 1){
            sb = "a" + sb;
        }else {
            sb = "b" + sb;
        }

        return sb;
    }

    public static String generateItemNo(Integer id) {
        if (id == null) return null;
        String sb = id.toString();
        int len = id.toString().length();
        if (len<5) {
            for (int i = 5 - len; i > 0; i--) {
                sb = "0" + sb;
            }
        }

        return sb;
    }

    public static String fillString(String data,int len){
        if (data.length()>len){
            return data;
        }else {
            for (int i=len - data.length();i>0;i--){
                data = "0"+data;
            }
            return data;
        }
    }

    public static String byteToHexString(byte[] bytes) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {                                    //转化为16进制字符串
            byte b1 = (byte) ((bytes[i] & 0xf0) >> 4);
            byte b2 = (byte) (bytes[i] & 0x0f);
            if (b1 < 10)
                result.append((char) ('0' + b1));
            else
                result.append((char) ('A' + (b1 - 10)));
            if (b2 < 10)
                result.append((char) ('0' + b2));
            else
                result.append((char) ('A' + (b2 - 10)));
        }
        return result.toString();
    }

    public static String generateSessionId(Long userId) {

        Random random = new SecureRandom();
        byte randomBytes[] = new byte[8];
        random.nextBytes(randomBytes);

        Date dateNow = new Date(System.currentTimeMillis());
        String token = byteToHexString(randomBytes) + Long.toHexString(userId | 0xF000000000000000L).substring(12, 16)
                + Long.toHexString(dateNow.getTime() | 0xF000000000000000L).substring(4, 16);
        return token.toUpperCase();
    }

    //获取url中的hostname  chenlm added
    public static String getURLHostname(String url){
        if (url!=null||"".equals(url)){
            int index1 = url.indexOf("://");
            String temp = url.substring(index1+3);
            int index2 = temp.indexOf("/");
            String hostname = temp.substring(0,index2);
            return hostname;
        }
        return null;
    }

    //将批次序号B000000001，B000000002转换成1,2
    public static String retNoBstring(String str) {
        String[] batchidstrs = str.split(",");
        StringBuffer newbatchidstrs = new StringBuffer();
        for (String batchidstr : batchidstrs) {
            Integer batchid = Integer.parseInt(batchidstr.substring(5));
            newbatchidstrs.append(batchid).append(",");
        }
        return newbatchidstrs.toString();
    }
}
