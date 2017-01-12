package com.lwx.study.ui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lwx.study.R;

/**
 * description ：
 * project name：Network
 * author : Vincent
 * creation date: 2017/1/12 14:59
 *
 * @version 1.0
 */

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        //http://blog.csdn.net/guolin_blog/article/details/51763825
      /*  //获取系统顶部视图
        View decorView = getWindow().getDecorView();
        //SYSTEM_UI_FLAG_FULLSCREEN 表示全屏
        int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);
        //获取ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();*/

      //以图片作为背景的时候图片会延伸到顶部系统状态栏 隐藏本身的状态栏
       /* if (Build.VERSION.SDK_INT >= 21) {
            //设置顶部系统视图的颜色
            View decorView = getWindow().getDecorView();
            //SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN，View.SYSTEM_UI_FLAG_LAYOUT_STABLE表示系统的主体内容会占用系统状态栏的空间
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);//透明
//            getWindow().setStatusBarColor(Color.RED);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();*/

       //以下代码会隐藏底部状态栏和系统状态栏实现全屏 这种情况触摸屏幕就会显示状态栏和导航栏
      /*  View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();*/

      //以下方式底部导航栏和顶部的状态栏都会透明
      /*  if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
*/

    }


    /**
     * 这里会实现系统导航栏的透明化和底部状态栏的透明化延伸  重写此方法不需要对onCreate方法做任何操作
     * 从顶部下拉的时候会显示底部和顶部导航栏，但是2s之后会再次消失
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
