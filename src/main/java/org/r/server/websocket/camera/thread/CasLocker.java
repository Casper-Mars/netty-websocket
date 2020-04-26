package org.r.server.websocket.camera.thread;

import java.util.HashSet;
import java.util.Set;

/**
 * 乐观锁
 *
 * @author casper
 * @date 19-12-24 下午4:35
 **/
public class CasLocker {


    /**
     * 键集合
     */
    private Set<String> keySet;

    public CasLocker(int keySetSize) {
        keySet = new HashSet<>(keySetSize);
    }

    /**
     * 乐观操作
     *
     * @param key       键
     * @param block     是否阻塞
     * @param container 运行容器
     * @return 如果是非阻塞，没有拿到锁则返回false。流程完成返回true
     */
    public boolean cas(String key, boolean block, AtomContainer container) {

        if (block) {
            lock(key);
        } else {
            boolean b = tryLock(key);
            if (!b) {
                return false;
            }
        }
        try {
            container.run();
        } catch (Exception e){
            throw e;
        }
        finally {
            release(key);
        }
        return true;
    }


    /**
     * 尝试对键加锁，非阻塞
     *
     * @param key 键
     * @return true-成功，false-失败
     */
    public synchronized boolean tryLock(String key) {
        if (this.keySet.contains(key)) {
            return false;
        } else {
            this.keySet.add(key);
            return true;
        }
    }


    /**
     * 对键加锁，阻塞
     *
     * @param key 键
     */
    public void lock(String key) {
        while (true) {
            if (tryLock(key)) {
                return;
            }
        }
    }

    public void release(String key) {
        this.keySet.remove(key);
    }


}
