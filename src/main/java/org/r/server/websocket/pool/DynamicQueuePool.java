package org.r.server.websocket.pool;

import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * date 20-4-22 下午5:07
 *
 * @author casper
 **/
@Component
public class DynamicQueuePool {

    private final ConcurrentMap<String, SimpleMessageListenerContainer> queueThreadPool;

    public DynamicQueuePool() {
        queueThreadPool = new ConcurrentHashMap<>();
    }

    public void put(String queueName, SimpleMessageListenerContainer thread) {
        queueThreadPool.put(queueName, thread);
    }

    public SimpleMessageListenerContainer get(String queueName) {
        return queueThreadPool.get(queueName);
    }

    public void remove(String queueName) {
        SimpleMessageListenerContainer thread = queueThreadPool.get(queueName);
        if (thread != null && thread.isRunning()) {
            thread.stop();
        }
    }

}
