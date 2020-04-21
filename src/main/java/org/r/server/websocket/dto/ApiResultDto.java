package org.r.server.websocket.dto;


/**
 * date 20-4-16 下午5:01
 *
 * @author casper
 **/
public class ApiResultDto {


    /**
     * 代号
     */
    private String code;
    /**
     * 信息
     */
    private String msg;
    /**
     * 返回的数据
     */
    private String data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
