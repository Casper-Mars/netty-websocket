package org.r.server.websocket.listener;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.r.server.websocket.pojo.dto.VideoDataDto;
import org.r.server.websocket.service.QueueService;
import org.r.server.websocket.utils.ByteUtil;
import org.r.server.websocket.utils.SpringUtil;

/**
 * date 20-4-21 上午9:41
 *
 * @author casper
 **/
public class VideoDataDispatchListener implements MqListener<byte[]> {


    private Channel channel;
    private String queueName;

    public VideoDataDispatchListener(Channel channel, String queueName) {
        this.channel = channel;
        this.queueName = queueName;
    }

    public void run(byte[] data) {

        /*如果通道被关闭了，意味着前端的websocket已经关闭，则移除队列和对应的监听*/
        if (!channel.isOpen()) {
            shutdown();
            return;
        }
        /*转化出视频帧数据对象*/
        VideoDataDto videoDataDto = convertData(data);
        if (videoDataDto == null) {
            return;
        }
        /*
         * 判断数据类型
         * 如果是2（视频大小信息）则推送字符串数据
         * 如果是0（视频帧数据）先进行后16字节的处理，再返回字节数据
         * */
        WebSocketFrame result = null;
        switch (videoDataDto.getType()) {
            case 2:
//                result = new TextWebSocketFrame(new String(videoDataDto.getData()));
                break;
            case 0:
                ByteBuf byteBuf = Unpooled.directBuffer();
                byteBuf.writeBytes(processBuf(videoDataDto.getData()));
                result = new BinaryWebSocketFrame(byteBuf);
                break;
        }
        if (result != null) {
            channel.writeAndFlush(result);
        }
    }

    /**
     * 获取监听的队列名称
     *
     * @return
     */
    @Override
    public String getQueueName() {
        return queueName;
    }

    private VideoDataDto convertData(byte[] buf) {
        VideoDataDto videoDataDto = new VideoDataDto();
        int size = 0;
        /*获取前8个字节组成long型的视频流句柄*/
        long handle = ByteUtil.byteToLong(buf, 0);
        size += 8;
        videoDataDto.setHandle(handle);
        videoDataDto.setType(buf[8]);
        size += 1;
        videoDataDto.setbIsKey(buf[9]);
        size += 1;
        long dataLen = ByteUtil.byteToLong(buf, 10);
        size += 8;
        videoDataDto.setDataLen(dataLen);
        if ((size + dataLen) != buf.length) {
            System.out.println("buf error! missing some byte!!!");
            return null;
        }
        byte[] data = new byte[(int) dataLen];
        System.arraycopy(buf, 18, data, 0, (int) dataLen);
        videoDataDto.setData(data);
        return videoDataDto;
    }


    /**
     * 判断帧数据的最后16个字节的情况
     *
     * @param buf 帧数据
     * @return
     */
    private byte[] processBuf(byte[] buf) {

        /*
         * 最后16个字节转化为如下的结构体，如果flag标记为0x1a2b3c4d，则此16字节需要丢弃
         * typedef struct
         * {
         *   unsigned int flag;			//标记，固定为0x1a2b3c4d
         *   unsigned int data;			//最后面字节，即data & 0xff如果值不为0，则为当前实际帧率
         *   unsigned int frame_index;		//当前帧ID
         *   unsigned int keyframe_index;	//当前帧依赖的关键帧ID
         * }VIDEO_FRAME_HEADER;
         * */
        /*
         * 只要判断从最后往前数16个字节的前4个字节是否是固定的0x1a2b3c4d标记即可
         * */
        int beginPos = buf.length - 1 - 16;
        int flag = ByteUtil.byteToInt(buf, beginPos);
        if (flag == 0x1a2b3c4d) {
            /*去除后16位*/
            int len = buf.length - 16;
            byte[] result = new byte[len];
            System.arraycopy(buf, beginPos, result, 0, len);
            return result;
        }
        return buf;
    }


    private void shutdown() {
        QueueService queueService = SpringUtil.getBean("queueService");
        queueService.removeQueueAndListener(getQueueName());

    }


}
