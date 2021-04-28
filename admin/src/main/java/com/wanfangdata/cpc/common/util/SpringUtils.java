package com.wanfangdata.cpc.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Packagename com.wanfangdata.cpc.common.util
 * @Classname SpringUtils
 * @Description
 * @Authors Mr.Wu
 * @Date 2020/08/21 10:59
 * @Version 1.0
 */
@Component
public class SpringUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }
}
