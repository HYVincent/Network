package com.lwx.study.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.utils.ToastUtils;
import com.lwx.study.R;
import com.werb.pickphotoview.BaseActivity;
import com.werb.pickphotoview.PickPhotoView;
import com.werb.pickphotoview.util.PickConfig;

import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * description ：
 * project name：Network
 * author : Vincent
 * creation date: 2017/1/17 22:31
 *
 * @version 1.0
 */
@RuntimePermissions
public class SelectImgActivity extends BaseActivity {

    private ImageView ivShowImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_img);
        ivShowImg = (ImageView)findViewById(R.id.iv_show_img);
        findViewById(R.id.tv_select_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImgActivityPermissionsDispatcher.selectImgWithCheck(SelectImgActivity.this);
            }
        });
    }

    @NeedsPermission({Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void  selectImg(){
        new PickPhotoView.Builder(SelectImgActivity.this)
                .setPickPhotoSize(1)   //select max size
                .setShowCamera(true)   //is show camera
                .setSpanCount(4)       //SpanCount  内容间距
                .start();
    }
    @OnShowRationale({Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showRationaleForGetImg(PermissionRequest request) {
        showRationaleDialog("老子要权限", request);
    }

    @OnPermissionDenied({Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void turnGetImg() {
        ToastUtils.showLongToast("不给就滚蛋");
    }

    @OnNeverAskAgain({Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void juJueAndNoTiShi() {
        ToastUtils.showLongToast("滚");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        SelectImgActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showRationaleDialog(String messageResId, final PermissionRequest request) {
        new android.support.v7.app.AlertDialog.Builder(SelectImgActivity.this)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(messageResId)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 0){
            return;
        }
        if(data == null){
            return;
        }
        if (requestCode == PickConfig.PICK_PHOTO_DATA) {
            List<String> selectPaths = (List<String>) data.getSerializableExtra(PickConfig.INTENT_IMG_LIST_SELECT);
            if(selectPaths.size()>0&&selectPaths!=null){
                // do something u want
                Drawable imgDrawable = Drawable.createFromPath(selectPaths.get(0));
                ivShowImg.setImageDrawable(imgDrawable);
            }else {
                ToastUtils.showLongToast("图片路径为空");
            }
        }
    }

}
