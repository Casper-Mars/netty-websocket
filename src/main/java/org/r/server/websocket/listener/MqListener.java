package org.r.server.websocket.listener;

/**
 * @author casper
 * @date 20-4-22 下午3:13
 **/
public interface MqListener<T> {


    /**
     * 处理mq的数据
     *
     * @param data mq的数据
     */
    void run(T data);

    /**
     * 获取监听的队列名称
     *
     * @return
     */
    String getQueueName();

}
