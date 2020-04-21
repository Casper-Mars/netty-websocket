package org.r.server.websocket.listener;

import org.r.server.websocket.pool.TopicExchangePool;
import org.r.server.websocket.service.MsgService;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * date 20-4-20 下午4:11
 *
 * @author casper
 **/
@Component
@RabbitListener(queues = {"video"})
public class VideoDataListener {

    @Autowired
    private MsgService msgService;
    @Autowired
    private TopicExchangePool topicExchangePool;


    @RabbitHandler
    public void run(byte[] buf) {
        System.out.println("=================get a video call back======================");
        long tmp = buf[1] & 0xff;
        long lUserId = 0;
        /*移位接收long数据*/
        for (int i = 7; i >= 0; i--) {
            lUserId = lUserId << 8;
            lUserId = lUserId | (buf[i] & 0xff);
        }
        System.out.println(lUserId);
        TopicExchange exchange = topicExchangePool.getByHandle(lUserId);
        if (exchange != null) {
            msgService.sendMsg(exchange, "*", buf);
        }
    }

}
