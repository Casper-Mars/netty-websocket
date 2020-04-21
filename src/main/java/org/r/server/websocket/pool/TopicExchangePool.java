package org.r.server.websocket.pool;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * date 20-4-21 上午9:32
 *
 * @author casper
 **/
@Component
public class TopicExchangePool {

    private final ConcurrentMap<Long, TopicExchange> exchangeCameraIdCache = new ConcurrentHashMap<>();

    private final ConcurrentMap<Long, TopicExchange> exchangeHandleCache = new ConcurrentHashMap<>();

    private static TopicExchangePool topicExchangePool = new TopicExchangePool();

    private TopicExchangePool() {

    }

    public static TopicExchangePool getInstance() {
        return topicExchangePool;
    }


    public void putId(Long id, TopicExchange exchange) {
        exchangeCameraIdCache.put(id, exchange);
    }

    public TopicExchange getById(Long id) {
        return exchangeCameraIdCache.get(id);
    }

    public void removeId(Long id) {
        exchangeCameraIdCache.remove(id);
    }

    public void putHandle(Long handle, TopicExchange exchange) {
        exchangeHandleCache.put(handle, exchange);
    }

    public TopicExchange getByHandle(Long handle) {
        return exchangeHandleCache.get(handle);
    }

    public void removeHandle(Long handle) {
        exchangeHandleCache.remove(handle);
    }

}
