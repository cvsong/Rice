package com.cvsong.study.library.net.exception;

import android.text.TextUtils;

import com.cvsong.study.library.BuildConfig;

import java.io.Serializable;

/**自定义应用网络异常类
 * Created by chenweisong on 2017/10/27.
 */

public class AppHttpException extends Exception implements Serializable {
    private static final long serialVersionUID = 1430833168492550403L;
    private String message;
    private String errorId;//异常的ID,在ExceptionConstants中定义

    public AppHttpException() {
    }

    public AppHttpException(String message) {
        super(message);
    }

    public AppHttpException(String errorID, String message) {
        super(message);
        this.message= message;
        this.errorId= errorID;
    }

    public String getErrorId() {
        return errorId;
    }

    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }


    /**
     * 获取异常信息
     * @return
     */
    public String getErrorInfo() {

        if (BuildConfig.DEBUG && !TextUtils.isEmpty(errorId)) {
            return String.format("异常类型:%s;异常内容:%s", errorId,message);
        } else {
            return message;
        }

    }

    /**
     * 获取异常信息
     */
    @Override
    public String getMessage() {
        return getErrorInfo();
    }
}
