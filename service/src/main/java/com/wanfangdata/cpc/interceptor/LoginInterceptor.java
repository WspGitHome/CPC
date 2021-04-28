package com.wanfangdata.cpc.interceptor;


import com.wanfangdata.cas.WFCasClient;
import com.wanfangdata.cas.entity.User;
import com.wanfangdata.cpc.config.BaseConfig;
import com.wanfangdata.cpc.utils.StringUtil;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;

@Component
@Order(11)
public class LoginInterceptor implements HandlerInterceptor {


    BaseConfig config;
    /**  登录拦截业务
     *  1 获取session中的用户信息
     *  2 如果为空 证明没有登录 则 返回false 并重定向到 登录界面
     *  3 如果不为空 则 放行 true
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object arg2) throws Exception {
            List<User> userList = WFCasClient.getUserList(request);
            if(userList.size()==0){
                String requestUrl= StringUtil.nvl( request.getHeader("Referer"));
                String logInUrl= config.getLogin() + "service=" + URLEncoder.encode(requestUrl, "UTF-8");
                response.sendRedirect(logInUrl);
                return false;
            }
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
                           Object arg2, ModelAndView arg3) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest arg0,
                                HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {

    }
    /**
     * 获取用户token
     * @param request
     * @return
     */
    public static String getauthtoken(HttpServletRequest request){
        //获取权限信息开始
        Cookie[] cs = request.getCookies();
        String ticket="";
        if(cs!=null){
            for (int i = 0; i < cs.length; i++) {
                String name = cs[i].getName();
                if(name.equals("CASTGC")){
                    ticket = cs[i].getValue();
                }
            }
        }

        return ticket;
    }
    private void returnJson(HttpServletResponse response, String json) throws Exception{
                PrintWriter writer = null;
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html; charset=utf-8");
                try {
                    writer = response.getWriter();
                    writer.print(json);
                    } catch (IOException e) {
                    } finally {
                         if (writer != null) {
                             writer.close();
                         }
             }
        }

}
