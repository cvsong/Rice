package com.cvsong.study.library.ndk;


import android.content.Context;

/**Jni调用
 * Created by 苍松 on 2018/2/26.
 */

public class JniUtil {

    static {

        System.loadLibrary("jniutil");

    }

    public native String getRsaKey(Context context);
}
