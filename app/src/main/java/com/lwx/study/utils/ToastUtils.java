package com.lwx.study.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.lwx.study.app.App;

/**
 * description ：
 * project name：Network
 * author : Vincent
 * creation date: 2017/1/9 12:29
 *
 * @version 1.0
 */

public class ToastUtils  {

    private static Toast toast;

    public static void defaultToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }
    public static void defaultToast2(Context context,String msg){
        if(toast==null){
            toast = Toast.makeText(context,msg,Toast.LENGTH_LONG);
        }
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

}
