package com.xproject.controller.system;

import com.xproject.bean.control.UserInfo;
import com.xproject.bean.dao.system.TSysMenu;
import com.xproject.bean.dao.system.TSysRole;
import com.xproject.bean.manager.system.MMenuInfo;
import com.xproject.manager.controller.system.UserServer;
import com.xproject.util.md5.Encrypt;
import com.xproject.util.sys.Constants;
import com.xproject.util.sys.ResultInfo4Contorl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/3.
 */
@SuppressWarnings("ALL")
@Controller
@RequestMapping("/v2/user")
public class UserController {
    private static Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    UserServer userServer;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String[].class, new StringArrayPropertyEditor(null));
    }

    /**
     * @param userinfo 输入
     * @return 输出
     */
    @RequestMapping(method = RequestMethod.POST,value = "/loginapi")
    @ResponseBody
    public Object loginapi(@RequestBody UserInfo userinfo,HttpServletRequest request,HttpServletResponse response) {
        Map<String,Object> result = new HashMap<>();
        try {

            //null值检查，pid和aid检查
            if (userinfo==null) {
                result.put(Constants.result, ResultInfo4Contorl.Fail);
                result.put(Constants.message, ResultInfo4Contorl.ErrorParameter);
                return result;
            }
            int clientflag = Constants.clientflag_1;
            if (request.getHeader(Constants.CLIENTFLAG_Attribute)!=null) {
                clientflag = Integer.valueOf(request.getHeader(Constants.CLIENTFLAG_Attribute));
                userinfo.setClientflag(clientflag);
            }
            //null判断
            if (userinfo.getUsername() == null || userinfo.getUserpassword() == null ) {
                result.put(Constants.result, ResultInfo4Contorl.Fail);
                result.put(Constants.message, ResultInfo4Contorl.ErrorParameter);
                return result;
            }

            UserInfo loginresul = userServer.loginapi(userinfo);
            //返回head信息
            response.setHeader(Constants.CATOKEN_Attribute, loginresul.getToken());
            response.setHeader(Constants.CLIENTFLAG_Attribute, String.valueOf(clientflag));
            loginresul.setToken(null);
            result.put(Constants.result, loginresul.getResult());
            result.put(Constants.message, loginresul.getMessge());
            result.put(Constants.json,loginresul);

        }catch (Exception e){
            logger.error(e.getMessage());
            result.put(Constants.result, ResultInfo4Contorl.Fail);
            result.put(Constants.message, ResultInfo4Contorl.ErrorOperation);
        }
        return result;
    }

    /**
     * @param userinfo 输入 roleid userid usertype companyid
     * @return 输出
     */
    @RequestMapping(method = RequestMethod.POST,value = "/getusertreeapi")
    @ResponseBody
    public Map<String,Object> getusertree(@RequestBody UserInfo bean, HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> result = new HashMap<>();
        try {
            List<MMenuInfo> userMenu = null;
            JSONObject retjson = new JSONObject();

            if (bean.getUsertype() == Constants.USER_TYPE_FACTORY){
                //如果是企业账号，则需要去省局企业表获取
                if (bean.getNusertype() == Constants.NUSER_TYPE_SUB){
                    bean.setRolename(Constants.ROLENAMEFACTORYSUB);
                }else {
                    bean.setRolename(Constants.ROLENAMEFACTORYMAIN);
                }
                userMenu = userServer.getTSysMenuByUser(bean);
            }else if (bean.getUsertype() == Constants.USER_TYPE_GOVERNMENT&&bean.getNusertype()==Constants.NUSER_TYPE_SUB){
                bean.setRolename(Constants.ROLENAMEGOVERNMENT);
                userMenu = userServer.getTSysMenuByUser(bean);
            }else {
                //除了企业账号和国检账号，其他账号都是根据角色
                TSysRole role = new TSysRole();
                role.setRoleid(bean.getRoleid());
                userMenu = userServer.getMenuListByAuthrole(role);
                if (bean.getUsertype() == Constants.USER_TYPE_PSUPPORT){

                }else if (bean.getUsertype() == Constants.USER_TYPE_BUSINESSSUPPORT){

                }
            }
            //
            JSONArray upmenus = new JSONArray();//选择子局  上面那部分的菜单
            JSONArray downmenus = new JSONArray();//选择子局  下面那部分的菜单
            for (MMenuInfo menu:userMenu){
                JSONObject firstmenu = new JSONObject();
                firstmenu.put("id", menu.getNodeid());
                firstmenu.put("label",menu.getNodetext());
                firstmenu.put("url",menu.getPath());
                if (menu.getSonmenus()!=null&&menu.getSonmenus().size()>0){
                    JSONArray smenus = new JSONArray();
                    for (TSysMenu sonmenu:menu.getSonmenus()){
                        JSONObject secmenu = new JSONObject();
                        secmenu.put("id", sonmenu.getNodeid());
                        secmenu.put("label",sonmenu.getNodetext());
                        secmenu.put("url",sonmenu.getPath());
                        smenus.add(secmenu);
                    }
                    firstmenu.put("list",smenus);
                }

                //需要用一个字段区分是属于哪部分的菜单
                if (menu.getMtype() == 3) {
                    upmenus.add(firstmenu);
                }else if (menu.getMtype() == 4){
                    downmenus.add(firstmenu);
                }
            }

            JSONArray authlist = new JSONArray();//对应子局
            retjson.put("menulist",upmenus);
            retjson.put("govmenulist",downmenus);
            retjson.put("govlist",authlist);

            result.put(Constants.result,ResultInfo4Contorl.Success);
            result.put(Constants.json,retjson);
        }catch (Exception e){
            logger.error(e.getMessage());
            result.put(Constants.result, ResultInfo4Contorl.Fail);
            result.put(Constants.message, ResultInfo4Contorl.ErrorOperation);
        }
        return result;
    }


    /**
     * @param userinfo 输入 aaid ppid userid usertype
     * @return 输出
     */
    @RequestMapping(method = RequestMethod.POST,value = "/updateauthmenuapi")
    @ResponseBody
    public Map<String,Object> updateauthmenuapi(@RequestBody UserInfo bean, HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> result = new HashMap<>();
        try {
            List<MMenuInfo> userMenu = null;
            JSONObject retjson = new JSONObject();

            if (bean.getUsertype() == Constants.USER_TYPE_FACTORY||(bean.getUsertype() == Constants.USER_TYPE_GOVERNMENT&&bean.getNusertype()==Constants.NUSER_TYPE_SUB)){
                //如果是企业账号或国检账号，则需要去省局企业表获取
                userMenu = userServer.getTSysMenuByUser(bean);
            }else {

            }
            //
            JSONArray downmenus = new JSONArray();//选择子局  下面那部分的菜单
            if (userMenu!=null) {
                for (MMenuInfo menu : userMenu) {
                    JSONObject firstmenu = new JSONObject();
                    firstmenu.put("id", menu.getNodeid());
                    firstmenu.put("label", menu.getNodetext());
                    firstmenu.put("url", menu.getPath());
                    if (menu.getSonmenus() != null && menu.getSonmenus().size() > 0) {
                        JSONArray smenus = new JSONArray();
                        for (TSysMenu sonmenu : menu.getSonmenus()) {
                            JSONObject secmenu = new JSONObject();
                            secmenu.put("id", sonmenu.getNodeid());
                            secmenu.put("label", sonmenu.getNodetext());
                            secmenu.put("url", sonmenu.getPath());
                            smenus.add(secmenu);
                        }
                        firstmenu.put("list", smenus);
                    }

                    //需要用一个字段区分是属于哪部分的菜单
                    if (menu.getMtype() == 4) {
                        downmenus.add(firstmenu);
                    }
                }
            }
            retjson.put("govmenulist",downmenus);

            result.put(Constants.result,ResultInfo4Contorl.Success);
            result.put(Constants.json,retjson);
        }catch (Exception e){
            logger.error(e.getMessage());
            result.put(Constants.result, ResultInfo4Contorl.Fail);
            result.put(Constants.message, ResultInfo4Contorl.ErrorOperation);
        }
        return result;
    }

    /**密码重置
     * @param userinfo 输入 aaid ppid userid savetime
     * @return 输出
     */
    @RequestMapping(method = RequestMethod.POST,value = "/resetpwdapi")
    @ResponseBody
    public Map<String,Object> resetpwdapi(@RequestBody UserInfo bean) {
        Map<String, Object> result = new HashMap<>();
        try{
            bean.setUserpassword(Encrypt.encryptMD5(Constants.USERPASSWORD));
            UserInfo retuser = userServer.updateUser(bean);
            result.put(Constants.result, retuser.getResult());
            result.put(Constants.message, retuser.getMessge());
        }catch (Exception e){
            logger.error(e.getMessage());
            result.put(Constants.result, ResultInfo4Contorl.Fail);
            result.put(Constants.message, ResultInfo4Contorl.ErrorOperation);
        }
        return result;
    }

    /**密码修改
     * @param userinfo 输入 aaid ppid userid savetime
     * @return 输出
     */
    @RequestMapping(method = RequestMethod.POST,value = "/modifypwdapi")
    @ResponseBody
    public Map<String,Object> modifypwdapi(@RequestBody UserInfo bean) {
        Map<String, Object> result = new HashMap<>();
        try{
            bean.setUserpassword(bean.getNewuserpassword());
            UserInfo retuser = userServer.updateUser(bean);
            result.put(Constants.result, retuser.getResult());
            result.put(Constants.message, retuser.getMessge());
        }catch (Exception e){
            logger.error(e.getMessage());
            result.put(Constants.result, ResultInfo4Contorl.Fail);
            result.put(Constants.message, ResultInfo4Contorl.ErrorOperation);
        }
        return result;
    }

}
