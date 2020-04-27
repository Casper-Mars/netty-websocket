package org.r.server.websocket.config;

import org.r.server.websocket.task.HeartBeatTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * date 2020/4/26 21:15
 *
 * @author casper
 */
@Configuration
@EnableScheduling
public class TaskConfig implements SchedulingConfigurer {


    @Value("${camera-manager.heart-beat.delay}")
    private long delay;
    @Autowired
    private HeartBeatTask heartBeatTask;


    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {

        /*添加检查心跳包的定时任务*/
        scheduledTaskRegistrar.addFixedDelayTask(heartBeatTask, delay * 1000);


    }
}




