package org.r.server.websocket.camera.handle.status;

import lombok.extern.slf4j.Slf4j;
import org.r.server.websocket.camera.enums.EventStatusCode;
import org.springframework.stereotype.Component;

/**
 * date 20-4-10 下午4:47
 *
 * @author casper
 **/
@Component
@Slf4j
public class ConnectEventHandleNode extends AbstractStatuesEventHandleNode<StatusEventContext> {

    @Override
    public boolean needHandle(int nStatusCode) {
        if (nStatusCode == EventStatusCode.EVENT_CONNECTING
                || nStatusCode == EventStatusCode.EVENT_CONNECTOK
                || nStatusCode == EventStatusCode.EVENT_CONNECTFAILED
        ) {
            return true;
        }
        return false;
    }

    @Override
    public void handle0(StatusEventContext context, int errorCode, int actionCode) {
//        log.info("====================================get a call back=================================");
//        log.info(context.getPResponse());
    }

}
