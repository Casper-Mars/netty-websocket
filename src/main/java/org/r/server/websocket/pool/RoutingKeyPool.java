package org.r.server.websocket.pool;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 摄像机和对应的exchange池
 * date 2020/4/20 21:12
 *
 * @author casper
 */
@Component
public class RoutingKeyPool {

    private final ConcurrentMap<Long, String> exchangeCameraHandCache = new ConcurrentHashMap<>();

    public void put(String routingKey, Long id) {
        exchangeCameraHandCache.put(id, routingKey);
    }

    public String getRoutingKey(Long id) {
        return exchangeCameraHandCache.get(id);
    }

    public boolean hasId(Long id) {
        return exchangeCameraHandCache.containsKey(id);
    }

    public boolean hasRoutingKey(String exChangeName) {
        return exchangeCameraHandCache.containsValue(exChangeName);
    }


}
