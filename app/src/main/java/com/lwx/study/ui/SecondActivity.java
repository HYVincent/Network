package com.lwx.study.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lwx.study.R;
import com.lwx.study.bean.Bean;
import com.lwx.study.rx.RxBusUtil;
import com.lwx.study.utils.StatusBarUtil;

import java.util.Random;

import static com.blankj.utilcode.utils.BarUtils.getStatusBarHeight;


/**
 * description ：
 * project name：Network
 * author : Vincent
 * creation date: 2017/1/9 11:14
 *
 * @version 1.0
 */

public class SecondActivity extends AppCompatActivity {

    private TextView tv;

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucent(this);
        setContentView(R.layout.activity_second);
        getSupportActionBar().hide();
        tv = (TextView)findViewById(R.id.tv_click);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                RxBusUtil.getInstance().post(new Bean("B_Activity",random.nextInt(10000))); //发送一个对象
                finish();
            }
        });
    }
}
