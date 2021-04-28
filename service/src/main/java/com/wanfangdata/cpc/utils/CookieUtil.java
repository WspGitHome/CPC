package com.wanfangdata.cpc.utils;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author zhangshuai
 * @version 1.0
 * @FileName: CookieUtil.java
 * @Date: 2017年12月26日
 * @Description: TODO
 * @Function List: // 主要函数及其功能
 * @History: zhangshuai 2017年12月26日 1.0 build this moudle
 */
public class CookieUtil {
    private static final Logger logger = Logger.getLogger(CookieUtil.class);

    /**
     * @Function:
     * @Description:删除cookie
     * @Input: @param request
     * @Input: @param response
     * @Input: @param name
     * @Output: void
     * @Others:
     */
    public static void delCookie(HttpServletRequest request, HttpServletResponse response, String... names) {
        int countNamesLength = 0;
        // 全部cookie
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                for (String name : names) {
                    if (cookie.getName().equals(name)) {
                        cookie.setMaxAge(0);// 立即销毁cookie
                        cookie.setDomain(Constant.DOMAIN_ALL);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                        countNamesLength++;
                        if (countNamesLength == names.length)
                            break;
                    }
                }
            }
        }
    }

    public static void addCookie(HttpServletResponse response, String name, String value, String domain, String path,
                                 int expiry) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(expiry);// 设置为30min
        cookie.setPath(path);
        cookie.setDomain(domain);
        response.addCookie(cookie);
    }

    public static void addCookies(HttpServletResponse response, Map<String, String> keyValues, String domain,
                                  String path, int expiry) {
        logger.info("addCookies开始：keyValues：" + JSON.toJSONString(keyValues) + ",domain:" + domain + ",path:" + path + ",expiry:" + expiry);
        for (Entry<String, String> entry : keyValues.entrySet()) {
            String encodeName = "";
            try {
                logger.info("entry：" + entry.toString());
                encodeName = URLEncoder.encode(entry.getValue(), "utf-8");
                Cookie cookie = new Cookie(entry.getKey(), encodeName);
                cookie.setMaxAge(expiry);// 设置为30min
                cookie.setPath(path);
                cookie.setDomain(domain);
                response.addCookie(cookie);
            } catch (UnsupportedEncodingException e) {
                logger.error("addCookies出现异常：", e);
                e.printStackTrace();
            } catch (Exception e) {
                logger.error("addCookie出现异常：", e);
            }
        }
//        for (Entry<String, String> entry : keyValues.entrySet()) {
//            Cookie cookie = new Cookie(entry.getKey(), entry.getValue());
//            cookie.setMaxAge(expiry);// 设置为30min
//            cookie.setPath(path);
//            cookie.setDomain(domain);
//            response.addCookie(cookie);
//        }
    }

    public static String getCookieByKey(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i] != null && key.equals(cookies[i].getName())) {
                return cookies[i].getValue();
            }
        }
        return null;
    }
}
