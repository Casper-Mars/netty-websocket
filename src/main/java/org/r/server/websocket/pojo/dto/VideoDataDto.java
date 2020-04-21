package org.r.server.websocket.pojo.dto;

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
    private byte type;

    /**
     * 数据
     */
    private byte[] data;

    /**
     * 是否关键帧
     */
    private byte bIsKey;

    private long dataLen;

    public long getDataLen() {
        return dataLen;
    }

    public void setDataLen(long dataLen) {
        this.dataLen = dataLen;
    }

    public long getHandle() {
        return handle;
    }

    public void setHandle(long handle) {
        this.handle = handle;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte getbIsKey() {
        return bIsKey;
    }

    public void setbIsKey(byte bIsKey) {
        this.bIsKey = bIsKey;
    }
}
