package com.cvsong.study.library.net.httpservice;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.cvsong.study.library.util.utilcode.util.Utils;


import java.lang.reflect.Field;

/**
 * Http请求辅助工具类
 * Created by chenweisong on 2017/10/10.
 */

public class HttpRequestHelper {

    /**
     * 检测网络是否可用
     *
     * @return
     */
    public static boolean isNetworkConnected() {

        ConnectivityManager cm = (ConnectivityManager) Utils.getApp().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }


    /**
     * 检测网络是否连接
     *
     * @return
     */
    private static boolean checkNetworkState() {
        boolean flag = false;
        // 得到网络连接信息
        ConnectivityManager manager = (ConnectivityManager) Utils.getApp().getSystemService(Context.CONNECTIVITY_SERVICE);
        // 去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            flag = manager.getActiveNetworkInfo().isAvailable();
        }

        return flag;
    }


    /**
     * WIFI环境下
     */
    private static final int NETTYPE_WIFI = 0x01;
    /**
     * GPRS
     */
    private static final int NETTYPE_CMWAP = 0x02;
    private static final int NETTYPE_CMNET = 0x03;

    private static String CMNET = "cmnet";

    /**
     * 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
     * <p/>
     * 获取当前网络类型
     *
     * @return
     */
    public static int getNetworkType() {
        int netType = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) Utils.getApp().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if (!TextUtils.isEmpty(extraInfo)) {
                if (extraInfo.toLowerCase().equals(CMNET)) {
                    netType = NETTYPE_CMNET;
                } else {
                    netType = NETTYPE_CMWAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NETTYPE_WIFI;
        }
        return netType;
    }


    /**
     * field数组组合成string
     *
     * @param reqs
     * @param fields
     * @return
     * @throws IllegalAccessException
     */
    public static String fieldArray2Str(Object reqs, Field[] fields) throws IllegalAccessException {
        StringBuffer sb = new StringBuffer();
        if (fields != null && fields.length > 0) {
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true); //设置些属性是可以访问的
                if (field.isSynthetic()) {//判断是不是编译器自动生成的Field->去除"$change"字段
                    continue;
                }
                /**忽略serialVersionUID**/
                if (field.getName().equals("serialVersionUID")) {
                    continue;
                }

                Object value = field.get(reqs);//得到此属性的值
                if (i == 0) {
                    sb.append("?").append(field.getName()).append("=").append(value);
                } else {
                    sb.append("&").append(field.getName()).append("=").append(value);
                }
            }
        }
        return sb.toString();
    }
}
