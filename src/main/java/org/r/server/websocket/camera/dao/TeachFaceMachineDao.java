package org.r.server.websocket.camera.dao;

import org.r.server.websocket.camera.entity.TeachFaceMachine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author casper
 * @date 20-4-23 上午11:03
 **/
public interface TeachFaceMachineDao extends JpaRepository<TeachFaceMachine, Integer> {


    /**
     * 根据id集合查询多条数据
     *
     * @param ids id集合
     * @return 球机集合
     */
    List<TeachFaceMachine> findByIdIn(List<Long> ids);

    /**
     * 根据句柄集合查询多条数据
     *
     * @param handles 句柄集合
     * @return 球机集合
     */
    List<TeachFaceMachine> findByCameraHandleIn(List<Long> handles);


}
