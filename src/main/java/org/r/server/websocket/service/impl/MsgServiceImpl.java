package org.r.server.websocket.service.impl;

import org.r.server.websocket.service.MsgService;
import org.r.server.websocket.utils.Constants;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * date 20-4-21 下午6:37
 *
 * @author casper
 **/
@Service
public class MsgServiceImpl implements MsgService {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 发送消息
     *
     * @param exchange   交换器
     * @param routingKey 路由键
     * @param bytes      数据
     */
    @Override
    public void sendMsg(Exchange exchange, String routingKey, byte[] bytes) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader(Constants.VIDEO_DATA_MSG_HEADER,new Object());
        Message msg = new Message(bytes, messageProperties);
        rabbitTemplate.send(exchange.getName(), routingKey, msg);
    }

    /**
     * 发送消息
     *
     * @param exchange 交换器
     * @param bytes    数据
     */
    @Override
    public void sendMsg(Exchange exchange, byte[] bytes) {
        sendMsg(exchange, "", bytes);
    }
}
