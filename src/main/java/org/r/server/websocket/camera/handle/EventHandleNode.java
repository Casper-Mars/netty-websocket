package org.r.server.websocket.camera.handle;

/**
 * date 20-4-10 下午4:32
 *
 * @author casper
 **/
public interface EventHandleNode<T extends EventChainContext> {


    void handle(T context);

}
