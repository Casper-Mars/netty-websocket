package org.r.server.websocket.handle;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.impl.AMQImpl;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpRequest;
import org.r.server.websocket.listener.VideoDataDispatchListener;
import org.r.server.websocket.pojo.bo.CameraInfoBo;
import org.r.server.websocket.pool.RoutingKeyPool;
import org.r.server.websocket.pool.TopicExchangePool;
import org.r.server.websocket.utils.SpringUtil;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * date 2020/4/20 20:45
 *
 * @author casper
 */
public class CameraRegistryHandle extends HttpHandshakeHandle {


    public CameraRegistryHandle(String path) {
        super(path);
    }


    @Override
    protected void afterHandShake(FullHttpRequest req, Channel channel) {
        /*获取查询参数*/
        Map<String, String> params = getParams(req.uri());
        if (CollectionUtils.isEmpty(params)) {
            return;
        }
        CameraInfoBo cameraInfoBo = BeanUtil.fillBeanWithMap(params, new CameraInfoBo(), true);
        /*获取exchange*/
        TopicExchange exchange = getExchange(cameraInfoBo.getId());
        /*创建新的queue并绑定routingKey*/
        String queueName = params.get("panelId");
        buildQueue(queueName, exchange);
        /*注册新的queue监听器*/
        setListener(queueName, channel);
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


    private TopicExchange getExchange(Long id) {
        TopicExchangePool topicExchangePool = SpringUtil.getBean("topicExchangePool");
        TopicExchange topicExchange = topicExchangePool.get(id);
        /*如果当前的摄像机没有建立exchange，则先建立*/
        if (topicExchange == null) {
            RabbitAdmin rabbitAdmin = SpringUtil.getBean("rabbitAdmin");
            topicExchange = new TopicExchange("h264-camera-" + id, true, false);
            /*重复的exchange只会创建一次*/
            rabbitAdmin.declareExchange(topicExchange);
            topicExchangePool.put(id, topicExchange);
        }
        return topicExchange;
    }

    private void buildQueue(String panelMachineId, TopicExchange exchange) {

        Queue queue = new Queue(panelMachineId, true, false, false);
        RabbitAdmin rabbitAdmin = SpringUtil.getBean("rabbitAdmin");
        rabbitAdmin.declareQueue(queue);
        Binding binding = BindingBuilder.bind(queue).to(exchange).with("*");
        /*重复的queue只会创建一次*/
        rabbitAdmin.declareBinding(binding);
    }

    private void setListener(String queueName, Channel channel) {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(new VideoDataDispatchListener(channel));
        messageListenerAdapter.addQueueOrTagToMethodName(queueName, "run");
        SimpleMessageListenerContainer simpleMessageListenerContainer = SpringUtil.getBean("simpleMessageListenerContainer");
        simpleMessageListenerContainer.addQueueNames(queueName);
        simpleMessageListenerContainer.setMessageListener(messageListenerAdapter);
    }


}
