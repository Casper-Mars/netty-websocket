package org.r.server.websocket.handle;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.net.InetSocketAddress;

/**
 * date 2020/4/18 13:33
 *
 * @author casper
 */
public class WebSocketHandle extends SimpleChannelInboundHandler<Object> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String hostAddress = inetSocketAddress.getAddress().getHostAddress();
        System.out.println("get a connection : "+hostAddress);


        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object o) throws Exception {
        System.out.println("get msg");
        if(o instanceof TextWebSocketFrame){
            TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame) o;
            String content = textWebSocketFrame.text();
            System.out.println(content);
            ByteBuf byteBuf = Unpooled.directBuffer();
            byteBuf.writeBytes("hello world".getBytes());
            BinaryWebSocketFrame binaryWebSocketFrame = new BinaryWebSocketFrame(byteBuf);
            ctx.channel().writeAndFlush(binaryWebSocketFrame);
        }


    }
}
