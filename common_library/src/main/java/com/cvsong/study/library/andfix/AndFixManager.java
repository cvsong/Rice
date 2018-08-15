package com.cvsong.study.library.andfix;

import android.content.Context;

import com.alipay.euler.andfix.patch.PatchManager;
import com.cvsong.study.library.util.utilcode.util.Utils;

import java.io.IOException;

/**
 * 热修复框架AndFix管理类
 * <p>
 * Created by chenweisong on 2018/8/15.
 */

public class AndFixManager {


    private PatchManager patchManager;

    private AndFixManager() {
    }


    //静态内部类实现单例
    private static class AndFixManagerHandler {
        private static AndFixManager instance = new AndFixManager();
    }


    public static AndFixManager getInstance() {

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
        patchManager = new PatchManager(applicationContext);
        patchManager.init(appVersion);//current version
        patchManager.loadPatch();
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
