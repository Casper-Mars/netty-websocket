package org.r.server.websocket.camera.enums;

/**
 * date 20-4-10 上午9:18
 *
 * @author casper
 **/
public class CmdCode {

    /**
     * al命令开始号码
     */
    private static int CMD_AI_CONFIG_BASE = 750;

    /**
     * 人脸抓拍参数配置
     */
    public static int CMD_SET_AI_CONFIG = CMD_AI_CONFIG_BASE + 1;

    /**
     * 读取用户特征列表
     */
    public static int CMD_GET_USER_FACELIST = CMD_AI_CONFIG_BASE + 2;

    /**
     * 注册一个人脸特征
     */
    public static int CMD_SET_USER_FACE = CMD_AI_CONFIG_BASE + 3;

    /**
     * 删除指定人脸特征
     */
    public static int CMD_DELETE_USER_FACE = CMD_AI_CONFIG_BASE + 4;

    /**
     * 读取人脸识别参数
     */
    public static int CMD_GET_AI_FACEREG = CMD_AI_CONFIG_BASE + 6;

    /**
     * 修改人脸识别参数
     */
    public static int CMD_SET_AI_FACEREG = CMD_AI_CONFIG_BASE + 7;


}
