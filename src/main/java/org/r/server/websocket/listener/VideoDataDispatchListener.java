package org.r.server.websocket.listener;

import io.netty.channel.Channel;

/**
 * date 20-4-21 上午9:41
 *
 * @author casper
 **/
public class VideoDataDispatchListener {


    private Channel channel;



    public VideoDataDispatchListener(Channel channel){
        this.channel = channel;
    }

    public void run(byte[] data){

    }




}
