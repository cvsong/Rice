package com.cvsong.study.library.net.entity;

import com.cvsong.study.library.net.interfaces.IHttpResponseCallBack;
import com.cvsong.study.library.util.utilcode.util.LogUtils;
import com.cvsong.study.library.util.utilcode.util.ToastUtils;

import okhttp3.Request;

/**
 * Http网络请求回调实现类  用来对一些统一的东西进行处理
 * Created by chenweisong on 2017/10/12.
 */

public class HttpCallBack<T> implements IHttpResponseCallBack<T> {


    @Override
    public void onSuccess(Result result, T entity) {
        if (entity != null) {
            LogUtils.i("网络请求成功返回的数据:", entity.toString());
        }

    }

    @Override
    public void onFailure(Request request, Exception exception) {
        if (exception != null) {
            LogUtils.e("网络请求成功返回的数据:", exception.getMessage());
            ToastUtils.showShort(exception.getMessage());
        }

    }

    /**
     * 令牌失效
     */
    @Override
    public void onTokenInvalidation() {
//        AppActivityManage.getInstance().finishAllActivity();//结束所有Activity
//        Intent intent = new Intent(AppUtils.getContext(), LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        AppUtils.getContext().startActivity(intent);
        ToastUtils.showShort("登录失效,请重新登录");
    }
}
