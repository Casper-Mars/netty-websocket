package org.r.server.websocket.camera.handle.status;

import org.r.server.websocket.camera.enums.EventStatusCode;
import org.r.server.websocket.pool.OnLineCameraPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * date 20-4-26 下午4:40
 *
 * @author casper
 **/
@Component
public class HeartBeatEventHandelNode extends AbstractStatuesEventHandleNode<StatusEventContext> {

    @Autowired
    private OnLineCameraPool onLineCameraPool;

    @Override
    public boolean needHandle(int nStatusCode) {
        return nStatusCode == EventStatusCode.EVENT_NVR_IPC_STATUS;
    }

    @Override
    public void handle0(StatusEventContext context, int errorCode, int actionCode) {
        long lUser = context.getIUser();
        System.out.println(String.format("get %d heart beat ", lUser));
        Long time = onLineCameraPool.getTime(lUser);
        if (time != null) {
            onLineCameraPool.putTime(lUser, System.currentTimeMillis());
        }
    }
}
