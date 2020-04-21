package org.r.server.websocket.listener;

import org.r.server.websocket.utils.SpringUtil;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * date 20-4-21 上午10:14
 *
 * @author casper
 **/
@Component
public class SpringContextListener implements ApplicationListener<ApplicationStartedEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        ConfigurableApplicationContext applicationContext = applicationStartedEvent.getApplicationContext();
        SpringUtil.init(applicationContext);
    }
}
