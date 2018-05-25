package com.cvsong.study.rice.manager.http;

import android.app.Activity;


import com.cvsong.study.library.net.entity.HttpCallBack;
import com.cvsong.study.library.net.entity.Result;
import com.cvsong.study.library.net.httpservice.HttpRequestUtil;
import com.cvsong.study.rice.entity.TestVersionUpdateEntity;

import okhttp3.Request;

/**
 * 应用网络管理类
 * <p>
 * Created by chenweisong on 2017/10/10.
 */

public class AppHttpManage {

    private static final String TAG = AppHttpManage.class.getSimpleName();

    /**
     * 检查版本更新
     */
    public static void checkVersionUpdate(Activity activity, final HttpCallBack<TestVersionUpdateEntity> callBack) {
        HttpRequestUtil.getInstance().postAsynRequest(activity, HttpUrlManage.RICE_SYSTEM_VERSION_UPDATE, null, Object.class, new HttpCallBack<Object>() {
            @Override
            public void onSuccess(Result result, Object entity) {
                super.onSuccess(result, entity);
                initTestEntity(result);
            }

            @Override
            public void onFailure(Request request, Exception exception) {
                super.onFailure(request, exception);
                initTestEntity(null);
            }

            private void initTestEntity(Result result) {
                TestVersionUpdateEntity updateEntity = new TestVersionUpdateEntity();
                updateEntity.setDownloadUrl("http://10.138.60.143:10000/apk/CreditCat1.5.1-A0001.apk");
                updateEntity.setVersionCode(10);
                updateEntity.setUpdateDesc("新版本解决了之前的bug,优化了用户体验...");
                updateEntity.setHaveToUpdate(false);
                callBack.onSuccess(result, updateEntity);
            }
        });
    }

    /**
     * 用户登录
     */
    public static void makeLogin(Activity activity, String userName, String psw, final HttpCallBack<Object> callBack) {
        HttpRequestUtil.getInstance().postAsynRequest(activity, HttpUrlManage.RICE_SYSTEM_LOGIN, null, Object.class, new HttpCallBack<Object>() {
            @Override
            public void onSuccess(Result result, Object entity) {
                super.onSuccess(result, entity);
                callBack.onSuccess(result, entity);
            }

            @Override
            public void onFailure(Request request, Exception exception) {
                super.onFailure(request, exception);
                callBack.onFailure(request, exception);
            }
        });
    }

}
