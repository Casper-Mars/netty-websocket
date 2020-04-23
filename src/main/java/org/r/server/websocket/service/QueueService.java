package org.r.server.websocket.service;

import org.r.server.websocket.listener.MqListener;
import org.springframework.amqp.core.Exchange;

/**
 * @author casper
 * @date 20-4-22 下午4:54
 **/
public interface QueueService {


    /**
     * 注册指定名称的队列并监听
     *
     * @param queueName  队列名称
     * @param exchange   交换路由
     * @param mqListener 监听器
     * @return
     */
    void bindNewQueueAndListen(String queueName, Exchange exchange, MqListener mqListener);

    /**
     * 根据队列名称移除队列和对应的监听器
     *
     * @param queueName 队列名称
     */
    void removeQueueAndListener(String queueName);

}
