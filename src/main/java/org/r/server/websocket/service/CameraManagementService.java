package org.r.server.websocket.service;

/**
 * @author casper
 * @date 20-4-21 上午10:39
 **/
public interface CameraManagementService {


    /**
     * 打开摄像机的实时流
     *
     * @param ip       摄像机ip
     * @param username 登录用户名
     * @param password 登录密码
     * @param lUserId  登录成功后的handle
     * @return
     */
    long openLiveStream(String ip, String username, String password, long lUserId);

    /**
     * 停止实时流
     *
     * @param streamHandle 打开实时流后返回的流句柄
     * @return
     */
    long stopLiveStream(long streamHandle);


}
