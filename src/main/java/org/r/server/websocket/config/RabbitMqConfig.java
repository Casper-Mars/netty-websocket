package org.r.server.websocket.config;

import org.r.server.websocket.converter.VideoDataConverter;
import org.r.server.websocket.listener.VideoDataDispatchListener;
import org.r.server.websocket.listener.VideoDataListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * date 2020/4/20 22:13
 *
 * @author casper
 */
@Configuration
public class RabbitMqConfig {


    /**
     * @param connectionFactory
     * @return
     */
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(new VideoDataConverter());
        return rabbitTemplate;
    }


//    @Bean
//    public SimpleMessageListenerContainer simpleMessageListenerContainer(ConnectionFactory connectionFactory) {
//
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
////        container.addQueueNames("video");
////        container.setConcurrentConsumers(1);
////        container.setMaxConcurrentConsumers(1);
//
////        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(new VideoDataListener());
////        messageListenerAdapter.addQueueOrTagToMethodName("video", "run");
////        container.setMessageListener(messageListenerAdapter);
//        return container;
//    }

}
