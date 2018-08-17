package com.cvsong.study.library.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.cvsong.study.library.util.utilcode.util.StringUtils;
import com.cvsong.study.library.util.utilcode.util.Utils;
import com.meituan.android.walle.ChannelInfo;
import com.meituan.android.walle.WalleChannelReader;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用工具类
 */
public class CommonUtils {

    /**
     * 获取安装包信息
     *
     * @param context
     * @return
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo packageInfo = new PackageInfo();
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return packageInfo;
    }


    /**
     * 判断Sdcard是否存在
     *
     * @return
     */
    public static boolean detectSdcardIsExist() {
        String extStorageState = Environment.getExternalStorageState();
        File file = Environment.getExternalStorageDirectory();
        if (!Environment.MEDIA_MOUNTED.equals(extStorageState)
                || !file.exists() || !file.canWrite()
                || file.getFreeSpace() <= 0) {
            return false;
        }
        return true;
    }


    /**
     * 获得当前时间
     *
     * @return
     */
    public static String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }

    /**
     * dp转px
     *
     * @param dp
     * @return
     */
    public static int Dp2Px(Context context, float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    /**
     * px转dp
     *
     * @param px
     * @return
     */
    public static int Px2Dp(Context context, float px) {
        return (int) (px / context.getResources().getDisplayMetrics().density + 0.5f);
    }


    /**
     * 获取应用版本号
     *
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        int versioncode = 0;
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            versioncode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versioncode;
    }

    /**
     * 获取应用版本名
     *
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String versionName = "";
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }


    /**
     * 清除WebView缓存
     */
    public static synchronized void clearWebViewCache(Context context) {
        //清理Webview缓存数据库
        try {
            context.deleteDatabase("webview.db");
            context.deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //WebView 缓存文件
        File appCacheDir = new File(context.getApplicationContext().getCacheDir().getAbsolutePath());
        File webviewCacheDir = new File(context.getCacheDir().getAbsolutePath() + "/webviewCache");

        //删除webview 缓存目录
        if (webviewCacheDir.exists()) {
            deleteFile(webviewCacheDir);
        }
        //删除webview 缓存 缓存目录
        if (appCacheDir.exists()) {
            deleteFile(appCacheDir);
        }
    }

    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
        }
    }


    /**
     * 格式化手机号
     *
     * @param phoneNo
     * @return
     */
    public static String formatPhoneNo(String phoneNo) {
        if (null == phoneNo) {
            return "";
        }
        if (phoneNo.startsWith("+86")) {
            phoneNo = phoneNo.substring(3);
        }
        if (phoneNo.startsWith("86")) {
            phoneNo = phoneNo.substring(2);
        }
        phoneNo = phoneNo.replaceAll("-", "");
        phoneNo = phoneNo.replaceAll(" ", "");
        phoneNo = phoneNo.trim();
        return phoneNo;
    }


    /**
     * 银行卡号格式化，例如：6223 4356 8537 1235
     *
     * @param cardNo
     * @return
     */
    public static String formatBankCardNo(String cardNo) {
        if (TextUtils.isEmpty(cardNo)) {
            return "";
        }
        cardNo = cardNo.replace(" ", "");
        StringBuilder sb = new StringBuilder();
        try {
            for (int i = 0; i < cardNo.length(); i++) {
                if (i != 0 && i % 4 == 0) {
                    sb.append(" ");
                }
                sb.append(cardNo.charAt(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    /**
     * 去除字符串中的特殊字符只允中文
     */
    public static String getCharacterString(String name) {
        if (TextUtils.isEmpty(name)) {
            return "";
        }
        String regEx = "[^(\\u4e00-\\u9fa5)]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(name);
        return m.replaceAll("").trim();
    }


    /**
     * 获取屏幕图像
     *
     * @param activity
     * @return
     */
    public static Bitmap getScreenBitmap(Activity activity) {
        View dView = activity.getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        return dView.getDrawingCache();
    }


    /**
     * 获取应用签名
     *
     * @param context
     * @return
     */
    public static String getSignature(Context context)

    {
        try {
            /** 通过包管理器获得指定包名包含签名的包信息 **/
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            /******* 通过返回的包信息获得签名数组 *******/
            Signature[] signatures = packageInfo.signatures;
            /******* 循环遍历签名数组拼接应用签名 *******/
            return signatures[0].toCharsString();
            /************** 得到应用签名 **************/
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 获取安装包的渠道号
     *
     * @param defChannel 默认渠道号
     * @return
     */
    public static String getChannelCode(String defChannel) {
        String channel = defChannel;
        // 如果没有使用PackerNg打包添加渠道，默认返回的是""
        ChannelInfo channelInfo = WalleChannelReader.getChannelInfo(Utils.getApp());
        if (channelInfo != null) {
            String apkChannel = channelInfo.getChannel();
            channel = TextUtils.isEmpty(apkChannel) ? defChannel : apkChannel;
        }
        return channel;
    }
}
