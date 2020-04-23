package org.r.server.websocket.pool;

import io.netty.channel.Channel;
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

    private final ConcurrentMap<String, Thread> queueThreadPool;

    public DynamicQueuePool() {
        queueThreadPool = new ConcurrentHashMap<>();
    }

    public void put(String queueName, Thread thread) {
        queueThreadPool.put(queueName, thread);
    }

    public Thread get(String queueName) {
        return queueThreadPool.get(queueName);
    }

    public void remove(String queueName) {
        Thread thread = queueThreadPool.get(queueName);
        if (thread != null) {
            thread.interrupt();
        }
    }

}
