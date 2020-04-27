package org.r.server.websocket.task;

import org.r.server.websocket.camera.dao.TeachFaceMachineDao;
import org.r.server.websocket.camera.entity.TeachFaceMachine;
import org.r.server.websocket.camera.enums.CameraStatusEnum;
import org.r.server.websocket.pool.OnLineCameraPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * date 2020/4/26 21:18
 *
 * @author casper
 */
@Component
public class HeartBeatTask implements Runnable {

    @Autowired
    private OnLineCameraPool onLineCameraPool;
    @Autowired
    private TeachFaceMachineDao teachFaceMachineDao;
    @Value("${camera-manager.heart-beat.duration}")
    private Long duration;


    @Override
    public void run() {
        List<Long> lostCameraHandle = onLineCameraPool.getHandelTimeOverThan(duration);
        if(CollectionUtils.isEmpty(lostCameraHandle)){
            return;
        }
        List<TeachFaceMachine> offlineCameras = teachFaceMachineDao.findByCameraHandleIn(lostCameraHandle);
        for (TeachFaceMachine offlineCamera : offlineCameras) {
            offlineCamera.setCameraStatues(CameraStatusEnum.OFF.getCode());
            offlineCamera.setUpdateTime(new Date());
            offlineCamera.setCameraHandle(-1);
        }
        OnLineCameraPool.lock.writeLock().lock();
        try {
            if(CollectionUtils.isEmpty(offlineCameras)){
                teachFaceMachineDao.saveAll(offlineCameras);
            }
            onLineCameraPool.removeBathc(lostCameraHandle);
        } finally {
            OnLineCameraPool.lock.writeLock().unlock();
        }
    }
}
