package com.lwx.study.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.blankj.utilcode.utils.*;
import com.lwx.study.R;
import com.lwx.study.utils.PicassoImageLoader;
import com.lwx.study.view.MyWebChomeClient;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.vise.log.ViseLog;

import java.io.File;
import java.util.ArrayList;

/**
 * description ：
 * project name：Network
 * author : Vincent
 * creation date: 2017/1/11 18:00
 *
 * @version 1.0
 */

public class WebViewTestActivity extends AppCompatActivity {

    private WebView webView;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private int IMAGE_PICKER = 100;
    private ValueCallback<Uri> mUploadFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webView = (WebView) findViewById(R.id.webview);
        setWebView();
        initPicChoose();
    }
    private void initPicChoose() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
        imagePicker.setMultiMode(false);//设置是否是多选  true多选  false单选 这个属性要设置在模式之前不然是没有效果的
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(false);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制最多为9
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状、也可以选择圆形
        imagePicker.setFocusWidth(ScreenUtils.getScreenWidth() / 10 * 7);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(ScreenUtils.getScreenWidth() / 10 * 7);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }

    private void setWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDefaultTextEncodingName("utf-8");
//        webView.addJavascriptInterface(new JsInterface(this), "jsObj");
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl("file:///android_asset/11.html");
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript:(function() { var videos = document.getElementsByTagName('video'); for(var i=0;i<videos.length;i++){videos[i].play();}})()");
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView webView, int i, String s, String s1) {
                com.blankj.utilcode.utils.ToastUtils.showLongToast("加载失败了");
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView webView, int newProgress) {
                if (newProgress == 100) {
                    //隐藏进度条
                } else {
                }
                super.onProgressChanged(webView, newProgress);
            }

            //5.0以上调用此处
            @Override
            @SuppressLint("NewApi")
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                   /*if (mUploadCallbackAboveL != null) {
                       mUploadCallbackAboveL.onReceiveValue(null);
                   }*/
                mUploadCallbackAboveL = filePathCallback;
//                    showDialog();
//                    LoadUrlActivityPermissionsDispatcher.showDialogWithCheck(LoadUrlActivity.this);
                Intent intent = new Intent(WebViewTestActivity.this, ImageGridActivity.class);
                startActivityForResult(intent, IMAGE_PICKER);


                return true;
            }

            // Andorid 4.1+
            public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture) {
                openFileChooser(uploadFile, acceptType);
            }

            // Andorid 3.0 +
            public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType) {
                mUploadFile = uploadFile;
//                    showDialog();
//                    LoadUrlActivityPermissionsDispatcher.showDialogWithCheck(LoadUrlActivity.this);
//                openFileChooser(uploadFile);

                Intent intent = new Intent(WebViewTestActivity.this, ImageGridActivity.class);
                startActivityForResult(intent, IMAGE_PICKER);

            }

            // Android 3.0
            public void openFileChooser(ValueCallback<Uri> uploadFile) {
                // Toast.makeText(WebviewActivity.this, "上传文件/图片",Toast.LENGTH_SHORT).show();
                openFileChooser(uploadFile, "");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
//                Picasso.with(this).load(images.get(0).path).into(ivShowImg);
//                Bitmap bitmap= BitmapFactory.decodeFile(images.get(0).path);
                String imgUrl=images.get(0).path;
                ViseLog.d("imgUrl-----> " + imgUrl);
                /*Drawable drawable=Drawable.createFromPath(images.get(0).path);
                ivShowImg.setImageDrawable(drawable);*/
                File file = new File(imgUrl);
                try {
                    postFile(file.getPath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
        if (resultCode == 0) {
            if (mUploadFile != null) {
                mUploadFile.onReceiveValue(null);
            }
            if (mUploadCallbackAboveL != null) {
                mUploadCallbackAboveL.onReceiveValue(null);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void postFile(String path) throws Exception {
        File file = new File(path);
        if (file.exists() && file.length() > 0) {
            Uri uri = Uri.fromFile(file);
            if (mUploadFile != null) {
                mUploadFile.onReceiveValue(uri);
                mUploadFile = null;
            }
            if (mUploadCallbackAboveL != null) {
                mUploadCallbackAboveL.onReceiveValue(new Uri[]{uri});
                mUploadCallbackAboveL = null;
            }
        } else {
            Log.i("......", "no file");
            mUploadCallbackAboveL.onReceiveValue(null);
            mUploadCallbackAboveL = null;
        }
    }

}
