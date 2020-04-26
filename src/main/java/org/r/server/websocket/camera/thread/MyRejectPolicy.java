package org.r.server.websocket.camera.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * date 20-4-13 下午4:55
 *
 * @author casper
 **/
@Slf4j
public class MyRejectPolicy extends ThreadPoolExecutor.CallerRunsPolicy {

    /**
     * Executes task r in the caller's thread, unless the executor
     * has been shut down, in which case the task is discarded.
     *
     * @param r the runnable task requested to be executed
     * @param e the executor attempting to execute this task
     */
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        super.rejectedExecution(r, e);
        log.info("get a reject execution");
    }
}
