package org.r.server.websocket.service;

import org.springframework.amqp.core.Exchange;

/**
 * @author casper
 * @date 20-4-21 下午6:34
 **/
public interface MsgService {


    /**
     * 发送消息
     *
     * @param exchange   交换器
     * @param routingKey 路由键
     * @param bytes      数据
     */
    void sendMsg(Exchange exchange, String routingKey, byte[] bytes);

    /**
     * 发送消息
     *
     * @param exchange 交换器
     * @param bytes    数据
     */
    void sendMsg(Exchange exchange, byte[] bytes);


}
