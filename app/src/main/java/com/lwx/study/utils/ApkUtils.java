package com.lwx.study.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * description ：
 * project name：Rainbow
 * author : Vincent
 * creation date: 2016/12/30 13:55
 *
 * @version 1.0
 */

public class ApkUtils {

    /**
     * 安装 apk 文件
     *
     * @param apkFile
     */
    public static void installApk(Context context,File apkFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.lwx.study.fileprovider", apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (context.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
            context.startActivity(intent);
        }
    }
}
