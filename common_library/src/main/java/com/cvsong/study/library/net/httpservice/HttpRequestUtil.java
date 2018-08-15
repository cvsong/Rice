package com.cvsong.study.library.net.httpservice;

import android.app.Activity;

import com.cvsong.study.library.net.interfaces.IHttpRequest;
import com.cvsong.study.library.net.interfaces.IHttpResponseCallBack;
import com.cvsong.study.library.net.interfaces.IHttpUrlManage;


/**
 * Http网络请求工具类
 * Created by chenweisong on 2017/0/10.
 */

public class HttpRequestUtil implements IHttpRequest {

    private volatile static HttpRequestUtil httpRequestUtil;
    IHttpRequest httpRequest;

    private HttpRequestUtil() {
        httpRequest = OkHttpRequestManage.getInstance();
    }

    public static HttpRequestUtil getInstance() {
        if (httpRequestUtil == null) {
            synchronized (HttpRequestUtil.class) {
                if (httpRequestUtil == null) {
                    httpRequestUtil = new HttpRequestUtil();
                }

            }

        }
        return httpRequestUtil;
    }

    /**
     * 设置Http请求管理类
     *
     * @param httpRequest
     */
    public void setHttpRequestManage(IHttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    /**
     * 同步POST请求
     */
    @Override
    public void postSynRequest(Activity activity, IHttpUrlManage httpUrlManage, Object object, Class clazz, IHttpResponseCallBack callBack) {
        httpRequest.postSynRequest(activity, httpUrlManage, object, clazz, callBack);
    }

    /**
     * 异步POST请求
     */
    @Override
    public void postAsyncRequest(Activity activity, IHttpUrlManage httpUrlManage, Object object, Class clazz, IHttpResponseCallBack callBack) {
        httpRequest.postAsyncRequest(activity, httpUrlManage, object, clazz, callBack);
    }

//    /**
//     * 同步GET请求
//     */
//    @Override
//    public void getSynRequest(Activity activity, IHttpUrlManage httpUrlManage, Object object, Class clazz, IHttpResponseCallBack callBack) {
//        httpRequest.getSynRequest(activity, httpUrlManage, object, clazz, callBack);
//    }

    /**
     * 异步GET请求
     */
    @Override
    public void getAsyncRequest(Activity activity, IHttpUrlManage httpUrlManage, Object object, Class clazz, IHttpResponseCallBack callBack) {
        httpRequest.getAsyncRequest(activity, httpUrlManage, object, clazz, callBack);
    }
}
