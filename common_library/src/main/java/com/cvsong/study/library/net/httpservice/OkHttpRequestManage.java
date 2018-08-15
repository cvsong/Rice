package com.cvsong.study.library.net.httpservice;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.cvsong.study.library.ndk.JniUtil;
import com.cvsong.study.library.net.cache.ACache;
import com.cvsong.study.library.util.GsonUtil;
import com.cvsong.study.library.net.entity.Result;
import com.cvsong.study.library.net.exception.AppHttpException;
import com.cvsong.study.library.net.exception.HttpExceptionConstant;
import com.cvsong.study.library.net.interfaces.IHttpRequest;
import com.cvsong.study.library.net.interfaces.IHttpResponseCallBack;
import com.cvsong.study.library.net.interfaces.IHttpUrlManage;
import com.cvsong.study.library.util.RSAUtil;
import com.cvsong.study.library.util.app_tools.AppSpUtils;
import com.cvsong.study.library.util.utilcode.util.AppUtils;
import com.cvsong.study.library.util.utilcode.util.LogUtils;


import com.cvsong.study.library.util.utilcode.util.Utils;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static android.graphics.Typeface.NORMAL;


/**
 * OkHttp网络请求管理类
 * Created by chenweisong on 2017/10/1.
 */

public class OkHttpRequestManage implements IHttpRequest {

    private static final String TAG = OkHttpRequestManage.class.getSimpleName();

    //登录令牌
    private static final String ACCESSTOKEN = "token";

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    private static Handler handler;

    private volatile static OkHttpRequestManage okHttpRequestManage;

    public static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(HttpConstants.CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(HttpConstants.WRITE_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(HttpConstants.READ_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    boolean connected = HttpRequestHelper.isNetworkConnected();
                    if (!connected) {//网络连接不可用时强制使用本地缓存
                        request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                    }
                    Response response = chain.proceed(request);

                    return response;
                }
            })
            .build();

    private OkHttpRequestManage() {

        handler = new Handler(Looper.getMainLooper());


    }

    public static OkHttpRequestManage getInstance() {

        if (okHttpRequestManage == null) {
            synchronized (HttpRequestUtil.class) {
                if (okHttpRequestManage == null) {
                    okHttpRequestManage = new OkHttpRequestManage();
                }

            }

        }
        return okHttpRequestManage;
    }


    /**
     * 同步Pos请求
     *
     * @param httpUrlManage 网络请求Url对象
     * @param object        请求体
     * @param clazz         响应体
     * @param callBack      回调
     */
    @Override
    public void postSynRequest(final Activity activity, final IHttpUrlManage httpUrlManage, final Object object, final Class clazz, final IHttpResponseCallBack callBack) {
        //请求前校验
        if (checkBeforeRequest(activity, httpUrlManage, callBack)) return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = buildPostRequest(activity, httpUrlManage, object, callBack);
                    final Response response = okHttpClient.newCall(request).execute();
                    if (activity == null || activity.isFinishing()) {
                        return;
                    }
                    handleSuccessResponse(request, response, callBack, httpUrlManage, clazz);

                } catch (IOException e) {
                    handleFailResponse(null, new AppHttpException(HttpExceptionConstant.HTTP_EXCEPTION_RESPONSE, e.getMessage()), callBack);
                }

            }
        }).start();


    }


    /**
     * 异步Pos请求
     *
     * @param httpUrlManage 网络请求Url对象
     * @param object        请求体
     * @param clazz         响应体
     * @param callBack      回调
     */
    @Override
    public void postAsyncRequest(final Activity activity, final IHttpUrlManage httpUrlManage, Object object, final Class clazz, final IHttpResponseCallBack callBack) {
        //请求前校验
        if (checkBeforeRequest(activity, httpUrlManage, callBack)) return;
        //构建Post请求体
        final Request request = buildPostRequest(activity, httpUrlManage, object, callBack);
        if (request == null) {
            return;
        }
        okHttpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, final IOException e) {
                        if (activity == null || activity.isFinishing()) {
                            return;
                        }
                        handleFailResponse(call.request(), new AppHttpException(HttpExceptionConstant.HTTP_EXCEPTION_RESPONSE, e.getMessage()), callBack);//处理失败结果
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (activity == null || activity.isFinishing()) {
                            return;
                        }
                        handleSuccessResponse(request, response, callBack, httpUrlManage, clazz);
                    }
                });
    }

    /**
     * 异步Get请求
     *
     * @param activity
     * @param httpUrlManage
     * @param object
     * @param clazz
     * @param callBack
     */
    @Override
    public void getAsyncRequest(final Activity activity, final IHttpUrlManage httpUrlManage, Object object, final Class clazz, final IHttpResponseCallBack callBack) {
        if (activity == null || activity.isFinishing()) {
            return;
        }

        if (callBack == null) {
            throw new NullPointerException("网络请求回调不能为空");
        }

        if (httpUrlManage == null) {
            throw new NullPointerException("网络请求URL管理不能为空");
        }

        StringBuffer sb = new StringBuffer(httpUrlManage.getUrl());
        try {
            if (null != object) {
                Field[] fields = object.getClass().getFields();
                String str = HttpRequestHelper.fieldArray2Str(object, fields);
                sb.append(str);
            }

        } catch (IllegalAccessException e) {
            handleFailResponse(null, new AppHttpException(HttpExceptionConstant.HTTP_EXCEPTION_REQUEST, "网络请求对象字段反射失败"), callBack);
            return;
        }

        final String httpUrl = sb.toString();
        LogUtils.e(TAG, "网络请求Url以及参数:" + httpUrl);
        //请求的http协议
        final Request request = new Request.Builder().url(httpUrl).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                if (activity == null || activity.isFinishing()) {
                    return;
                }
                //处理失败结果
                handleFailResponse(call.request(), new AppHttpException(HttpExceptionConstant.HTTP_EXCEPTION_RESPONSE, e.getMessage()), callBack);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (activity == null || activity.isFinishing()) {
                    return;
                }
                handleSuccessResponse(request, response, callBack, httpUrlManage, clazz);
            }
        });

    }

    /**
     * 请求前校验
     */
    private boolean checkBeforeRequest(Activity activity, IHttpUrlManage httpUrlManage, IHttpResponseCallBack callBack) {
        if (activity == null || activity.isFinishing()) {
            return true;
        }

        if (callBack == null) {
            throw new NullPointerException("网络请求回调不能为空");
        }

        if (httpUrlManage == null) {
            throw new NullPointerException("网络请求URL管理不能为空");
        }

        if (httpUrlManage.isNeedCache()) {//需要缓存
            //TODO
        }

        // 网络连接检查
        if (!HttpRequestHelper.isNetworkConnected()) {
            callBack.onFailure(null, new AppHttpException(HttpExceptionConstant.HTTP_EXCEPTION_NET_ERROR, "网络连接异常,请检查网络"));
            return true;
        }

        //网络请求URL是否为空检查
        if (TextUtils.isEmpty(httpUrlManage.getUrl())) {
            callBack.onFailure(null, new AppHttpException(HttpExceptionConstant.HTTP_EXCEPTION_REQUEST, "网络请求URL为空"));
            return true;
        }
        return false;
    }


    /****************************************GET请求***********************************************/

//    /**
//     * 同步Get请求
//     *
//     * @param activity
//     * @param httpUrlManage
//     * @param object
//     * @param clazz
//     * @param callBack
//     */
//    @Override
//    public void getSynRequest(Activity activity, IHttpUrlManage httpUrlManage, Object object, Class clazz, IHttpResponseCallBack callBack) {
//
//    }

    /**
     * 构建Post请求体
     */

    private static Request buildPostRequest(Activity activity, final IHttpUrlManage httpUrlManage, Object object, IHttpResponseCallBack callBack) {

        String jsonStr = object == null ? "" : GsonUtil.GsonString(object);
        final String httpUrl = httpUrlManage.getUrl();
        boolean userIsLogin = AppSpUtils.getInstance().getBoolean(AppSpUtils.IS_USER_LOGIN);
        String token = AppSpUtils.getInstance().getString(AppSpUtils.ACCESS_TOKEN);

        LogUtils.e(TAG, "用户是否已登录:" + userIsLogin);
        LogUtils.e(TAG, "token:" + token);
        LogUtils.e(TAG, "网络请求URL:" + httpUrl);
        LogUtils.e(TAG, "网络请求Json串:" + jsonStr);

        //添加特殊处理 针对特定使用的JSON对象传递
        RequestBody body;
        try {
            if (httpUrlManage.isNeedEncryptRequestData()) {//是否需要加密请求数据
                byte[] reqStr = RSAUtil.encryptByPrivateKey(jsonStr.getBytes("utf-8"), HttpConstants.RSA_KEY);
                String req = RSAUtil.encodeBase64ToString(reqStr);
                body = RequestBody.create(JSON, req);
            } else {
                body = RequestBody.create(JSON, jsonStr);
            }

            CacheControl cacheControl = new CacheControl.Builder()
                    .maxAge(10, TimeUnit.MILLISECONDS)//指示客户机可以接收生存期不大于指定时间的响应。
                    .build();

            return new Request.Builder()
                    .addHeader(ACCESSTOKEN, token)//请求头中添加token
                    .url(httpUrlManage.getUrl())
                    .cacheControl(cacheControl)//缓存控制
                    .post(body)
                    .tag(activity)//设置网络请求标记
                    .build();

        } catch (Exception e) {
            if (e instanceof IOException) {
                handleFailResponse(null, new AppHttpException(HttpExceptionConstant.HTTP_EXCEPTION_REQUEST, "IO异常"), callBack);
            } else {
                handleFailResponse(null, new AppHttpException(HttpExceptionConstant.HTTP_EXCEPTION_REQUEST, "加密异常"), callBack);
            }
            return null;
        }

//            Map<String, String> map = new HashMap();
//            map.put("jsonStr", jsonStr);
//            FormBody.Builder builder = new FormBody.Builder();
//            Set<Map.Entry<String, String>> entries = map.entrySet();
//            for (Map.Entry<String, String> entry : entries) {
//                builder.add(entry.getKey(), entry.getValue());
//            }
//
//            body = builder.build();


    }

    /**
     * 对成功请求结果进行处理
     */
    private void handleSuccessResponse(final Request request, final Response response, final IHttpResponseCallBack callBack, final IHttpUrlManage httpUrlManage, Class clazz) {

        if (response == null) {
            handleFailResponse(request, new AppHttpException(HttpExceptionConstant.HTTP_EXCEPTION_RESPONSE, "返回体为空"), callBack);
            return;
        }

        if (!response.isSuccessful()) {//校验是否成功
            handleFailResponse(request, new AppHttpException(HttpExceptionConstant.HTTP_EXCEPTION_RESPONSE, "http状态码" + response.code()), callBack);
            return;
        }

        ResponseBody body = response.body();
        if (body == null) {
            handleFailResponse(request, new AppHttpException(HttpExceptionConstant.HTTP_EXCEPTION_RESPONSE, "返回结果体为空"), callBack);
            return;
        }

        if (body.contentLength() == 0) {
            handleFailResponse(request, new AppHttpException(HttpExceptionConstant.HTTP_EXCEPTION_RESPONSE, "数据转换异常"), callBack);
            return;
        }


        String token = response.headers().get(ACCESSTOKEN);
        if (token != null) {//从响应头中获取token并保存
            AppSpUtils.getInstance().put(AppSpUtils.ACCESS_TOKEN, token);
        }

        try {
            //对成功结果进行处理
            String resStr = body.string();
            if (TextUtils.isEmpty(resStr)) {
                handleFailResponse(request, new AppHttpException(HttpExceptionConstant.HTTP_EXCEPTION_RESPONSE, "请求结果为空"), callBack);
                return;
            }
            String result;

            if (httpUrlManage.isNeedCache()) {//数据缓存
                ACache.get(Utils.getApp()).put(httpUrlManage.getUrl(), resStr);
            }


            if (httpUrlManage.isNeedDecryptResponseData()) {//是否需要解密响应数据
                result = RSAUtil.decryptByPrivateKey(resStr, HttpConstants.RSA_KEY);//对数据进行解密-内部已做Base64转换
            } else {
                result = resStr;
            }

            LogUtils.e(TAG, "Url类型：" + httpUrlManage.getUrlDesc() + "，网络请求返回数据:" + result);

            if (httpUrlManage.getResponseDataType() == IHttpUrlManage.HTTP_NORMAL) {//外层数据统一的请求
                //处理正常的响应结果
                handleNormalResponse(request, callBack, httpUrlManage, clazz, result);

            } else {//处理特殊的响应结果

                final Object obj;
                if (!TextUtils.isEmpty(result) && !((clazz == String.class) || (clazz == Object.class))) {
                    obj = GsonUtil.GsonToBean(result, clazz);
                } else {
                    obj = result;
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess(null, obj);
                    }
                });

            }


        } catch (Exception e) {
            handleFailResponse(request, e, callBack);
        }
    }

    /**
     * 处理正常的响应结果
     */
    private void handleNormalResponse(final Request request, final IHttpResponseCallBack callBack, final IHttpUrlManage httpUrlManage, Class clazz, final String result) {
        //解析最外层数据
        final Result res = GsonUtil.GsonToBean(result, Result.class);
        if (res == null) {
            handleFailResponse(request, new AppHttpException(HttpExceptionConstant.HTTP_EXCEPTION_RESPONSE, "请求结果外层数据Json解析异常"), callBack);
            return;
        }

        final Object obj;

        //把Object对象转换成json字符串然后在根据对象进行重新转换
        String retValueString = GsonUtil.GsonString(res.getData());

        if (!TextUtils.isEmpty(retValueString) && !((clazz == String.class) || (clazz == Object.class))) {
            obj = GsonUtil.GsonToBean(retValueString, clazz);
        } else {
            obj = retValueString;
        }

        handler.post(new Runnable() {
            @Override
            public void run() {

                switch (res.getStatus()) {

                    case HttpConstants.RESULT_STATUS_OK:
                        callBack.onSuccess(res, obj);
                        break;

                    case HttpConstants.RESULT_STATUS_TOOKEN_USELESS://token失效
                        callBack.onTokenInvalidation();
                        break;
                    default:
                        callBack.onFailure(request, new AppHttpException(HttpExceptionConstant.HTTP_EXCEPTION_RESPONSE, res.getMessage()));
                        break;

                }
            }
        });
    }


    /**
     * 处理失败结果
     */
    private static void handleFailResponse(final Request request, final Exception e, final IHttpResponseCallBack callBack) {
        if (e != null) {
            LogUtils.e(TAG, e.getMessage());
        }

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (e instanceof SocketTimeoutException) {
                    callBack.onFailure(request, new AppHttpException(HttpExceptionConstant.HTTP_EXCEPTION_RESPONSE, "服务器响应超时"));
                } else if (e instanceof JsonSyntaxException) {
                    callBack.onFailure(request, new AppHttpException(HttpExceptionConstant.HTTP_EXCEPTION_RESPONSE, "JSON转Bean解析异常"));
                } else if (e instanceof JsonIOException) {
                    callBack.onFailure(request, new AppHttpException(HttpExceptionConstant.HTTP_EXCEPTION_RESPONSE, "Bean转JSON异常"));
                } else {
                    callBack.onFailure(request, e);
                }

            }
        });
    }


    /**
     * 取消网络请求
     *
     * @param tag
     */
    public static void cancel(Object tag) {

        Dispatcher dispatcher = okHttpClient.dispatcher();
        synchronized (dispatcher) {
            for (Call call : dispatcher.queuedCalls()) {//取消请求队列中的请求
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
            for (Call call : dispatcher.runningCalls()) {//取消正在执行的请求
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
        }
    }

    /**
     * 取消全部的网络请求
     */
    public static void cancelAll() {
        okHttpClient.dispatcher().cancelAll();
    }

}
