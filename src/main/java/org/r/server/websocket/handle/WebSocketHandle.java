package org.r.server.websocket.handle;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * date 2020/4/18 13:33
 *
 * @author casper
 */
public class WebSocketHandle extends SimpleChannelInboundHandler<Object> {


    private final String path;
    private WebSocketServerHandshaker handshaker;

    public WebSocketHandle(String path){
        this.path = path;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        ctx.flush();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String hostAddress = inetSocketAddress.getAddress().getHostAddress();
        System.out.println("get a connection : "+hostAddress);


        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object o) throws Exception {
        if(o instanceof FullHttpRequest){
            handleHttpReq(ctx, (FullHttpRequest) o);
        }else {
            handleWebSocketReq(ctx, (WebSocketFrame) o);
        }
    }


    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
        if (res.status().code() != HttpResponseStatus.OK.code()) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            HttpUtil.setContentLength(res, res.content().readableBytes());
        }
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpUtil.isKeepAlive(req) || res.status().code() != HttpResponseStatus.OK.code()) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private void handleHttpReq(ChannelHandlerContext ctx, FullHttpRequest req){
        String host = req.headers().get(HttpHeaderNames.HOST);
        String uri = req.uri();
        /*判断是否要处理的路径*/
        if (!uri.startsWith(path)) {
            return;
        }
        // Handle a bad request.
        if (!req.decoderResult().isSuccess()) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        // Allow only GET methods.
        if (req.method() != HttpMethod.GET) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN));
            return;
        }
        // Handshake
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://" + host + path, null, true, 5 * 1024 * 1024);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
        }
        afterHandShake(req,ctx.channel());
    }

    protected void afterHandShake(FullHttpRequest req, Channel channel) {

    }

    private void handleWebSocketReq(ChannelHandlerContext ctx, WebSocketFrame o){
        System.out.println("get msg");
        if(o instanceof TextWebSocketFrame){
            TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame) o;
            ctx.channel().writeAndFlush(textWebSocketFrame.retain());
        }else if(o instanceof CloseWebSocketFrame){
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) o.retain());
        }
    }


}
