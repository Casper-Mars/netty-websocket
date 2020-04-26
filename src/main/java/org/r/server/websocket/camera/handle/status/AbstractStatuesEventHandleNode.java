package org.r.server.websocket.camera.handle.status;


import org.r.server.websocket.camera.handle.EventHandleNode;

/**
 * date 20-4-10 下午4:43
 *
 * @author casper
 **/
public abstract class AbstractStatuesEventHandleNode<T extends StatusEventContext> implements EventHandleNode<T> {


    public abstract boolean needHandle(int nStatusCode);

    public abstract void handle0(T context, int errorCode, int actionCode);

    @Override
    public void handle(T context) {
        int stateCode = (int) context.getNStateCode();
        int actionCode = stateCode & 0xffffff;
        int errorCode = stateCode & 0xff000000;
        if (!needHandle(actionCode)) {
            return;
        }
        handle0(context, errorCode, actionCode);
    }
}
