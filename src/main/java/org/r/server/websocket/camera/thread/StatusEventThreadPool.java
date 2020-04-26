package org.r.server.websocket.camera.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * date 20-4-9 下午5:13
 *
 * @author casper
 **/
@Slf4j
public class StatusEventThreadPool extends ThreadPoolExecutor {


    public StatusEventThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                r -> new Thread(r, "handle-thread"),
                new MyRejectPolicy()
        );
    }
}
