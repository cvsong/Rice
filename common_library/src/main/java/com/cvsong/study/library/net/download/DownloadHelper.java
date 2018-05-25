package com.cvsong.study.library.net.download;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;


import com.cvsong.study.library.util.app_tools.AppSpUtils;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * 下载任务辅助类，用于APP下载时显示进度提示框
 */

public class DownloadHelper {

    public static final String KEY_DOWNLOAD_ID = "key_download_id";

    /**
     * 启动页
     */
    private Context context;

    /**
     * 进度条
     */
    private ProgressDialog progressDialog;

    /**
     * 下载进度显示
     */
    private DownloadObserver downloadObserver;

    /**
     * 下载进度
     */
    private int progress;

    /**
     * 下载监听器
     */
    public DownLoadIdBroadCast broadcast;

    /**
     * 监听键名
     */
    public static final String ACTION_RECEIVER_DOWNLOAD = "com.app.download";


    public DownloadHelper(Context context) {
        this.context = context;
        register();
    }

    /**
     * 注册动态广播，用于接收当前下载的apk的downloadId
     */
    private void register() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_RECEIVER_DOWNLOAD);

        broadcast = new DownLoadIdBroadCast();
        context.registerReceiver(broadcast, filter);
    }


    /**
     * 校验软件是否正在下载中
     *
     * @param downloadUrl
     * @return
     */
    public boolean checkIsDownloading(String downloadUrl) {
        if (isDownloading(downloadUrl)) {
            Long downloadId = AppSpUtils.getInstance().getLong(AppSpUtils.APK_DOWNLOAD_ID, -1);
            if (downloadId != -1) {
                startProgressDialog();
            }
            return true;
        }
        return false;
    }

    public void startProgressDialog() {
        displayProgressDialog();
        Long downloadId = AppSpUtils.getInstance().getLong(AppSpUtils.APK_DOWNLOAD_ID, -1);
        downloadObserver = new DownloadObserver(handler, downloadId);
        context.getContentResolver().registerContentObserver(Uri.parse("content://downloads/"), true, downloadObserver);
    }

    /**
     * 设置进度条
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1001) {
                progress = (int) msg.obj;
                if (progressDialog != null) {
                    progressDialog.setProgress(progress);
                    if (progress == 100) {
                        progressDialog.dismiss();
                    }
                }
            }

        }
    };

    /**
     * 显示进度条
     */
    private void displayProgressDialog() {
        // 创建ProgressDialog对象
        progressDialog = new ProgressDialog(context);
        // 设置进度条风格，风格为长形
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // 设置ProgressDialog 标题
        progressDialog.setTitle("下载提示");
        // 设置ProgressDialog 提示信息
        progressDialog.setMessage("当前下载进度:");
        // 设置ProgressDialog 标题图标
        //progressDialog.setIcon(R.drawable.a);
        // 设置ProgressDialog 进度条进度
        progressDialog.setProgress(100);
        // 设置ProgressDialog 的进度条是否不明确
        progressDialog.setIndeterminate(false);
        // 设置ProgressDialog 是否可以按退回按键取消
        progressDialog.setCancelable(false);
        // 让ProgressDialog显示
        progressDialog.show();
    }


    /**
     * 判断传入的Url地址是否正在下载中
     *
     * @param url
     * @return
     */
    private boolean isDownloading(String url) {
        Cursor c = ((DownloadManager) context.getSystemService(DOWNLOAD_SERVICE))
                .query(new DownloadManager.Query().setFilterByStatus(DownloadManager.STATUS_RUNNING));
        if (c.moveToFirst()) {
            String tmpURI = c.getString(c.getColumnIndex(DownloadManager.COLUMN_URI));
            if (tmpURI.equals(url)) {
                if (c != null && !c.isClosed()) {
                    c.close();
                }
                return true;
            }
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }
        return false;
    }


    /**
     * 下载广播接收者，用于接收下载的id
     */
    public class DownLoadIdBroadCast extends BroadcastReceiver {

        public DownLoadIdBroadCast() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Long downLoadId = intent.getLongExtra(KEY_DOWNLOAD_ID, -1);

            if (downLoadId != null) {
                downloadObserver = new DownloadObserver(handler, downLoadId);
                DownloadHelper.this.context.getContentResolver().registerContentObserver(Uri.parse("content://downloads/"), true, downloadObserver);
            }
        }
    }

    /**
     * 销毁
     */
    public void onDestroy() {
        if (null != handler) {
            handler.removeCallbacksAndMessages(null);
        }
        if (null != downloadObserver) {
            context.getContentResolver().unregisterContentObserver(downloadObserver);
        }
        if (null != progressDialog) {
            progressDialog.dismiss();
        }

    }

    /**
     * 反注册广播
     */
    public void closeBroadcast() {
        if (broadcast != null) {
            context.unregisterReceiver(broadcast);
        }
    }
}
