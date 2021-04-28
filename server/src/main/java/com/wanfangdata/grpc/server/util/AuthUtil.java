package com.wanfangdata.grpc.server.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wanfangdata.cas.entity.User;
import com.wanfangdata.cas.util.HttpClient;
import com.wanfangdata.cas.utils.JsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FLY
 * @date 2019年7月19日
 */
public class AuthUtil {

    protected final static Logger logger = LoggerFactory.getLogger(AuthUtil.class);

    public static String getTicket(String cookie){
        if(cookie==null){
            return null;
        }
        String[] cookies= cookie.split(";");
        if(cookies!=null&&cookies.length>0){
            for(String param:cookies){
                String[] params=param.split("=");
                if(params.length==2&&params[0].contains("CASTGC")&&params[1].contains("TGT-")){
                    return params[1];
                }
            }
        }
        return null;
    }

    public static List<User> getUserList(net.sf.json.JSONObject e ) throws IOException {
        try {
            if(e == null) {
                return new ArrayList();
            } else {
                LinkedHashMap result = HttpClient.parseSortList(e.toString(), new String[0]);
                List userList = JSONObjectToUserList(result);
                return userList;
            }
        } catch (Exception var4) {
            logger.error("获取用户列表失败。");
            return new ArrayList();
        }
    }

    private static List<User> JSONObjectToUserList(LinkedHashMap<String, String> object) {
        ArrayList userList = new ArrayList();
        if(object != null && object.keySet().size() != 0) {
            Iterator var2 = object.keySet().iterator();

            while(var2.hasNext()) {
                String key = (String)var2.next();
                userList.add(convert((String)object.get(key)));
            }

            return userList;
        } else {
            return userList;
        }
    }
    private static User convert(String data) {
        try {
            return (User) JsonHelper.deserize(data, User.class);
        } catch (IOException var2) {
            logger.error("反序列化User出错", var2);
            return new User();
        }
    }
    private static LinkedHashMap<String, String> parseSortList(String content, String... urlAndParemeter) {
        LinkedHashMap result = null;
        new JSONObject();

        try {
            result = (LinkedHashMap)JsonHelper.deserize(content, LinkedHashMap.class);
        } catch (Exception var7) {
            logger.error("顺序解析用户登录顺序出错。url:" + (urlAndParemeter != null && urlAndParemeter.length > 0?urlAndParemeter[0]:"null") + ", userInfo:" + content, var7);
            JSONObject json = JSON.parseObject(content);
            result = new LinkedHashMap();
            if(json == null || json.keySet().size() == 0) {
                return result;
            }

            Iterator var5 = json.keySet().iterator();

            while(var5.hasNext()) {
                String key = (String)var5.next();
                result.put(key, json.getString(key));
            }
        }

        return result;
    }

}