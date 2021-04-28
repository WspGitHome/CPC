package com.wanfangdata.cpc.interceptor;


import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Order(12)
public class PowerInterceptor implements HandlerInterceptor {

    /**  登录拦截业务
     *  1 获取session中的用户信息
     *  2 如果为空 证明没有登录 则 返回false 并重定向到 登录界面
     *  3 如果不为空 则 放行 true
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse resp,
                             Object arg2) throws Exception {
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

}
