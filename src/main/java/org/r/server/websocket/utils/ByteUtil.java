package org.r.server.websocket.utils;

/**
 * date 2020/4/21 21:10
 *
 * @author casper
 */
public class ByteUtil {

    /**
     * 将begin到end（不包括end）的字节转化为long数值
     *
     * @param buf   字节数组
     * @param begin 开始下标
     * @param len   结束下标
     * @return
     */
    public static long byteToLong(byte[] buf, int begin, int len) {

        long lUserId = 0;
        /*移位接收long数据,最后一个字节对应long的最高8位，以此类推，第一个字节对应long的低8位（大尾端）*/
        int end = begin + len;
        for (int i = end - 1; i >= begin; i--) {
            lUserId = lUserId << 8;
            lUserId = lUserId | (buf[i] & 0xff);
        }
        return lUserId;
    }


}
