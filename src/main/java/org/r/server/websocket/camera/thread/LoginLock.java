package org.r.server.websocket.camera.thread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.LockSupport;

/**
 * date 20-4-16 下午6:04
 *
 * @author casper
 **/
public class LoginLock {


    private static Map<Long, Thread> waitingPool = new ConcurrentHashMap<>((int) (32 * 0.75 + 1));


    public static void park(long handle, Thread thread) {
        park(handle,thread,0L);
    }
    public static void park(long handle) {
        park(handle,Thread.currentThread(),0L);
    }

    public static void park(long handle, Thread thread,long nanoSec) {
        waitingPool.put(handle, thread);
        LockSupport.park(nanoSec);
    }

    public static void unpark(long handle, int topTimes, long sleep) {
        Thread thread;
        for (int i = 0; i < topTimes; i++) {
            thread = waitingPool.get(handle);
            if (thread != null) {
                LockSupport.unpark(thread);
                break;
            }
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void unpark(long handle) {
        unpark(handle, 10, 1500);
    }

}
