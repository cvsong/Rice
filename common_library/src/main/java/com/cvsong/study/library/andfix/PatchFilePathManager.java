package com.cvsong.study.library.andfix;

import android.os.Environment;

import com.cvsong.study.library.util.utilcode.util.Utils;

import java.io.File;

/**
 * Patch文件路径管理类
 * Created by chenweisong on 2018/8/15.
 */

public class PatchFilePathManager {

    //patch文件目录
    public static final String PATCH_DIR = Environment.getExternalStorageDirectory() + "/apatch/";

    //文件结尾
    private static final String FILE_END = ".apatch";

    //patch文件名
    public static final String PATCH_FILE_NAME1 = PATCH_DIR.concat("study1").concat(FILE_END);

    public static final String PATCH_FILE_NAME2 = PATCH_DIR.concat("study2").concat(FILE_END);







}
