package org.r.server.websocket.listener;

import org.r.server.websocket.camera.dao.TeachFaceMachineDao;
import org.r.server.websocket.camera.entity.TeachFaceMachine;
import org.r.server.websocket.camera.enums.CameraStatusEnum;
import org.r.server.websocket.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * date 20-4-21 上午10:14
 *
 * @author casper
 **/
@Component
public class SpringContextListener implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    private TeachFaceMachineDao teachFaceMachineDao;


    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        ConfigurableApplicationContext applicationContext = applicationStartedEvent.getApplicationContext();
        SpringUtil.init(applicationContext);

        /*启动的时候重置摄像机的句柄和状体*/
        List<TeachFaceMachine> all = teachFaceMachineDao.findAll();
        if (!CollectionUtils.isEmpty(all)) {
            all.forEach(t -> {
                t.setUpdateTime(new Date());
                t.setCameraStatues(CameraStatusEnum.OFF.getCode());
                t.setCameraHandle(-1);
            });
            teachFaceMachineDao.saveAll(all);
        }

    }
}
