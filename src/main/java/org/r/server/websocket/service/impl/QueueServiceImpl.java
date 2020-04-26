package org.r.server.websocket.service.impl;

import org.r.server.websocket.listener.MqListener;
import org.r.server.websocket.pool.DynamicQueuePool;
import org.r.server.websocket.service.QueueService;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * date 20-4-22 下午4:59
 *
 * @author casper
 **/
@Service("queueService")
public class QueueServiceImpl implements QueueService {


    @Autowired
    private RabbitAdmin rabbitAdmin;
    @Autowired
    private ConnectionFactory rabbitMqConnectionFactory;
    @Autowired
    private DynamicQueuePool dynamicQueuePool;
    @Value("${camera-manager.dispatchStrategy}")
    private int dispatchStrategy;

    /**
     * 注册指定名称的队列并监听
     *
     * @param queueName  队列名称
     * @param exchange   交换路由
     * @param mqListener 监听器
     * @return
     */
    @Override
    public void bindNewQueueAndListen(String queueName, Exchange exchange, MqListener mqListener) {
        Queue queue = new Queue(queueName, true, false, false);
        /*重复的queue只会创建一次*/
        rabbitAdmin.declareQueue(queue);
        Binding binding = BindingBuilder.bind(queue).to(exchange).with("*").noargs();
        rabbitAdmin.declareBinding(binding);
        if (dispatchStrategy == 1) {
            System.out.println("创建监听器线程");
            SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(rabbitMqConnectionFactory);
            MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(mqListener);
            messageListenerAdapter.addQueueOrTagToMethodName(queueName, "run");
            simpleMessageListenerContainer.addQueueNames(queueName);
            simpleMessageListenerContainer.setMessageListener(messageListenerAdapter);
            simpleMessageListenerContainer.setConcurrentConsumers(1);
            simpleMessageListenerContainer.setMaxConcurrentConsumers(1);
            dynamicQueuePool.put(queueName, simpleMessageListenerContainer);
            simpleMessageListenerContainer.start();
        }
    }

    /**
     * 根据队列名称移除队列和对应的监听器
     *
     * @param queueName 队列名称
     */
    @Override
    public void removeQueueAndListener(String queueName) {
        System.out.println("队列：" + queueName + " 退出监听");
        if (dispatchStrategy == 1) {
            dynamicQueuePool.remove(queueName);
        }
        rabbitAdmin.deleteQueue(queueName);
    }


}
