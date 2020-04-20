package org.r.server.websocket.dto;

/**
 * date 20-4-20 下午4:15
 *
 * @author casper
 **/
public class VideoDataDto {

    /**
     * 摄像机句柄
     */
    private long handle;

    /**
     * 数据类型
     */
    private int type;

    /**
     * 数据
     */
    private String data;

    /**
     * 是否关键帧
     */
    private int bIsKey;


    public long getHandle() {
        return handle;
    }

    public void setHandle(long handle) {
        this.handle = handle;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getbIsKey() {
        return bIsKey;
    }

    public void setbIsKey(int bIsKey) {
        this.bIsKey = bIsKey;
    }
}
