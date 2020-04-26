package org.r.server.websocket.camera.pojo.dto;

import lombok.Data;

/**
 * date 20-4-15 下午6:04
 *
 * @author casper
 **/
@Data
public class StatusEventDto {

    private long lUser;

    private long nStateCode;

    private String pResponse;

}
