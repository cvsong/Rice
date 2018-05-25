package com.cvsong.study.library.net.download;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.cvsong.study.library.net.download.DownloadService;
import com.cvsong.study.library.util.utilcode.util.AppUtils;

import java.io.File;


/**
 * 下载工具类
 */
public class DownloadUtils {


    /**
     * 下载
     *  @param url      下载链接
     * @param context
     * @param fileName 文件名
     */
    public static void startDownload(String url, final Context context, String fileName) {

        //开始下载服务
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra(DownloadService.DOWNLOAD_URL, url);
        intent.putExtra(DownloadService.FILE_NAME, fileName);
        context.startService(intent);
    }





    /**
     * 通过安装引导页安装apk
     *
     * @param context
     * @param localFilePath
     */
    public static void installApkByGuide(Context context, String localFilePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        File file = new File(localFilePath);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//兼容Android7.0 FileProvider的使用
            //此处的authority需要和manifest里面保持一致。
            String appPackageName = AppUtils.getAppPackageName();
            uri = FileProvider.getUriForFile(context, appPackageName + ".fileprovider", file);
            //需要申请权限
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(file);
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }


    /**
     * 生成本地安装包的文件名称
     *
     * @param versionNo
     * @return
     */
    public static String getAppPackageName(int versionNo) {
        StringBuffer fileName = new StringBuffer();
        fileName.append(AppUtils.getAppName());
        fileName.append("_v");
        fileName.append(versionNo);
        fileName.append(".apk");
        return fileName.toString();
    }

    /**
     * 生成本地安装包的文件路径
     *
     * @param versionNo
     * @return
     */
    public static String getApkFilePath(int versionNo) {

        StringBuilder filePath = new StringBuilder();
        filePath.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        filePath.append("/");
        filePath.append(Environment.DIRECTORY_DOWNLOADS);
        filePath.append("/");
        filePath.append(getAppPackageName(versionNo));
        return filePath.toString();
    }
}
