package org.r.server.websocket.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.r.server.websocket.handle.netty.CameraRegistryHandle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * date 2020/4/18 13:16
 *
 * @author casper
 */
@Component
public class WebSocketServer {


    @Value("${server.port}")
    private int port;


    public void start() {

        /*调度者线程*/
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        /*工作者线程*/
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        /*netty 启动类*/
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                /*处理日志，netty自带的工具*/
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.SO_BACKLOG, 1024 * 1024 * 10)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        /*添加http编解码器*/
                        pipeline.addLast("http-codec", new HttpServerCodec());
                        /*以块的方式进行读写*/
                        pipeline.addLast("http-chunked", new ChunkedWriteHandler());
                        //netty是基于分段请求的，HttpObjectAggregator的作用是将请求分段再聚合,参数是聚合字节的最大长度
                        pipeline.addLast("aggregator", new HttpObjectAggregator(1024 * 1024 * 1024));
//                        pipeline.addLast(cameraRegistryHandle);
                        //这个是websocket的handler，是netty提供的，也可以自定义，建议就用默认的
//                        pipeline.addLast(new WebSocketServerProtocolHandler("/h264", null, true, 65535));
                        //自定义的handler，处理服务端传来的消息
                        pipeline.addLast(new CameraRegistryHandle());
                    }
                });
        try {
            ChannelFuture channelFuture = bootstrap.bind(new InetSocketAddress(port)).sync();
            System.out.println("websocket server started at port: "+port);
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
