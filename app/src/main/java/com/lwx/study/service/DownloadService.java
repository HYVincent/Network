package com.lwx.study.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.lwx.study.R;
import com.lwx.study.utils.ApkUtils;
import com.lwx.study.utils.NotificationUtil;
import com.othershe.dutil.DUtil;
import com.othershe.dutil.callback.SimpleDownloadCallback;
import com.othershe.dutil.download.DownloadManger;

import java.io.File;

public class DownloadService extends Service {
    private Context mContext;

    private DownloadBinder mBinder = new DownloadBinder();

  public class DownloadBinder extends Binder {

        public void startDownload(String path, final String name, String url, final int notifyId) {
            if(TextUtils.isEmpty(path)||TextUtils.isEmpty(name)||TextUtils.isEmpty(url)||notifyId==0){
                new NullPointerException("参数不为空");
                return;
            }
            DUtil.init(mContext)
                    .path(path)
                    .name(name)
                    .url(url)
                    .childTaskCount(3)
                    .build()
                    .start(new SimpleDownloadCallback() {
                        @Override
                        public void onStart(long currentSize, long totalSize, float progress) {
                            NotificationUtil.createProgressNotification(mContext, name, "出行必备...", R.mipmap.ic_launcher, notifyId);
                        }

                        @Override
                        public void onProgress(long currentSize, long totalSize, float progress) {
                            NotificationUtil.updateNotification(notifyId, progress);
                        }

                        @Override
                        public void onFinish(File file) {
                            NotificationUtil.cancelNotification(notifyId);
                            Toast.makeText(mContext, "下载完成", Toast.LENGTH_SHORT).show();
                            ApkUtils.installApk(getApplicationContext(),file);
                        }

                        @Override
                        public void onWait() {

                        }
                    });
        }

        public void pauseDownload(String url) {
            DownloadManger.getInstance(mContext).pause(url);
        }

        public void resumeDownload(String url) {
            DownloadManger.getInstance(mContext).resume(url);
        }

        public void cancelDownload(String url) {
            DownloadManger.getInstance(mContext).cancel(url);
        }

        public void restartDownload(String url) {
            DownloadManger.getInstance(mContext).restart(url);
        }

        public float getProgress(String url) {
            if (DownloadManger.getInstance(mContext).getCurrentData(url) != null) {
                return DownloadManger.getInstance(mContext).getCurrentData(url).getPercentage();
            }
            return -1;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
