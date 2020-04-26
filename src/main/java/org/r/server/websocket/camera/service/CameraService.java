package org.r.server.websocket.camera.service;

import org.r.server.websocket.camera.entity.TeachFaceMachine;
import org.r.server.websocket.pojo.bo.CameraInfoBo;
import org.springframework.amqp.core.TopicExchange;

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

    /**
     * 根据id查询摄像机信息
     *
     * @param id 摄像机id
     * @return 摄像机信息
     */
    TeachFaceMachine getFaceMachineById(Long id);


    /**
     * 注册摄像机
     *
     * @param id 摄像机id
     */
    void registryCameraStream(Long id, TopicExchange exchange);

    /**
     * 注册摄像机
     *
     * @param cameraInfoBo 摄像机信息
     */
    void registryCameraStream(CameraInfoBo cameraInfoBo, TopicExchange exchange);


    /**
     * 登录摄像机
     *
     * @param id 摄像机id信息
     * @return
     */
    CameraInfoBo loginCamera(Long id);


}
