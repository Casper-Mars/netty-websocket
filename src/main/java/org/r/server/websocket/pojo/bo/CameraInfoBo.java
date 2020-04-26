package org.r.server.websocket.pojo.bo;

import org.r.server.websocket.camera.entity.TeachFaceMachine;

/**
 * date 2020/4/20 20:31
 *
 * @author casper
 */
public class CameraInfoBo {

    /**
     * 摄像机id
     */
    private Long id;

    /**
     * 摄像机ip
     */
    private String ip;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 句柄
     */
    private Long handle;

    /**
     * 状态
     */
    private int statue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getHandle() {
        return handle;
    }

    public void setHandle(Long handle) {
        this.handle = handle;
    }

    public int getStatue() {
        return statue;
    }

    public void setStatue(int statue) {
        this.statue = statue;
    }

    public static  CameraInfoBo build(TeachFaceMachine teachFaceMachine){

        CameraInfoBo target = new CameraInfoBo();
        target.setId((long) teachFaceMachine.getId());
        target.setHandle(teachFaceMachine.getCameraHandle());
        target.setIp(teachFaceMachine.getIp());
        target.setUsername(teachFaceMachine.getCameraUsername());
        target.setPassword(teachFaceMachine.getCameraPassword());
        target.setStatue(teachFaceMachine.getCameraStatues());
        return target;
    }



}
