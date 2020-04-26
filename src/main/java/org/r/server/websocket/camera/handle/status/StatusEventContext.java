package org.r.server.websocket.camera.handle.status;

import lombok.Data;
import lombok.ToString;
import org.r.server.websocket.camera.handle.EventChainContext;

/**
 * date 20-4-10 下午4:35
 *
 * @author casper
 **/
@Data
@ToString
public class StatusEventContext implements EventChainContext {


    /**
     * 设备id
     */
    private long IUser;
    /**
     * 事件类型
     */
    private long nStateCode;
    /**
     * 数据体
     */
    private String pResponse;

    public StatusEventContext() {
    }

    public StatusEventContext(long IUser, long nStateCode, String pResponse) {
        this.IUser = IUser;
        this.nStateCode = nStateCode;
        this.pResponse = pResponse;
    }

}
