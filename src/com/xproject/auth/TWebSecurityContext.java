package com.xproject.auth;

import com.xproject.bean.dao.system.TBaseConfig;
import com.xproject.bean.dao.system.TSysSession;
import com.xproject.manager.ManagerException;
import com.xproject.manager.controller.system.UserServer;
import com.xproject.manager.tx.system.TBaseConfigManager;
import com.xproject.util.DataSourceContextHolder;
import com.xproject.util.ServeletIPUtil;
import com.xproject.util.sys.Constants;
import com.xproject.util.sys.ResultInfo4Contorl;
import com.xproject.util.sys.TokenUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * 拦截器
 */
@SuppressWarnings("ALL")
public class TWebSecurityContext  extends HandlerInterceptorAdapter {
    private static Logger logger = Logger.getLogger(TWebSecurityContext.class);

    @Autowired
    public UserServer userServer;
    @Autowired
    private TBaseConfigManager tBaseConfigManager;

    @Value("#{baseinfoMap}")
    private HashMap<String, String> baseinfoMap;

    private static final String aurl="#/v2/#/tip/#";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            String uri = request.getRequestURI();//返回请求行中的资源名称
            String url = request.getRequestURL().toString();//获得客户端发送请求的完整url
            String ip = request.getRemoteAddr();//返回发出请求的IP地址
            String params = request.getQueryString();//返回请求行中的参数部分
            String host = request.getRemoteHost();//返回发出请求的客户机的主机名
            int port = request.getRemotePort();//返回发出请求的客户机的端口号。


//        System.out.println(ip);
//        System.out.println(url);
//        System.out.println(uri);
//        System.out.println(params);
//        System.out.println(host);
//        System.out.println(port);

            ip = ServeletIPUtil.getIpAddress(request);
            //System.out.println("ip==" + ip);
            ip = ServeletIPUtil.getIpAddress4Proxy(request);
            //System.out.println("ip==" + ip);
            ip = ServeletIPUtil.getLocalIp(request);
            //System.out.println("ip==" + ip);
            ip = ServeletIPUtil.getIp(request);
            //System.out.println("ip==" + ip);


            url = request.getServletPath();     //request.getContextPath()
            String path = request.getPathInfo();
            String token = request.getHeader(Constants.CATOKEN_Attribute);
            if (token == null) {
                token = request.getParameter(Constants.CATOKEN_Attribute);
            }

            //System.out.println("url:" + url + " token:" + token + " path:" + path);
            if (donotCheckToken(url)) {
//            if (1==1){
                return true;
            } else {
                if (token != null) {
                    if (!TokenUtil.checkTokenFormat(token)) {
                        //无效的token
                        //request.getRequestDispatcher("/tip/index1.html").forward(request, response);
                        response.setContentType("application/json;charset=UTF-8");
                        response.getWriter().write("{\"result\":" + ResultInfo4Contorl.Fail + ",\"message\":\"" + ResultInfo4Contorl.ErrorTokenStr + "\"}");

                        return false;
                    } else {
                        //数据库token校验
                        TSysSession tSysSession = new TSysSession();
                        tSysSession.setSessionid(TokenUtil.getSessionidByToken(token));
                        tSysSession.setToken(TokenUtil.getDbtokenByToken(token));
//                        tSysSession.setPpid(TokenUtil.getPidByToken(token));
                        tSysSession.setUserid(TokenUtil.getUseridByToken(token));
                        tSysSession.setClientflag(TokenUtil.getClientflagByToken(token));


                        int confi_pid=Integer.valueOf(baseinfoMap.get("xproject.pid"));
//                        if(confi_pid!=tSysSession.getPpid()){
//                            throw new ManagerException("casystem.pid配置有误，请到审查配置文件（casystem.pid="+confi_pid+",session_pid="+tSysSession.getPpid()+"）。");
//                        }
                        TBaseConfig tBaseConfig = new TBaseConfig();
                        tBaseConfig.setCnfkey("IDUTIL.PROVINCEAUTHID");
                        DataSourceContextHolder.setDBType(baseinfoMap.get("xproject.pid.prefix"));
                        tBaseConfig = tBaseConfigManager.getTBaseConfig(tBaseConfig);
                        if (tBaseConfig == null) {
                            throw new ManagerException("IDUTIL.PROVINCEAUTHID 未配置，请到数据库中配置。");
                        } else {
                            int db_pid = Integer.parseInt(tBaseConfig.getCnfvalue());
//                            if(db_pid!=tSysSession.getPpid()){
//                                throw new ManagerException("IDUTIL.PROVINCEAUTHID配置有误，请审查数据库中配置（db_pid="+db_pid+",session_pid="+tSysSession.getPpid()+"）。");
//                            }
                        }


                        TSysSession resToken = userServer.checkSysSession(tSysSession);
                        if(resToken.getResult()!= ResultInfo4Contorl.Success){
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter().write("{\"result\":" + resToken.getResult() + ",\"message\":\"" + resToken.getMessge() + "\"}");

                            return false;
                        }else {
                            response.setHeader(Constants.CATOKEN_Attribute, token);
                            return true;
                        }
                    }
                } else {
                    //无效的token
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write("{\"result\":" + ResultInfo4Contorl.Fail + ",\"message\":\"" + ResultInfo4Contorl.ErrorTokenStr + "\"}");

                    return false;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            //操作失败
            //无效的token
            response.setContentType("application/json;charset=UTF-8");
            try {
                response.getWriter().write("{\"result\":" + ResultInfo4Contorl.Fail + ",\"message\":\"" + ResultInfo4Contorl.ErrorTokenStr + "\"}");
            } catch (IOException e1) {
                logger.error(e1.getMessage());
            }

        }
        return false;
    }


    private boolean donotCheckToken(String url) {
        StringBuilder sb = new StringBuilder();
        //无需token
        sb.append("#/v2/user/loginapi");
        sb.append("#/v2/user/loginoutapi");
        sb.append("#v2/test/testget");
        sb.append("#");
        return sb.toString().contains(url) ;
    }

}
