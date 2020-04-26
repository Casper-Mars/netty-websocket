package org.r.server.websocket.camera.enums;

/**
 * date 20-4-26 下午1:51
 *
 * @author casper
 **/
public enum CameraStatusEnum {
    ON(1),
    OFF(0),
    LINKING(2)
    ;
    private int code;

    CameraStatusEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
