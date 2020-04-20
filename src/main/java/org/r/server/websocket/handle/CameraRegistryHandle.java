package org.r.server.websocket.handle;

import cn.hutool.core.bean.BeanUtil;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpRequest;
import org.r.server.websocket.pojo.bo.CameraInfoBo;
import org.r.server.websocket.pool.RoutingKeyPool;
import org.r.server.websocket.utils.SpringUtil;
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
    protected void afterHandShake(FullHttpRequest req, Channel channel ) {
        /*获取查询参数*/
        Map<String, String> params = getParams(req.uri());
        if (CollectionUtils.isEmpty(params)) {
            return;
        }
        CameraInfoBo cameraInfoBo = BeanUtil.fillBeanWithMap(params, new CameraInfoBo(), true);
        /*获取routingKey*/
        String routingKey = getRoutingKey(cameraInfoBo.getId());
        /*创建新的queue并绑定routingKey*/

        /*注册新的queue监听器*/

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

    private String getRoutingKey(Long id) {
        RoutingKeyPool routingKeyPool = SpringUtil.getBean("routingKeyPool");
        String routingKey = routingKeyPool.getRoutingKey(id);
        /*如果routingKey 为空，则新建一个*/
        if (StringUtils.isEmpty(routingKey)) {
            routingKey = "video_" + id + "_";
            routingKeyPool.put(routingKey, id);
        }
        return routingKey;
    }




}
