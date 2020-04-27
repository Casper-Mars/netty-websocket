package org.r.server.websocket.camera.service.impl;

import org.r.server.websocket.camera.dao.TeachFaceMachineDao;
import org.r.server.websocket.camera.entity.TeachFaceMachine;
import org.r.server.websocket.camera.enums.CameraStatusEnum;
import org.r.server.websocket.camera.service.CameraManagementService;
import org.r.server.websocket.camera.service.CameraService;
import org.r.server.websocket.pojo.bo.CameraInfoBo;
import org.r.server.websocket.pool.OnLineCameraPool;
import org.r.server.websocket.pool.TopicExchangePool;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * date 20-4-21 上午10:30
 *
 * @author casper
 **/
@Service("cameraService")
public class CameraServiceIpl implements CameraService {


    @Autowired
    private CameraManagementService cameraManagementService;
    @Autowired
    private TopicExchangePool topicExchangePool;
    @Autowired
    private TeachFaceMachineDao teachFaceMachineDao;
    @Autowired
    private OnLineCameraPool onLineCameraPool;


    /**
     * 根据id查询摄像机信息
     * 如果摄像机没有登录，则先进行登录
     *
     * @param id 摄像机id
     * @return 摄像机信息
     */
    @Override
    public CameraInfoBo getCameraInfoById(Long id) {

        Optional<TeachFaceMachine> camera = teachFaceMachineDao.findById(id.intValue());
        if (camera.isPresent()) {
            TeachFaceMachine teachFaceMachine = camera.get();
            return CameraInfoBo.build(teachFaceMachine);
        } else {
            throw new RuntimeException("摄像机不存在");
        }
    }

    /**
     * 根据id查询摄像机信息
     *
     * @param id 摄像机id
     * @return 摄像机信息
     */
    @Override
    public TeachFaceMachine getFaceMachineById(Long id) {
        OnLineCameraPool.lock.readLock().lock();
        TeachFaceMachine result = null;
        try {
            Optional<TeachFaceMachine> faceMachine = teachFaceMachineDao.findById(id.intValue());
            if (faceMachine.isPresent()) {
                result = faceMachine.get();
            }
        } finally {
            OnLineCameraPool.lock.readLock().unlock();
        }
        return result;
    }

    /**
     * 开启摄像机实时视频流
     *
     * @param id 摄像机id
     */
    @Override
    public void registryCameraStream(Long id, TopicExchange exchange) {
        registryCameraStream(getCameraInfoById(id), exchange);
    }

    /**
     * 注册摄像机
     *
     * @param cameraInfoBo 摄像机信息
     */
    @Override
    public void registryCameraStream(CameraInfoBo cameraInfoBo, TopicExchange exchange) {

        /*
         * http://localhost:18080/live/start?
         * username=admin&
         * password=123456&
         * ip=192.168.20.100&
         * port=554&
         * lUserID=19281952
         * */
        /*判断摄像机是否在线*/
        if (cameraInfoBo.getStatue() != 1) {
            throw new RuntimeException("摄像机不在线");
        }
        /*开启实时视频流*/
        long l = cameraManagementService.openLiveStream(cameraInfoBo.getIp(), cameraInfoBo.getUsername(), cameraInfoBo.getPassword(), cameraInfoBo.getHandle());
        if (l == -1) {
            throw new RuntimeException("can not open live stream of camera:" + cameraInfoBo.getIp() + ",login first");
        }
        topicExchangePool.putHandle(l, exchange);
    }

    /**
     * 登录摄像机
     *
     * @param id 摄像机id信息
     * @return
     */
    @Override
    public CameraInfoBo loginCamera(Long id) {
        TeachFaceMachine faceMachine = getFaceMachineById(id);
        if (faceMachine == null) {
            throw new RuntimeException("摄像机不存在");
        }
        if (faceMachine.getCameraStatues() == CameraStatusEnum.OFF.getCode()) {
            synchronized (this) {
                faceMachine = getFaceMachineById(id);
                if (faceMachine.getCameraStatues() == CameraStatusEnum.OFF.getCode()) {
                    long handle = cameraManagementService.login(faceMachine.getIp(), "8091", faceMachine.getCameraUsername(), faceMachine.getCameraPassword());
                    faceMachine.setCameraHandle(handle);
                    faceMachine.setUpdateTime(new Date());
                    faceMachine.setCameraStatues(CameraStatusEnum.ON.getCode());
                    teachFaceMachineDao.save(faceMachine);
                    onLineCameraPool.putTime(handle, System.currentTimeMillis());
                }
            }
        }

        return CameraInfoBo.build(faceMachine);
    }
}
