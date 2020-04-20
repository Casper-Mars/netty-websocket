package org.r.server.websocket.service;

import org.r.server.websocket.pojo.bo.CameraInfoBo;

/**
 * date 2020/4/20 20:29
 *
 * @author casper
 */
public interface CameraService {

    /**
     * 根据id查询摄像机信息
     *
     * @param id 摄像机id
     * @return 摄像机信息
     */
    CameraInfoBo getCameraInfoById(Long id);


}
