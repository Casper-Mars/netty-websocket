package org.r.server.websocket.camera.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * date 20-4-23 上午10:52
 *
 * @author casper
 **/
@Entity(name = "teach_face_machine")
public class TeachFaceMachine {

    /**
     * 主键id
     */
    @Id
    private int id;

    /**
     * 创建时间
     */
    @Column
    private Date createTime;

    /**
     * 更新时间
     */
    @Column
    private Date updateTime;

    /**
     * 人脸识别机名称
     */
    @Column
    private String name;

    /**
     * 人脸识别机编号
     */
    @Column
    private String code;

    /**
     * 人脸识别机IP
     */
    @Column
    private String ip;

    /**
     * 备注
     */
    @Column
    private String remark;

    /**
     * 摄像机句柄
     */
    @Column
    private long cameraHandle;

    /**
     * 摄像机状态
     */
    @Column
    private int cameraStatues;

    /**
     * 摄像机登录端口，默认8091
     */
    @Column
    private int cameraPort;

    /**
     * 摄像机登录用户名
     */
    @Column
    private String cameraUsername;

    /**
     * 摄像机登录密码
     */
    @Column
    private String cameraPassword;

    /**
     * 是否已被绑定
     */
    @Column
    private boolean isBand;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getCameraHandle() {
        return cameraHandle;
    }

    public void setCameraHandle(long cameraHandle) {
        this.cameraHandle = cameraHandle;
    }

    public int getCameraStatues() {
        return cameraStatues;
    }

    public void setCameraStatues(int cameraStatues) {
        this.cameraStatues = cameraStatues;
    }

    public int getCameraPort() {
        return cameraPort;
    }

    public void setCameraPort(int cameraPort) {
        this.cameraPort = cameraPort;
    }

    public String getCameraUsername() {
        return cameraUsername;
    }

    public void setCameraUsername(String cameraUsername) {
        this.cameraUsername = cameraUsername;
    }

    public String getCameraPassword() {
        return cameraPassword;
    }

    public void setCameraPassword(String cameraPassword) {
        this.cameraPassword = cameraPassword;
    }

    public boolean isBand() {
        return isBand;
    }

    public void setBand(boolean band) {
        isBand = band;
    }
}
