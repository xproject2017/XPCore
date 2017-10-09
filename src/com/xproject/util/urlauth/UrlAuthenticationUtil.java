package com.xproject.util.urlauth;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 *Url鉴权
 *
 * @author  : 浙江云检科技有限公司 周祥兴
 * @Date    : 2016-07-13 上午9:25:32
 */
public class UrlAuthenticationUtil {
    protected static final char[] random_chars="1234567890*abcdefghijklmnopqrstuvwxyz$ABCDEFGHIJKLMNOPQRSTUVWXYZ!@#%^&(),.".toCharArray(); //随机字符串
    protected static final String plaintext="Study hard and make progress every day!";//加密明文
    private static final long expiry_seconds=3*1000l;  //url有效期3秒
    private final static String ENCODING = "UTF-8";
    private static final char[] base64EncodeChars = new char[]{
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '+', '/'};
    private static final byte[] base64DecodeChars = new byte[]{
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,
            52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,
            -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
            15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,
            -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
            41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1};



    public static void main(String[] args){
        UrlAuthInfo urlAuthInfo=new UrlAuthInfo(0);
        UrlAuthInfo urlAuthInfo1=decodeCiphertext(urlAuthInfo.getCiphertext());//解密

        try { Thread.sleep(1*1000); } catch (Exception e){  e.printStackTrace();  }
        System.out.println("校验信息：" + checkUrlAuthInfo(urlAuthInfo1)); //校验
        try { Thread.sleep(1*1000); } catch (Exception e){  e.printStackTrace();  }
        System.out.println("校验信息：" + checkUrlAuthInfo(urlAuthInfo1)); //校验
        try { Thread.sleep(1*1000); } catch (Exception e){  e.printStackTrace();  }
        System.out.println("校验信息：" + checkUrlAuthInfo(urlAuthInfo1)); //校验

        try { Thread.sleep(1*1000); } catch (Exception e){  e.printStackTrace();  }
        System.out.println("校验信息：" + checkUrlAuthInfo(urlAuthInfo1)); //校验

//        String s = plaintext+UrlAuthenticationUtil.getCurrentTimeMillis();
//        System.out.println("原始：" + s);
//        System.out.println("MD5后：" + string2MD5(s));
//        System.out.println("MD5后：" + encrypted4MD5(urlAuthInfo));
//        System.out.println("MD5后：" + encrypted4MD5(new UrlAuthInfo()));
//        //System.out.println("MD5后：" + encrypted4MD5(null));//空指针
//          String tt="1234567";
//        System.out.println(tt.substring(tt.length() - 1));
//        long st1=u1.getCurrentTimeMillis();
//        try {
//            Thread.sleep(1*1000);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        long st2=u1.getCurrentTimeMillis();
//        System.out.println( st1+","+st2);
//        System.out.println( st2-st1);
    }

    protected static long getCurrentTimeMillis(){
        return   System.currentTimeMillis();
    }

    /***
     * MD5加密算法生成32位md5码
     */
    protected static String string2MD5(String inStr){
        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++){
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }

    /**
     * 亦或加解密
     */
    protected static String _or_operation(String inStr,char or_char){

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++){
            a[i] = (char) (a[i] ^ or_char);
        }
        String s = new String(a);
        return s;

    }


    /***
     * MD5加密，生成密文 加密两次
     * fe3760b5bb62ada223aba9a53342c1e6
     */
    protected static String encrypted4MD5(UrlAuthInfo urlAuthInfo){
      return string2MD5(string2MD5(urlAuthInfo.getPlaintext()+urlAuthInfo.getTimestamp())+urlAuthInfo.getPlaintext()+urlAuthInfo.getTimestamp());
    }

    protected static String base64Encode4private(String str) throws UnsupportedEncodingException{
        byte[] data=str.getBytes(ENCODING);
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i = 0;
        int b1, b2, b3;
        while (i < len) {
            b1 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
                //sb.append("==");
                sb.append("!!");
                break;
            }
            b2 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
                //sb.append("==");
                sb.append("!!");
                break;
            }
            b3 = data[i++] & 0xff;
            sb.append(base64EncodeChars[b1 >>> 2]);
            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
            sb.append(base64EncodeChars[b3 & 0x3f]);
        }
        return sb.toString();
    }

    protected static String base64Decode4private(String str) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        byte[] data = str.getBytes(ENCODING);
        int len = data.length;
        int i = 0;
        int b1, b2, b3, b4;
        while (i < len) {

            do {
                b1 = base64DecodeChars[data[i++]];
            } while (i < len && b1 == -1);
            if (b1 == -1) break;

            do {
                b2 = base64DecodeChars
                        [data[i++]];
            } while (i < len && b2 == -1);
            if (b2 == -1) break;
            sb.append((char) ((b1 << 2) | ((b2 & 0x30) >>> 4)));

            do {
                b3 = data[i++];
                if (b3 == 61) return sb.toString();
                b3 = base64DecodeChars[b3];
            } while (i < len && b3 == -1);
            if (b3 == -1) break;
            sb.append((char) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));

            do {
                b4 = data[i++];
                if (b4 == 61) return sb.toString();
                b4 = base64DecodeChars[b4];
            } while (i < len && b4 == -1);
            if (b4 == -1) break;
            sb.append((char) (((b3 & 0x03) << 6) | b4));
        }
        return sb.toString();
    }

    /***
    * 加密生成密文信息
    */
    protected static String encodeCiphertext(UrlAuthInfo urlAuthInfo){
        //ciphertext=base64(random_char+base64((plaintext+timestamp+md5signature)^random_char))
        String  ciphertext=null;
        try {
            String instr=urlAuthInfo.getPlaintext()+"/"+urlAuthInfo.getTimestamp()+"/"+urlAuthInfo.getMd5signature();
            //System.out.println("instr=encodeCiphertext="+instr);

            ciphertext=base64Encode4private(urlAuthInfo.getRandom_char()+base64Encode4private(_or_operation(instr,urlAuthInfo.getRandom_char())));
        }catch (Exception e){
            e.printStackTrace();
        }
       // System.out.println("ciphertext=encodeCiphertext="+ciphertext);
        return ciphertext;
    }
    /***
    * 加密生成密文信息
    */
    public static UrlAuthInfo decodeCiphertext(String ciphertext){
        //ciphertext=base64(base64((plaintext+timestamp+md5signature)^random_char)+random_char)
        UrlAuthInfo  urlAuthInfo=new UrlAuthInfo();
        try {
            String instr=base64Decode4private(ciphertext);
            //System.out.println("instr=DecodeCiphertext="+instr);
            char random_char=instr.substring(0,1).charAt(0);
            //System.out.println("random_char=DecodeCiphertext="+random_char);
            instr=instr.substring(1);
            //System.out.println("instr=DecodeCiphertext="+instr);

            String ciphertext1=_or_operation(base64Decode4private(instr), random_char);
            //System.out.println("ciphertext1=DecodeCiphertext="+ciphertext1);
            String[] tmp=ciphertext1.split("/");

            urlAuthInfo.setRandom_char(random_char);
            urlAuthInfo.setPlaintext(tmp[0]);
            urlAuthInfo.setTimestamp(Long.valueOf(tmp[1]));
            urlAuthInfo.setMd5signature(tmp[2]);
        }catch (Exception e){
            e.printStackTrace();
        }
        return urlAuthInfo;
    }

    protected static boolean checkTimestamp(UrlAuthInfo urlAuthInfo){
        long ot=getCurrentTimeMillis()-urlAuthInfo.getTimestamp();
        //System.out.println("ot=checkTimestamp="+ot);
        if(ot>expiry_seconds){
            return  false;
        }
        return  true;
    }
    /***
     * 校验鉴权信息
     */
    public static boolean checkUrlAuthInfo(UrlAuthInfo urlAuthInfo){
        if(checkTimestamp(urlAuthInfo)){
             //校验md5信息
            String md5signature=encrypted4MD5(urlAuthInfo);
            if(!md5signature.equals(urlAuthInfo.getMd5signature())){
                return false;
            }
            if(!plaintext.equals(urlAuthInfo.getPlaintext())){
                return false;
            }

        }else {
            //超时
            return false;
        }
        return true;
    }
}
