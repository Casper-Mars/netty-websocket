package org.r.server.websocket.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * date 20-4-20 下午4:11
 *
 * @author casper
 **/
@Component
@RabbitListener(queues = {"video"})
public class VideoDataListener {

    @RabbitHandler
    public void run(byte[] buf){
        System.out.println("=================get a video call back======================");
        System.out.println(buf.length);
    }



}
