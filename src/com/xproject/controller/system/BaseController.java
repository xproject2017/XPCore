package com.xproject.controller.system;

import com.xproject.bean.control.ConfigInfo;
//import com.xproject.bean.dao.org.TBaseAuthinfos;
import com.xproject.util.PropertyFileter.JSONUtil;
import com.xproject.util.sys.Constants;
import com.xproject.util.sys.ResultInfo4Contorl;
import com.xproject.util.sys.TokenUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/18.
 */
@SuppressWarnings("ALL")
@Controller
@RequestMapping("/v2/base")
public class BaseController {
    private static Logger logger = Logger.getLogger(BaseController.class);

    @Value("#{baseinfoMap}")
    private HashMap<String, String>  baseinfoMap;


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String[].class, new StringArrayPropertyEditor(null));
    }




    //获取各基础数据
    @RequestMapping(value = "/getbasedataapi")
    @ResponseBody
    public Map<String,Object> getbasedataapi(HttpServletRequest request) {
        Map<String,Object> result = new HashMap<String, Object>();
        JSONObject data = new JSONObject();

        result.put(Constants.result,ResultInfo4Contorl.Success);
        result.put(Constants.json,data);
        return result;
    }



}
