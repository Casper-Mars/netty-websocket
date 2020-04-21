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

    private final ConcurrentMap<Long, TopicExchange> exchangeCameraHandCache = new ConcurrentHashMap<>();

    public void put(Long id, TopicExchange exchange) {
        exchangeCameraHandCache.put(id, exchange);
    }

    public TopicExchange get(Long id) {
        return exchangeCameraHandCache.get(id);
    }


}
