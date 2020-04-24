package org.r.server.websocket.config;

import org.r.server.websocket.listener.VideoDataListener;
import org.r.server.websocket.pool.TopicExchangePool;
import org.r.server.websocket.service.MsgService;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * date 2020/4/20 22:13
 *
 * @author casper
 */
@Configuration
public class RabbitMqConfig {

    @Autowired
    private MsgService msgService;
    @Autowired
    private TopicExchangePool topicExchangePool;

    /**
     * @param connectionFactory
     * @return
     */
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean("videoQueueListener")
    public SimpleMessageListenerContainer simpleMessageListenerContainer(ConnectionFactory connectionFactory) {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(new VideoDataListener(msgService, topicExchangePool));
        String queueName = "video";
        messageListenerAdapter.addQueueOrTagToMethodName(queueName, "run");
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(connectionFactory);
        simpleMessageListenerContainer.setMaxConcurrentConsumers(1);
        simpleMessageListenerContainer.setConcurrentConsumers(1);
        simpleMessageListenerContainer.addQueueNames(queueName);
        simpleMessageListenerContainer.setMessageListener(messageListenerAdapter);
        return simpleMessageListenerContainer;
    }


}
