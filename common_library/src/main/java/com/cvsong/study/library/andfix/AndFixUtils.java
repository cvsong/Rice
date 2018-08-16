package com.cvsong.study.library.andfix;

import android.content.Context;
import android.util.Log;

import com.alipay.euler.andfix.patch.PatchManager;

import java.io.IOException;

/**
 * 热修复框架AndFix管理类
 * <p>
 * Created by chenweisong on 2018/8/15.
 */

public class AndFixUtils {


    private PatchManager patchManager;

    private AndFixUtils() {
    }


    //静态内部类实现单例
    private static class AndFixManagerHandler {
        private static AndFixUtils instance = new AndFixUtils();
    }


    public static AndFixUtils getInstance() {

        return AndFixManagerHandler.instance;
    }


    /**
     * 初始化
     */
    public void initAndFix(Context context, String appVersion) {
        if (context == null) {
            throw new NullPointerException("context不能为空");
        }

        Context applicationContext = context.getApplicationContext();
        patchManager = new PatchManager(applicationContext);//初始化阿里的热修复
        patchManager.init(appVersion);//current version
        patchManager.loadPatch();//加载之前的apatch包
        Log.e("TAG", appVersion);

    }


    /**
     * 加载Patch文件
     *
     * @param patchFilePath patch文件路径
     */
    public void addPatch(String patchFilePath) {
        if (patchManager == null) {
            return;
        }

        try {
            patchManager.addPatch(patchFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
