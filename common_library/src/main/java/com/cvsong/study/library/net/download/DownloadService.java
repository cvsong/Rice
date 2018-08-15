package com.cvsong.study.library.net.download;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.webkit.MimeTypeMap;

import com.cvsong.study.library.util.VerifyUtils;
import com.cvsong.study.library.util.app_tools.AppConfig;
import com.cvsong.study.library.util.app_tools.AppSpUtils;
import com.cvsong.study.library.util.utilcode.util.ActivityUtils;
import com.cvsong.study.library.util.utilcode.util.ToastUtils;


/**
 * 下载服务
 *
 * @author zhuofeng
 */
public class DownloadService extends Service {

    public static final String  DOWNLOAD_URL="downloadUrl";//下载URL
    public static final String  FILE_NAME="fileName";//文件名称

    /**
     * 下载管理器
     */
    private DownloadManager downloadManager = null;
    /**
     * 完成监听器
     */
    private OnCompleteReceiver onComplete;
    /**
     * 下载编号
     */
    private Long downloadId;
    /**
     * 文件名
     */
    private String fileName;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // 实例化下载管理器和下载完成监听器
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        onComplete = new OnCompleteReceiver();

        // 注册下载完成监听器
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        //注册通知栏点击监听器
        registerReceiver(onNotificationClick, new IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED));

    }

    @Override
    public void onDestroy() {
        // 注销完成监听器和通知栏点击监听器
        unregisterReceiver(onComplete);
        unregisterReceiver(onNotificationClick);
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String url = intent.getStringExtra(DOWNLOAD_URL);
            fileName = intent.getStringExtra(FILE_NAME);
            download(url, fileName);//下载apk
        }
        //获取最后一次下载的id值
        Long lastDownLoadId = AppSpUtils.getInstance().getLong(AppSpUtils.APK_DOWNLOAD_ID,-1);
        if (-1 != lastDownLoadId) {
            //如果这个id  -1 那么说明还在继续下载
            downloadId = lastDownLoadId;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 下载
     */
    @SuppressLint("NewApi")
    private void download(String url, String fileNameTmp) {
        // 校验下载地址是否正确
        if (!VerifyUtils.isUrl(url)) {
            ToastUtils.showShort("下载地址错误");
            return;
        }

        // 校验下载地址是否正在下载
        if (isDownloading(url)) {
            return;
        }

        // 将请求对象放入下载管理器
        DownloadManager.Request request = createRequest(url, fileNameTmp);
        downloadId = downloadManager.enqueue(request);
        //保存下载的Id
        AppSpUtils.getInstance().put(AppSpUtils.APK_DOWNLOAD_ID,downloadId);

        // 把当前下载id发送给广播接收者
        Intent intent = new Intent();
        intent.putExtra(DownloadHelper.KEY_DOWNLOAD_ID, downloadId);
        intent.setAction(DownloadHelper.ACTION_RECEIVER_DOWNLOAD);
        sendBroadcast(intent);
    }

    /**
     * 创建请求对象
     *
     * @param url      下载地址
     * @param fileName 文件名
     * @return
     */
    private DownloadManager.Request createRequest(String url, String fileName) {

        Uri uri = Uri.parse(url);
        //getContentResolver().registerContentObserver(uri,true,new DownloadObserver(handler,this,downloadid));
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(true);
        // 根据文件后缀设置mime
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        int startIndex = fileName.lastIndexOf(".");
        String tmpMimeString = fileName.substring(startIndex + 1).toLowerCase();
        String mimeString = mimeTypeMap.getMimeTypeFromExtension(tmpMimeString);
        request.setMimeType(mimeString);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle(fileName);
        request.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        request.setDescription("更新下载");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        return request;
    }

    /**
     * 下载通知点击的监听器
     */
    BroadcastReceiver onNotificationClick = new BroadcastReceiver() {
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            showDownloadManagerView();
        }
    };

    /**
     * 下载完成的接收器
     */
    private class OnCompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent intent) {
            // 通过intent获取发广播的id
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Cursor c = downloadManager.query(new DownloadManager.Query().setFilterById(id));
            if (c.moveToFirst()) {
                int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
                if (downloadId==null){
                    ToastUtils.showShort("downloadId不能为null");
                }
                if (id==(Long.valueOf(downloadId)) && c.getInt(columnIndex) == DownloadManager.STATUS_SUCCESSFUL) {

                    String localFilePath = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    AppSpUtils.getInstance().put(AppSpUtils.APK_DOWNLOAD_ID,"-1");
                    DownloadUtils.installApkByGuide(DownloadService.this,localFilePath.replaceFirst("file://", ""));


//                    DownloadUtils.installApkByGuide(DownloadService.this, localFilePath);
                    stopSelf();
                }
            }
            c.close();

            //下载完成退出应用
            ActivityUtils.finishAllActivities();

        }
    }

    /**
     * 跳转到系统下载界面
     */
    private void showDownloadManagerView() {
        Intent intent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



    /**
     * 判断传入的url是否正在下载
     */
    private boolean isDownloading(String url) {
        //获取正在下载的任务队列的指针
        Cursor c = downloadManager.query(new DownloadManager.Query().setFilterByStatus(DownloadManager.STATUS_RUNNING));
        if (c.moveToFirst()) {
            //获取正在下载任务的URL
            String tmpURI = c.getString(c.getColumnIndex(DownloadManager.COLUMN_URI));
            if (tmpURI.equals(url)) {
                if (!c.isClosed()) {
                    c.close();//关闭指针
                }
                return true;
            }
        }

        if (!c.isClosed()) {
            c.close();
        }
        return false;
    }

}
