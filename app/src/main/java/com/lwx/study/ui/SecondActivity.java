package com.lwx.study.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.lwx.study.R;
import com.lwx.study.bean.Bean;
import com.lwx.study.rx.RxBusUtil;

import java.util.Random;

import rx.Subscription;
import rx.functions.Action1;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
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
