package com.wanfangdata.cpc.web;


import com.wanfangdata.cas.entity.User;
import com.wanfangdata.cas.util.HttpClient;
import com.wanfangdata.cas.utils.JsonHelper;
import com.wanfangdata.cpc.entity.Config;
import com.wanfangdata.cpc.entity.Domain;
import com.wanfangdata.cpc.entity.Template;
import com.wanfangdata.cpc.entity.UserInfo;
import com.wanfangdata.cpc.model.ResponseModel;
import com.wanfangdata.cpc.service.DataService;
import com.wanfangdata.cpc.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FLY
 * @create 2018-01-05 8:23
 **/
@Api(value = "/api", description = "公共接口")
@RestController
@AllArgsConstructor
public class IndexController {
    private static Logger logger = LoggerFactory.getLogger(IndexController.class);

    Domain domain;
    DataService dataService;


    @ApiOperation(value = "获取配置信息", notes = "获取配置信息", httpMethod = "GET")
    @ApiResponses({@ApiResponse(code = 400, message = "请求参数有误")})
    @RequestMapping(value = "/config", method = RequestMethod.GET)
    public ResponseModel config(String alias) {
        try {
            Template template=dataService.getTemplate(alias);
            Config config=new Config(template,domain);
            return ResultUtil.success("查询成功",config);
        }catch (Exception e){
            logger.error("查询失败 alias:{} msg:{}",alias,e.getMessage());
            return ResultUtil.error("查询失败");
        }
    }


    @ApiOperation(value = "获取登录信息", notes = "获取登录信息", httpMethod = "GET")
    @ApiResponses({@ApiResponse(code = 400, message = "请求参数有误")})
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseModel users(HttpServletRequest request) {
        try {
            List<User> userList =null;
            JSONObject e = JSONObject.fromObject(request.getAttribute("user"));
            if(e == null) {
                userList=new ArrayList();
            } else {
                LinkedHashMap result = HttpClient.parseSortList(e.toString(), new String[0]);
                userList = JSONObjectToUserList(result);
            }
            if (logger.isDebugEnabled()){
                logger.debug("userList:" + userList);
            }
            List<UserInfo> userInfos=new ArrayList<>();
            for(User user:userList){
                UserInfo info=new UserInfo();
                info.setUserId(user.getUserId());
                info.setUserRealName(user.getUserRealName());
                info.setUserNickname(user.getUserNickname());
                info.setInstitution(user.getInstitution());
                info.setUserType(user.getUserType());
                userInfos.add(info);
            }
            return ResultUtil.success("查询成功",userInfos);
        }catch (Exception e){
            logger.error("查询用户信息失败 msg:{}",e.getMessage());
            return ResultUtil.error("查询失败");
        }

    }


    private static List<User> JSONObjectToUserList(LinkedHashMap<String, String> object) {
        List<User> userList = new ArrayList<User>();
        if(object != null && object.keySet().size() != 0) {
            Iterator var2 = object.keySet().iterator();

            while(var2.hasNext()) {
                String key = (String)var2.next();
                User user=convert((String)object.get(key));
                if(user.getUserType()==0){
                    continue;
                }
                userList.add(user);
            }
            return userList;
        } else {
            return userList;
        }
    }

    private static User convert(String data) {
        try {
            return JsonHelper.deserize(data, User.class);
        } catch (IOException var2) {
            return new User();
        }
    }
}
