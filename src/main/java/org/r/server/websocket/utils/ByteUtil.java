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
     * @return
     */
    public static long byteToLong(byte[] buf, int begin) {
        return byteToLong(buf, begin, 8);
    }


    /**
     * 将begin开始的4个字节转化为int数值
     *
     * @param buf   字节数组
     * @param begin 开始下标
     * @return
     */
    public static int byteToInt(byte[] buf, int begin) {
        return (int) byteToLong(buf, begin, 4);
    }

    /**
     * 将begin开始的2个字节转化为int数值
     *
     * @param buf   字节数组
     * @param begin 开始下标
     * @return
     */
    public static short byteToShort(byte[] buf, int begin) {
        return (short) byteToLong(buf, begin, 2);
    }


    /**
     * 将begin开始的len个字节转化为long数值
     *
     * @param buf   字节数组
     * @param begin 开始下标
     * @param len   长度
     * @return
     */
    private static long byteToLong(byte[] buf, int begin, int len) {
        if (len > 8) {
            throw new RuntimeException("len can not great than 8(long length)");
        }
        int target = 0;
        int end = begin + len;
        for (int i = end - 1; i >= begin; i--) {
            target = target << 8;
            target = target | (buf[i] & 0xff);
        }
        return target;
    }


}
