package com.cvsong.study.library.net.interfaces;

import android.app.Activity;

/**网络请求接口
 * Created by chenweisong on 2017/10/10.
 */

public interface IHttpRequest {

    /**
     * post同步请求
     */
    void postSynRequest(Activity activity, IHttpUrlManage httpUrlManage, Object object, final Class clazz, final IHttpResponseCallBack callBack);

    /**
     * post异步请求
     */
    void postAsyncRequest(Activity activity, IHttpUrlManage httpUrlManage, Object object, final Class clazz, final IHttpResponseCallBack callBack);


//    /**
//     * get同步请求
//     */
//    void getSynRequest(Activity activity,IHttpUrlManage httpUrlManage, Object object, final Class clazz, final IHttpResponseCallBack callBack);

    /**
     * get异步请求
     */
    void getAsyncRequest(Activity activity, IHttpUrlManage httpUrlManage, Object object, final Class clazz, final IHttpResponseCallBack callBack);

}
