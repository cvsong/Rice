package com.cvsong.study.library.net.download;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;

import com.cvsong.study.library.util.utilcode.util.ActivityUtils;
import com.cvsong.study.library.util.utilcode.util.NetworkUtils;
import com.cvsong.study.library.util.utilcode.util.SDCardUtils;
import com.cvsong.study.library.util.utilcode.util.ToastUtils;
import com.cvsong.study.library.wiget.CustomDialogManager;

import java.io.File;

/**
 * 版本更新管理类
 */
public class VersionUpdateManager {


    private Activity activity;
    private DownloadHelper downloadHelper;
    private VersionUpdateCallback callback;//下载更新回调


    public interface VersionUpdateCallback {

        void onNextStep();
    }


    public VersionUpdateManager(Activity context) {
        this.activity = context;
    }


    /**
     * 进行版本更新
     */
    public void makeVersionUpdate(int versionCode, @NonNull String downloadUrl, @NonNull String updateDesc, boolean haveToUpdate, @NonNull VersionUpdateCallback callback) {
        this.callback = callback;

        //检查网络连接情况
        boolean connected = NetworkUtils.isConnected();
        if (!connected) {//网络异常
            showNetErrorDialog();//弹出网络异常提示弹窗
            return;
        }

        downloadHelper = new DownloadHelper(activity);
        // 检查后台是否有app正在下载中
        if (downloadHelper.checkIsDownloading(downloadUrl)) {
            return;
        }

        //判断本地是否已有新版安装包，有则提示安装，无则提示下载
        String apkDownloadFilePath = DownloadUtils.getApkFilePath(versionCode);
        File apkFile = new File(apkDownloadFilePath);
        if (apkFile.exists()) {//判断apk是否已经下载

            showInstallApkDialog(apkDownloadFilePath, haveToUpdate);//弹出应用安装提示框
        } else {

            showVersionUpdateDialog(versionCode, downloadUrl, updateDesc, haveToUpdate);//弹出版本更新提示框
        }
    }


    /**
     * 弹出网络异常弹窗
     */
    private void showNetErrorDialog() {

        CustomDialogManager.newBuilder(activity)
                .titleText("温馨提示")
                .contentText("当前网络未连接或网络异常,请检查网络设置后再尝试打开应用.")
                .negativeButtonText("退出")
                .positiveButtonText("设置")
                .negativeListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ActivityUtils.finishAllActivities();
                    }
                })
                .positiveListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //检查设置页面是否存在
                        ResolveInfo resolveInfo = activity.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
                        if (resolveInfo != null) {
                            //跳转系统设置页面
                            activity.startActivity(intent);
                        }
                    }
                })
                .build().show();
    }


    /**
     * 弹出应用安装提示框
     */
    private void showInstallApkDialog(final String downloadApkFilePath, final boolean haveToUpdate) {

        CustomDialogManager.newBuilder(activity)
                .titleText("温馨提示")
                .contentText("检测到你已下载了新版本，请安装更新.")
                .positiveButtonText("安装")
                .positiveListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ActivityUtils.finishAllActivities();//退出应用
                        //通过安装引导页安装apk
                        DownloadUtils.installApkByGuide(activity, downloadApkFilePath);
                    }
                })
                .negativeButtonText("取消")
                .negativeListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finishOrGoNext(haveToUpdate);//退出应用或是进行下一步
                    }
                })
                .build().show();

    }


    /**
     * 弹出版本更新提示框
     */
    private void showVersionUpdateDialog(final int versionCode, final String downloadUrl, String updateDesc, final boolean haveToUpdate) {

        CustomDialogManager.newBuilder(activity)
                .titleText("检测到有新版本，请下载更新")
                .contentText(updateDesc)
                .positiveButtonText("更新")
                .positiveListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //下载apk
                        downloadApk(versionCode, haveToUpdate, downloadUrl);

                    }
                })
                .negativeButtonText("取消")
                .negativeListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finishOrGoNext(haveToUpdate);//退出应用或是进行下一步

                    }
                })
                .build().show();
    }

    /**
     * 下载apk
     */
    private void downloadApk(int versionCode, boolean haveToUpdate, String downloadUrl) {
        String appPackageName = DownloadUtils.getAppPackageName(versionCode);
        Float apkSize = 200f * 1024 * 1024;//默认Apk最大200MB

        if (!SDCardUtils.detectSdcardIsExist()) {//先判断SD卡是否存在
            ToastUtils.showShort("请检查存储卡是否安装");
            finishOrGoNext(haveToUpdate);//退出应用或是进行下一步
            return;
        }

        if (!SDCardUtils.isSatisfySpace(apkSize)) {//再判断存储空间是否足够
            ToastUtils.showShort("存储卡空间不足");
            finishOrGoNext(haveToUpdate);//退出应用或是进行下一步
            return;
        }

        DownloadUtils.startDownload(downloadUrl, activity, appPackageName);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                downloadHelper.startProgressDialog();
            }
        }, 100);
    }


    /**
     * 退出应用或是进行下一步
     *
     * @param haveToUpdate
     */
    private void finishOrGoNext(boolean haveToUpdate) {
        //通过是否必须更新来判断接下来的动作
        if (haveToUpdate) {//必须更新
            ActivityUtils.finishAllActivities();//退出应用
            return;
        }
        if (callback != null) {
            callback.onNextStep();
        }
    }


    /**
     * 反注销广播
     */
    public void close() {
        if (downloadHelper != null) {
            downloadHelper.closeBroadcast();
        }

        // 下载监控相关对象的销毁
        if (downloadHelper != null) {
            downloadHelper.onDestroy();
        }
    }
}
