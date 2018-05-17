package com.cvsong.study.library.net.interfaces;


import com.cvsong.study.library.net.entity.Result;

import okhttp3.Request;

/**okhttp请求结果回调
 * Created by chenweisong on 2017/10/10.
 */

public interface IHttpResponseCallBack<T> {

    void onSuccess(Result result, T entity);

    void onFailure(Request request, Exception exception);

    void onTokenInvalidation();//令牌失效

}
