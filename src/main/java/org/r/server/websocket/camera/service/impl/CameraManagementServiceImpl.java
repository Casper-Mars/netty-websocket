package org.r.server.websocket.camera.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import org.r.server.websocket.pojo.dto.ApiResultDto;
import org.r.server.websocket.camera.service.CameraManagementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * date 20-4-21 上午10:41
 *
 * @author casper
 **/
@Service
public class CameraManagementServiceImpl implements CameraManagementService {

    @Value("${camera-manager.ip}")
    private String managementIp;
    @Value("${camera-manager.port}")
    private String managementPort;

    /**
     * 构建Get请求的url
     *
     * @param uri    资源路径
     * @param params 参数
     * @return
     */
    private String buildGetRequestUrl(String uri, Map<String, String> params) {

        StringBuilder sb = new StringBuilder();
        sb.append("http://")
                .append(managementIp)
                .append(":")
                .append(managementPort)
                .append(uri);
        if (CollectionUtils.isEmpty(params)) {
            return sb.toString();
        }
        sb.append("?");
        params.forEach((k, v) -> {
            sb.append(k).append("=").append(v).append("&");
        });
        String result = sb.toString();
        if (result.endsWith("&")) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    private ApiResultDto doGet(String uri, Map<String, String> params) {
        String s = buildGetRequestUrl(uri, params);
        String s1 = HttpUtil.get(s);
        return JSONObject.parseObject(s1, ApiResultDto.class);
    }

    @Override
    public long openLiveStream(String ip, String username, String password, long lUserId) {

        Map<String, String> params = new HashMap<>();
        params.put("ip", ip);
        params.put("username", username);
        params.put("password", password);
        params.put("lUserID", String.valueOf(lUserId));
        params.put("port", String.valueOf(554));
        ApiResultDto apiResultDto = doGet("/live/start", params);
        if ("200".equals(apiResultDto.getCode())) {
            return Long.valueOf(apiResultDto.getData());
        }
        return -1;
    }

    /**
     * 停止实时流
     *
     * @param streamHandle 打开实时流后返回的流句柄
     * @return
     */
    @Override
    public long stopLiveStream(long streamHandle) {
        Map<String, String> params = new HashMap<>();
        params.put("handle", String.valueOf(streamHandle));
        ApiResultDto apiResultDto = doGet("/live/stop", params);
        if ("200".equals(apiResultDto.getCode())) {
            return 0;
        }
        throw new RuntimeException(apiResultDto.getMsg());
    }

    /**
     * 登录摄像头
     *
     * @param ip       摄像头ip
     * @param port     摄像头端口，默认8091
     * @param username 登录的用户名
     * @param password 登录的密码
     * @return 摄像头句柄, 登录成功返回非0正数，失败返回-1
     */
    @Override
    public long login(String ip, String port, String username, String password) {
        if ("127.0.0.1".equals(ip)) {
            throw new RuntimeException("ip 不能127.0.0.1");
        }
        long handle = -1;
        String loginUri = "/login";
        /*username=admin&password=123456&ip=192.168.20.100&port=8091*/
        Map<String, String> params = new HashMap<>(8);
        params.put("username", username);
        params.put("password", password);
        params.put("ip", ip);
        if (StringUtils.isEmpty(port)) {
            params.put("port", "8091");
        } else {
            params.put("port", port);
        }
        try {
            ApiResultDto resultDto = doGet(loginUri, params);
            if ("200".equals(resultDto.getCode())) {
                handle = Long.valueOf(resultDto.getData());
            }
        } catch (Exception e) {
            e.printStackTrace();
            handle = -1;
        }
        return handle;
    }


}
