package com.xproject.util.urlauth;

import java.util.Random;

/**
 *Url鉴权
 *
 * @author  : 浙江云检科技有限公司 周祥兴
 * @Date    : 2016-07-13 上午9:25:32
 */
public class UrlAuthInfo {
    char random_char;
    String plaintext;
    long timestamp;
    String md5signature; //md5signature=md5(md5(plaintext+timestamp)+plaintext+timestamp)
    String ciphertext; //ciphertext=base64(random_char+base64((plaintext+timestamp+md5signature)^random_char))

    public  UrlAuthInfo(){
    }
    public  UrlAuthInfo(int init){
        Random rand = new Random();
        int randNum_idex = rand.nextInt(UrlAuthenticationUtil.random_chars.length+1);
        this.setRandom_char(UrlAuthenticationUtil.random_chars[randNum_idex]); //取随机字符
        this.setTimestamp(UrlAuthenticationUtil.getCurrentTimeMillis());//取当前时间戳
        this.setPlaintext(UrlAuthenticationUtil.plaintext); //明文赋值
        this.setMd5signature(UrlAuthenticationUtil.encrypted4MD5(this));//生成MD5签名信息
        this.setCiphertext(UrlAuthenticationUtil.encodeCiphertext(this));//生成加密信息

        //System.out.println("random_char="+this.getRandom_char()+",plaintext="+this.getPlaintext()+",timestamp="+this.getTimestamp()+",md5signature="+this.getMd5signature()+",ciphertext="+this.getCiphertext());
    }
    public char getRandom_char() {
        return random_char;
    }
    public void setRandom_char(char random_char) {
        this.random_char = random_char;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPlaintext() {
        return plaintext;
    }
    public void setPlaintext(String plaintext) {
        this.plaintext = plaintext;
    }
    public String getMd5signature() {
        return md5signature;
    }
    public void setMd5signature(String md5signature) {
        this.md5signature = md5signature;
    }

    public String getCiphertext() {
        return ciphertext;
    }

    public void setCiphertext(String ciphertext) {
        this.ciphertext = ciphertext;
    }
}
