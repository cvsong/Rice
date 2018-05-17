package com.cvsong.study.library.net.entity;

import java.io.Serializable;

/**
 * 响应结果最外层封装类
 */
public class Result implements Serializable {

    /**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = 4663352187347003272L;


    /**
     * status : 0
     * code : 0
     * message : 登录成功
     * data : {"quanxian":[{"id":62,"name":"采购接收权限","status":0,"resource_type":1},{"id":63,"name":"入库上架权限","status":0,"resource_type":1},{"id":64,"name":"出库下架权限","status":0,"resource_type":1},{"id":65,"name":"车间发料权限","status":0,"resource_type":1},{"id":66,"name":"车间退料权限","status":0,"resource_type":1},{"id":67,"name":"车间盘点权限","status":0,"resource_type":1}]}
     */

    private String status;
    private String token;
    private String code;
    private String message;
    private Object data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
