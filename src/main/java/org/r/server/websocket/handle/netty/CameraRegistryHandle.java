package org.r.server.websocket.handle.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.r.server.websocket.listener.VideoDataDispatchListener;
import org.r.server.websocket.pojo.bo.CameraInfoBo;
import org.r.server.websocket.pool.TopicExchangePool;
import org.r.server.websocket.camera.service.CameraService;
import org.r.server.websocket.service.QueueService;
import org.r.server.websocket.utils.SpringUtil;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * date 2020/4/20 20:45
 *
 * @author casper
 */
public class CameraRegistryHandle extends WebSocketHandle {

    private CameraService cameraService;
    private RabbitAdmin rabbitAdmin;
    private TopicExchangePool topicExchangePool;
    private QueueService queueService;


    public CameraRegistryHandle() {
        super("/ipc");
        cameraService = SpringUtil.getBean("cameraService");
        rabbitAdmin = SpringUtil.getBean("rabbitAdmin");
        topicExchangePool = SpringUtil.getBean("topicExchangePool");
        queueService = SpringUtil.getBean("queueService");
    }


    /**
     * @param req
     * @param channel
     */
    @Override
    protected void afterHandShake(FullHttpRequest req, Channel channel) {
        /*uri:/ipc?id=1&panelId=1231312*/
        /*获取查询参数*/
        Map<String, String> params = getParams(req.uri());
        if (CollectionUtils.isEmpty(params)) {
            return;
        }
        /*登录设备*/
        CameraInfoBo cameraInfoBo = cameraService.loginCamera(Long.valueOf(params.get("id")));
        if (cameraInfoBo == null) {
            return;
        }
        /*获取exchange*/
        TopicExchange exchange = getExchange(cameraInfoBo);
        /*panelId 拼接一个随机数作为唯一的队列名称*/
        String queueName = params.get("panelId")+"-"+System.currentTimeMillis();

        /*创建新的queue并绑定routingKey,然后添加监听器*/
        queueService.bindNewQueueAndListen(queueName, exchange, new VideoDataDispatchListener(channel, queueName));
    }


    /**
     * 获取餐宿
     *
     * @param uri 请求资源的地址
     * @return
     */
    private Map<String, String> getParams(String uri) {
        /*分割出查询参数字符串*/
        int beginIndex = uri.indexOf('?');
        if (beginIndex == -1) {
            return Collections.emptyMap();
        }
        String paramStr = uri.substring(beginIndex + 1);
        /*分割出每个参数对*/
        String[] paramPair = paramStr.split("&");
        Map<String, String> result = new HashMap<>((int) (paramPair.length * 0.75) + 1);
        for (String pair : paramPair) {
            String[] keyValue = pair.split("=");
            if (keyValue.length > 1) {
                result.put(keyValue[0], keyValue[1]);
            } else {
                result.put(keyValue[0], "");
            }
        }
        return result;
    }


    private TopicExchange getExchange(CameraInfoBo cameraInfoBo) {
        Long id = cameraInfoBo.getId();
        TopicExchange topicExchange = topicExchangePool.getById(id);
        /*如果当前的摄像机没有建立exchange，则先建立*/
        if (topicExchange == null) {
            /*建立exchange*/
            topicExchange = new TopicExchange("h264-camera-" + id, true, false);
            /*重复的exchange只会创建一次*/
            rabbitAdmin.declareExchange(topicExchange);
            topicExchangePool.putId(id, topicExchange);
            cameraService.registryCameraStream(cameraInfoBo, topicExchange);
        }
        return topicExchange;
    }

    @Override
    protected void closeWebSocket(ChannelHandlerContext ctx, WebSocketFrame o) {
        super.closeWebSocket(ctx, o);


    }
}
