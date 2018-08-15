package com.cvsong.study.library.andfix;

import com.cvsong.study.library.util.utilcode.util.Utils;

import java.io.File;

/**
 * Patch文件路径管理类
 * Created by chenweisong on 2018/8/15.
 */

public class PatchFilePathManager {

    //patch文件目录
    public static final String PATCH_DIR = Utils.getApp().getExternalCacheDir().getAbsolutePath() + "/apatch/";

    //文件结尾
    private static final String FILE_END = ".apatch";

    //patch文件名
    public static final String PATCH_FILE_NAME = PATCH_DIR.concat("study").concat(FILE_END);


    /**
     * 获取Patch文件
     *
     * @return
     */
    public static File getPatchFile() {

        File file = new File(PATCH_DIR);
        if (file == null || !file.exists()) {
            file.mkdir();
        }

        return file;

    }


}
