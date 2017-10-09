package com.xproject.util.sys;


/**
 * Created by Administrator on 2017/1/16.
 */
public class TokenUtil {
    //数据库中的格式：token
//session中的格式：token流水号|token|局方id|USERTYPE|用户id|clientType
//token结构：SESSIONID|SESSIONTOKEN|PID|USERTYPE|Userid!clientType
//        SESSIONID：主键
//        SESSIONTOKEN：32位不重复串
//        PID：省局id （0--255）  默认(-1)
//        USERTYPE：用户类型    默认(-1)
//        Userid：用户id（0--31）    默认(-1)
//        clientType:请求来源：1：业务层，2：移动端，3：工控端，4：对外接口，如优特网
    public static final  String sp="]";

 /*
 * @author 周祥兴
 * @return long
 * @date 2016年03月03日
*/
    public static long getSessionidByToken(String token) {
        String[] tokenArray = token.split(sp);//已经通过token校验，所以后面的解析都不再校验token格式
        return Long.valueOf(tokenArray[0]);
    }
    /*
* @author 周祥兴
* @return String
* @date 2016年03月03日
*/
    public static String getDbtokenByToken(String token) {
        String[] tokenArray = token.split(sp);//已经通过token校验，所以后面的解析都不再校验token格式
        return tokenArray[1];
    }
    /*
* @author 周祥兴
* @return int
* @date 2016年03月03日
*/
    public static int getPidByToken(String token) {
        String[] tokenArray = token.split(sp);//已经通过token校验，所以后面的解析都不再校验token格式
        return Integer.valueOf(tokenArray[2]);
    }
    /*
* @author 周祥兴
* @return int
* @date 2016年03月03日
*/
    public static int getUsertypeByToken(String token) {
        String[] tokenArray = token.split(sp);//已经通过token校验，所以后面的解析都不再校验token格式
        return Integer.valueOf(tokenArray[3]);
    }

    /*
* @author 周祥兴
* @return int
* @date 2016年03月03日
*/
    public static int getUseridByToken(String token) {
        String[] tokenArray = token.split(sp);//已经通过token校验，所以后面的解析都不再校验token格式
        return Integer.valueOf(tokenArray[4]);
    }

    /*
* @author 周祥兴
* @return int
* @date 2016年03月03日
*/
    public static int getClientflagByToken(String token) {
        String[] tokenArray = token.split(sp);//已经通过token校验，所以后面的解析都不再校验token格式
        return Integer.valueOf(tokenArray[5]);
    }

    /*
* @author 周祥兴
* @return boolen
* @date 2016年03月03日
*/
    public static boolean checkTokenFormat(String token) {
        String[] tokenArray = token.split(sp);//已经通过token校验，所以后面的解析都不再校验token格式
        if (tokenArray.length != 6) {
            //无效的token
            return false;
        }

        int usertype=getUsertypeByToken(token);
        if(usertype<Constants.USER_TYPE_GOVERNMENT || usertype>Constants.USER_TYPE_BUSINESSSUPPORT){
            //无效的token
            return false;
        }
        int clientflag=getClientflagByToken(token);
        if(clientflag<1 || clientflag>4){
            //无效的token
            return false;
        }

        return true;
    }

}
