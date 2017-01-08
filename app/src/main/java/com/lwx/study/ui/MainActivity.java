package com.lwx.study.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.lwx.study.R;
import com.lwx.study.bean.Result;
import com.lwx.study.bean.WeatherEntity;
import com.lwx.study.network.ApiManager;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //聚合数据：http://op.juhe.cn/onebox/weather/query?cityname=重庆&key=bcb6bfa67f8db11c66f43a92c00a6855

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                weather();
                weatherData();
            }
        });
    }

    private void weatherData() {
        Map<String, String> parameter = ApiManager.getBasicMap();
        //http://op.juhe.cn/onebox/weather/query?cityname=重庆&key=bcb6bfa67f8db11c66f43a92c00a6855
        parameter.put("cityname", "重庆");
        parameter.put("key","bcb6bfa67f8db11c66f43a92c00a6855");

        ApiManager.getApiService().weatherEn(parameter)
                .enqueue(new Callback<WeatherEntity>() {
                    @Override
                    public void onResponse(Call<WeatherEntity> call, Response<WeatherEntity> response) {
                        WeatherEntity entity = response.body();
                        if(entity.getReason().equals("successed!")){
                            Toast.makeText(MainActivity.this,"成功",Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(MainActivity.this,"失败",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherEntity> call, Throwable throwable) {
                        Toast.makeText(MainActivity.this,"失败",Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void weather() {
        Map<String, String> parameter = ApiManager.getBasicMap();
        //http://op.juhe.cn/onebox/weather/query?cityname=重庆&key=bcb6bfa67f8db11c66f43a92c00a6855
        parameter.put("cityname", "重庆");
        parameter.put("key","bcb6bfa67f8db11c66f43a92c00a6855");

        ApiManager.getApiService()
                .weather(parameter)
                .enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        Result result = response.body();
                        if(result.getReason().equals("successed!")){
                            Toast.makeText(MainActivity.this,"成功",Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(MainActivity.this,"失败",Toast.LENGTH_LONG).show();
                        }

                    }
                    @Override
                    public void onFailure(Call<Result> call, Throwable throwable) {
                        Toast.makeText(MainActivity.this,"失败",Toast.LENGTH_LONG).show();
                    }
                });
    }
}
