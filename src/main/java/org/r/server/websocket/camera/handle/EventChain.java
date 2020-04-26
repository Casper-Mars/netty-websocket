package org.r.server.websocket.camera.handle;

/**
 * @author casper
 * @date 20-4-10 下午4:40
 **/
public interface EventChain<T extends EventChainContext> {

    void doChain(T context);

}
