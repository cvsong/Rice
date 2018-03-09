package com.cvsong.study.common_library.util;

import android.content.Context;

/**
 * 应用通用Util
 * Created by chenweisong on 2018/3/9.
 */

public class AppUtils {


    private static Context appContext;


    /**
     * 初始化AppUtil工具
     *
     * @param context
     */
    public static void initAppUtil(Context context) {
        appContext = context;
    }


    /**
     * 获取全局的Context对象
     *
     * @return
     */
    public static Context getAppContext() {
        if (null == appContext) {
            throw new NullPointerException(String.format("请在Application中初始化%s工具类", AppUtils.class.getSimpleName()));
        }
        return appContext;
    }


}
