package org.r.server.websocket.utils;

import org.springframework.context.ApplicationContext;

/**
 * date 2020/4/20 21:14
 *
 * @author casper
 */
public class SpringUtil {

    private final ApplicationContext context;

    private static SpringUtil springUtil;

    private SpringUtil(ApplicationContext context){
        this.context = context;
    }

    private ApplicationContext getContext(){
        return context;
    }

    public static void init(ApplicationContext context){
        springUtil = new SpringUtil(context);
    }

    public static  <T> T getBean(String name){
        return (T) springUtil.getContext().getBean(name);
    }





}
