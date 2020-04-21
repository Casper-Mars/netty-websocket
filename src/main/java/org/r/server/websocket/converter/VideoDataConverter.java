package org.r.server.websocket.converter;

import org.r.server.websocket.pojo.dto.VideoDataDto;
import org.r.server.websocket.utils.ByteUtil;
import org.r.server.websocket.utils.Constants;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.SimpleMessageConverter;


/**
 * date 2020/4/21 20:28
 *
 * @author casper
 */
public class VideoDataConverter extends SimpleMessageConverter {


    @Override
    public Object fromMessage(Message message) throws MessageConversionException {

        Object header = message.getMessageProperties().getHeader(Constants.VIDEO_DATA_MSG_HEADER);
        if (header == null) {
            return super.fromMessage(message);
        }
        byte[] buf = message.getBody();
        VideoDataDto videoDataDto = new VideoDataDto();
        /*获取前8个字节组成long型的视频流句柄*/
        long handle = ByteUtil.byteToLong(buf, 0);
        videoDataDto.setHandle(handle);
        videoDataDto.setType(buf[9]);
        videoDataDto.setbIsKey(buf[10]);
        long dataLen = ByteUtil.byteToLong(buf, 11);
        videoDataDto.setDataLen(dataLen);
        byte[] data = new byte[(int) dataLen];
        System.arraycopy(buf,19,data,0, (int) dataLen);
        videoDataDto.setData(data);
        return videoDataDto;
    }

    

}
