package com.cvsong.study.rice.manager.http;

import android.app.Activity;


import com.cvsong.study.library.net.entity.HttpCallBack;
import com.cvsong.study.library.net.entity.Result;
import com.cvsong.study.library.net.exception.AppHttpException;
import com.cvsong.study.library.net.httpservice.HttpRequestUtil;
import com.cvsong.study.rice.entity.SilverInfoEntity;
import com.cvsong.study.rice.entity.TestVersionUpdateEntity;

import java.util.List;

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
//                super.onFailure(request, exception);
                initTestEntity(null);
            }

            private void initTestEntity(Result result) {
                TestVersionUpdateEntity updateEntity = new TestVersionUpdateEntity();
                updateEntity.setDownloadUrl("http://10.138.60.143:10000/apk/CreditCat1.5.1-A0001.apk");
                updateEntity.setVersionCode(1);
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



    /**
     * 获取白银信息
     */
    public static void getSilverInfo(Activity activity,final HttpCallBack<List<SilverInfoEntity.ResultEntity>> callBack) {
        HttpRequestUtil.getInstance().getAsynRequest(activity, HttpUrlManage.RICE_SILVER_INFO, null, SilverInfoEntity.class, new HttpCallBack<SilverInfoEntity>() {
            @Override
            public void onSuccess(Result result, SilverInfoEntity entity) {
                super.onSuccess(result, entity);
                if (entity==null) {
                    callBack.onFailure(null,new AppHttpException("数据为空"));
                    return;
                }
                String retCode = entity.getRetCode();
                if (!"200".equals(retCode)) {
                    callBack.onFailure(null,new AppHttpException(entity.getMsg()));
                    return;
                }

                callBack.onSuccess(result, entity.getResult());
            }

            @Override
            public void onFailure(Request request, Exception exception) {
                super.onFailure(request, exception);
                callBack.onFailure(request, exception);
            }
        });
    }


}
