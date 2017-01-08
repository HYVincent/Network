package com.lwx.study.app;

import android.app.Application;
import android.util.Log;

import com.vise.log.ViseLog;
import com.vise.log.inner.DefaultTree;
import com.yolanda.nohttp.NoHttp;

/**
 * description ：
 * project name：Study
 * author : Vincent
 * creation date: 2017/1/7 11:30
 *
 * @version 1.0
 */

public class App extends Application {
    private static App app;


    @Override
    public void onCreate() {
        super.onCreate();
        initNoHttp();
        initLogs();
    }

    private void initNoHttp() {
        NoHttp.initialize(this, new NoHttp.Config()
                // 设置全局连接超时时间，单位毫秒
                .setConnectTimeout(30 * 1000)
                // 设置全局服务器响应超时时间，单位毫秒
                .setReadTimeout(30 * 1000)
        );
    }

    private void initLogs() {
        ViseLog.getLogConfig()
                .configAllowLog(true)//是否输出日志
                .configShowBorders(true)//是否排版显示
                .configTagPrefix("ViseLog")//设置标签前缀
                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}")//个性化设置标签，默认显示包名
                .configLevel(Log.VERBOSE);//设置日志最小输出级别，默认Log.VERBOSE
        ViseLog.plant(new DefaultTree());//添加打印日志信息到Logcat的树
    }

    /**
     * 返回一个Application的实例
     *
     * @return
     */
    public static synchronized Application getApp() {
        return app;
    }
}