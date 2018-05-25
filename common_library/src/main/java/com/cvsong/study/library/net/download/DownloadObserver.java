package com.cvsong.study.library.net.download;

import android.app.DownloadManager;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;

import com.cvsong.study.library.util.utilcode.util.ToastUtils;
import com.cvsong.study.library.util.utilcode.util.Utils;

/**
 * 下载观察者
 */
public class DownloadObserver extends ContentObserver {
    /**
     * 下载编号
     */
    private Long downloadId;
    /**
     * 处理者
     */
    private Handler handler;


    /**
     * 构造函数
     *
     * @param handler
     * @param downloadId
     */
    public DownloadObserver(Handler handler, Long downloadId) {
        super(handler);
        this.handler = handler;
        this.downloadId = downloadId;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);

        // 实例化查询类，这里需要一个刚刚的downloadId
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        DownloadManager downloadManager = (DownloadManager) Utils.getApp().getSystemService(Context.DOWNLOAD_SERVICE);

        if (downloadManager == null) {
            ToastUtils.showShort("系统下载管理器异常");
            return;
        }
        // 数据库查询
        Cursor cursor = downloadManager.query(query);
        while (cursor.moveToNext()) {
            int download_so_far = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
            int allNeedDownload = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
            // 进度 = 已下载字节数 / 总字节数
            int mProgress = (download_so_far * 100) / allNeedDownload;

            // 获得Message并赋值
            Message msg = Message.obtain();
            msg.what = 1001;
            msg.obj = mProgress;
            if (handler != null) {
                handler.sendMessage(msg);
            }

        }
        cursor.close();
    }
}
