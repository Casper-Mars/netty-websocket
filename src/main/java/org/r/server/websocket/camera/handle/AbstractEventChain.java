package org.r.server.websocket.camera.handle;

import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * date 20-4-9 下午4:23
 *
 * @author casper
 **/
public abstract class AbstractEventChain<T extends EventChainContext> implements EventChain<T> {


    public abstract List<EventHandleNode<T>> getNodeList();

    public void doChain(T context) {
        if (context == null) {
            return;
        }
        List<EventHandleNode<T>> nodeList = getNodeList();
        if (CollectionUtils.isEmpty(nodeList)) {
            return;
        }
        for (EventHandleNode<T> handleNode : nodeList) {
            handleNode.handle(context);
        }
    }


}
