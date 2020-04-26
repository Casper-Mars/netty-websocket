package org.r.server.websocket.camera.handle.status;

import org.r.server.websocket.camera.handle.AbstractEventChain;
import org.r.server.websocket.camera.handle.EventHandleNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * date 20-4-10 下午4:34
 *
 * @author casper
 **/
@Component("statusEventChain")
public class StatusEventChain extends AbstractEventChain<StatusEventContext> {

    @Autowired
    private List<EventHandleNode<StatusEventContext>> nodes;


    @Override
    public List<EventHandleNode<StatusEventContext>> getNodeList() {
        return nodes;
    }
}
