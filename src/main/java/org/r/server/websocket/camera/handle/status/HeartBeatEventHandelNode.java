package org.r.server.websocket.camera.handle.status;

import org.r.server.websocket.camera.enums.EventStatusCode;
import org.springframework.stereotype.Component;

/**
 * date 20-4-26 下午4:40
 *
 * @author casper
 **/
@Component
public class HeartBeatEventHandelNode extends AbstractStatuesEventHandleNode<StatusEventContext> {


    @Override
    public boolean needHandle(int nStatusCode) {
        if(nStatusCode == EventStatusCode.EVENT_NVR_IPC_STATUS){
            return true;
        }
        return false;
    }

    @Override
    public void handle0(StatusEventContext context, int errorCode, int actionCode) {
        long lUser = context.getIUser();
        System.out.println(String.format("get %d heart beat ",lUser));
    }
}
