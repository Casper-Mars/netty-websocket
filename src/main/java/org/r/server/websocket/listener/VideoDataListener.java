package org.r.server.websocket.listener;

import org.r.server.websocket.pool.TopicExchangePool;
import org.r.server.websocket.service.MsgService;
import org.r.server.websocket.utils.ByteUtil;
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
public class VideoDataListener implements MqListener<byte[]>{

    private final MsgService msgService;
    private final TopicExchangePool topicExchangePool;

    public VideoDataListener(MsgService msgService, TopicExchangePool topicExchangePool) {
        this.msgService = msgService;
        this.topicExchangePool = topicExchangePool;
    }


    @RabbitHandler
    public void run(byte[] buf) {
//        System.out.println("=================get a video call back======================");
        long lUserId = ByteUtil.byteToLong(buf,0);
//        System.out.println(lUserId);
        TopicExchange exchange = topicExchangePool.getByHandle(lUserId);
        if (exchange != null) {
            msgService.sendMsg(exchange, "*", buf);
        }
    }

    /**
     * 获取监听的队列名称
     *
     * @return
     */
    @Override
    public String getQueueName() {
        return "video";
    }

}
