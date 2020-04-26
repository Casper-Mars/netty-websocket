package org.r.server.websocket.listener;

import lombok.extern.slf4j.Slf4j;
import org.r.server.websocket.camera.handle.StatusEventHandler;
import org.r.server.websocket.camera.pojo.dto.StatusEventDto;
import org.r.server.websocket.camera.thread.MyThreadBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * date 20-4-14 下午6:00
 *
 * @author casper
 **/
@Component
@Slf4j
@RabbitListener(queues = {"face"})
public class StatusEventListener implements Closeable {

    private ExecutorService executor;

    private final MyThreadBuilder builder;

    public StatusEventListener(MyThreadBuilder builder) {
        log.info("初始化人脸识别签到线程池");
        executor = builder.build();
        this.builder = builder;
    }

    @RabbitHandler
    public void run(byte[] task) {
        try {
            String target = new String(task);
//            log.info("get message from mq:" + target);
            StatusEventDto statusEventDto = new StatusEventDto();
            String[] split = target.split(",");
            if (split.length != 3) {
                return;
            }
            statusEventDto.setLUser(Long.valueOf(split[0]));
            statusEventDto.setNStateCode(Long.valueOf(split[1]));
            statusEventDto.setPResponse(split[2]);
            executor.execute(new StatusEventHandler(statusEventDto.getLUser(), statusEventDto.getNStateCode(), statusEventDto.getPResponse()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void close() throws IOException {
        executor.shutdown();
        try {
            executor.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            executor.shutdownNow();
        }
        log.info("人脸识别签到线程池关闭");
    }
}
