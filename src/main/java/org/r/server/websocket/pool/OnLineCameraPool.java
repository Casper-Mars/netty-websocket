package org.r.server.websocket.pool;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * date 2020/4/26 20:56
 *
 * @author casper
 */
@Component
public class OnLineCameraPool {


    private final ConcurrentMap<Long,Long> heartBeatPool = new ConcurrentHashMap<>();

    public static ReadWriteLock lock = new ReentrantReadWriteLock();


    public Long getTime(Long handle){
        return heartBeatPool.get(handle);
    }

    public void putTime(Long handle,Long time){
        heartBeatPool.put(handle,time);
    }

    public List<Long> getHandelTimeOverThan(Long time){
        List<Long> result = new ArrayList<>();
        Long now = System.currentTimeMillis();
        heartBeatPool.forEach((k,v)->{
            if(now-v > time*1000){
                result.add(k);
            }
        });
        return result;
    }






}
