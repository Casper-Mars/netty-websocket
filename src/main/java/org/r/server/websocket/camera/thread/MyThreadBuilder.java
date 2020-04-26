package org.r.server.websocket.camera.thread;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * date 20-4-14 下午6:19
 *
 * @author casper
 **/
@Component
public class MyThreadBuilder {


    /**
     * 线程池核心大小
     */
    @Value("${camera-manager.threadPool.corePoolSize}")
    private int corePoolSize = 10;
    /**
     * 线程池最大数量
     */
    @Value("${camera-manager.threadPool.maximumPoolSize}")
    private int maximumPoolSize = 10;
    /**
     * 空闲线程生存时间
     */
    @Value("${camera-manager.threadPool.keepAliveTime}")
    private long keepAliveTime = 1;

    public ThreadPoolExecutor build() {
        TimeUnit _timeUnit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> _blockingQueue = new ArrayBlockingQueue<>(100);
        return new StatusEventThreadPool(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                _timeUnit,
                _blockingQueue);
    }

}
