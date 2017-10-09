package com.xproject.controller.test;

import com.xproject.bean.dao.system.TSysSession;
import com.xproject.bean.dao.test.TTmpTest;
import com.xproject.util.sys.Constants;
import com.xproject.util.sys.ParameterCheckUtil;
import com.xproject.util.sys.ResultInfo4Contorl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/9.
 */
@SuppressWarnings("ALL")
@Controller
@RequestMapping("/v2/test")
public class TestController {
    private static Logger logger = Logger.getLogger(TestController.class);
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String[].class, new StringArrayPropertyEditor(null));
    }


    /**
     * @param in_parameter 输入
     * @return 输出
     */
    @RequestMapping(method = RequestMethod.GET,value = "/addtestapi")
    @ResponseBody
    public Object addtestapi(HttpServletRequest request,HttpServletResponse response,MultipartFile file) {
        Map<String,Object> result = new HashMap<>();
        result.put("result",0);
        return result;
//        try {
//            response.addHeader("Access-Control-Allow-Origin", "*");
//            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
//            response.addHeader("Access-Control-Allow-Headers", "Content-Type");
//            response.setHeader("Access-Control-Allow-Headers","x-requested-with,content-type,CA-Token,Client-Flag");
//
//            response.addHeader("Access-Control-Max-Age", "1800");//30 min
//            response.setHeader(Constants.CATOKEN_Attribute, "1234567890123");
//            response.setHeader("Access1", "1234567890123");
//            response.setHeader("Access2", "1234567890123");
//            response.setHeader("Access3", "1234567890123");
//
//            response.setHeader("content-type", "1234567890123");


            //null值检查，pid和aid检查
//            if (ParameterCheckUtil.check(in_parameter)) {
//                result.put(Constants.result, ResultInfo4Contorl.Fail);
//                result.put(Constants.message, ResultInfo4Contorl.ErrorParameter);
//                return result;
//            }
//
//            //null判断
//            if (in_parameter.getTitle() == null || in_parameter.getNews() == null ) {
//                result.put(Constants.result, ResultInfo4Contorl.Fail);
//                result.put(Constants.message, ResultInfo4Contorl.ErrorParameter);
//                return result;
//            }

//            TTmpTest addTTmpTest = testServer.addTTmpTest(in_parameter);
//            result.put(Constants.result, addTTmpTest.getResult());
//            result.put(Constants.message, addTTmpTest.getMessge());
           // result.put(Constants.json,addTTmpTest);

//            result.put(Constants.result, ResultInfo4Contorl.Success);
//            result.put(Constants.message,"成功！！！");
//            result.put(Constants.json,"[{a:1}]");
//
//        }catch (Exception e){
//            logger.error(e.getMessage());
//            result.put(Constants.result, ResultInfo4Contorl.Fail);
//            result.put(Constants.message, ResultInfo4Contorl.ErrorOperation);
//        }
//        return result;
    }
    /**
     * @param in_parameter 输入
     * @return 输出
     */
    @RequestMapping(method = RequestMethod.POST,value = "/querytestapi")
    @ResponseBody
    public Object querytestapi(@RequestBody TTmpTest in_parameter,HttpServletRequest request,HttpServletResponse response) {
        Map<String,Object> result = new HashMap<>();
        try {
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type");
            response.setHeader("Access-Control-Allow-Headers","x-requested-with,content-type,CA-Token,Client-Flag");

            response.addHeader("Access-Control-Max-Age", "1800");//30 min
            response.setHeader(Constants.CATOKEN_Attribute, "1234567890123");
            response.setHeader("Access1", "1234567890123");
            response.setHeader("Access2", "1234567890123");
            response.setHeader("Access3", "1234567890123");

            response.setHeader("content-type", "1234567890123");


            //null值检查，pid和aid检查
            if (ParameterCheckUtil.check(in_parameter)) {
                result.put(Constants.result, ResultInfo4Contorl.Fail);
                result.put(Constants.message, ResultInfo4Contorl.ErrorParameter);
                return result;
            }

            //null判断
            if (in_parameter.getTitle() == null || in_parameter.getNews() == null ) {
                result.put(Constants.result, ResultInfo4Contorl.Fail);
                result.put(Constants.message, ResultInfo4Contorl.ErrorParameter);
                return result;
            }

            int clientflag = Integer.valueOf(request.getHeader(Constants.CLIENTFLAG_Attribute));
            String token=request.getHeader(Constants.CATOKEN_Attribute);
            response.setHeader(Constants.CATOKEN_Attribute, token);
            response.setHeader(Constants.CLIENTFLAG_Attribute, String.valueOf(clientflag));

//            List<TTmpTest> list = testServer.getTTmpTestList(in_parameter);
//            result.put(Constants.result, list.get(0).getResult());
//            result.put(Constants.message, list.get(0).getMessge());
//            result.put(Constants.json,list);
            result.put(Constants.result, ResultInfo4Contorl.Success);
            result.put(Constants.message,"成功！！！");
            result.put(Constants.json,"[{a:1}]");

        }catch (Exception e){
            logger.error(e.getMessage());
            result.put(Constants.result, ResultInfo4Contorl.Fail);
            result.put(Constants.message, ResultInfo4Contorl.ErrorOperation);
        }
        return result;
    }

    /**
     * @param in_parameter 输入
     * @return 输出
     */
    @RequestMapping(method = RequestMethod.POST,value = "/notokentestapi")
    @ResponseBody
    public Object notokentestapi(HttpServletRequest request,HttpServletResponse response) {
        Map<String,Object> result = new HashMap<>();
        try {
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type");
            response.setHeader("Access-Control-Allow-Headers","x-requested-with,content-type,CA-Token,Client-Flag");
            response.addHeader("Access-Control-Max-Age", "1800");//30 min

            //返回head信息
            int clientflag = Integer.valueOf(request.getHeader(Constants.CLIENTFLAG_Attribute));
            String token=request.getHeader(Constants.CATOKEN_Attribute);
            response.setHeader(Constants.CATOKEN_Attribute, token);
            response.setHeader(Constants.CLIENTFLAG_Attribute, String.valueOf(clientflag));

            result.put(Constants.result, ResultInfo4Contorl.Success);
            result.put(Constants.message,"成功！！！");
            result.put(Constants.json,"[{a:1}]");

        }catch (Exception e){
            logger.error(e.getMessage());
            result.put(Constants.result, ResultInfo4Contorl.Fail);
            result.put(Constants.message, ResultInfo4Contorl.ErrorOperation);
        }
        return result;
    }


    /**
     * @param in_parameter 输入
     * @return 输出
     */
    @RequestMapping(method = RequestMethod.POST,value = "multidbaddapi")
    @ResponseBody
    public Object multidbaddapi(@RequestBody TTmpTest in_parameter,HttpServletRequest request,HttpServletResponse response) {
        Map<String,Object> result = new HashMap<>();
        try {
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type");
            response.setHeader("Access-Control-Allow-Headers","x-requested-with,content-type,CA-Token,Client-Flag");
            response.addHeader("Access-Control-Max-Age", "1800");//30 min
            //返回head信息
            int clientflag = Integer.valueOf(request.getHeader(Constants.CLIENTFLAG_Attribute));
            String token=request.getHeader(Constants.CATOKEN_Attribute);
            response.setHeader(Constants.CATOKEN_Attribute, token);
            response.setHeader(Constants.CLIENTFLAG_Attribute, String.valueOf(clientflag));

            //空值判断（特定属性必须先判断）
            if(in_parameter==null){
                result.put(Constants.result, ResultInfo4Contorl.Fail);
                result.put(Constants.message, ResultInfo4Contorl.ErrorParameter);
                return result;
            }
            if(in_parameter.getTitle()==null){
                result.put(Constants.result, ResultInfo4Contorl.Fail);
                result.put(Constants.message, ResultInfo4Contorl.ErrorParameter);
                return result;
            }


        }catch (Exception e){
            logger.error(e.getMessage());
            result.put(Constants.result, ResultInfo4Contorl.Fail);
            result.put(Constants.message, ResultInfo4Contorl.ErrorOperation);
        }
        return result;
    }

}
