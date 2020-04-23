package org.r.server.websocket.service.impl;

import org.r.server.websocket.dao.TeachFaceMachineDao;
import org.r.server.websocket.entity.TeachFaceMachine;
import org.r.server.websocket.pojo.bo.CameraInfoBo;
import org.r.server.websocket.pool.TopicExchangePool;
import org.r.server.websocket.service.CameraManagementService;
import org.r.server.websocket.service.CameraService;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    /**
     * 根据id查询摄像机信息
     *
     * @param id 摄像机id
     * @return 摄像机信息
     */
    @Override
    public CameraInfoBo getCameraInfoById(Long id) {

        Optional<TeachFaceMachine> camera = teachFaceMachineDao.findById(id.intValue());
        if (camera.isPresent()) {
            TeachFaceMachine teachFaceMachine = camera.get();
            CameraInfoBo target = new CameraInfoBo();
            target.setId(id);
            target.setHandle(teachFaceMachine.getCameraHandle());
            target.setIp(teachFaceMachine.getIp());
            target.setUsername(teachFaceMachine.getCameraUsername());
            target.setPassword(teachFaceMachine.getCameraPassword());
            target.setStatue(teachFaceMachine.getCameraStatues());
            return target;
        } else {
            throw new RuntimeException("摄像机不存在");
        }
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
}
