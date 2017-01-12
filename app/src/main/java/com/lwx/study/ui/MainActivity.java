package com.lwx.study.ui;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lwx.study.R;
import com.lwx.study.app.Constant;
import com.lwx.study.bean.Bean;
import com.lwx.study.bean.LoginEntity;
import com.lwx.study.bean.Result;
import com.lwx.study.bean.WeatherEntity;
import com.lwx.study.network.ApiManager;
import com.lwx.study.rx.RxBusUtil;
import com.lwx.study.utils.StatusBarUtil;
import com.lwx.study.utils.SystemUtilts;
import com.lwx.study.utils.ToastUtils;
import com.lzy.imagepicker.view.SystemBarTintManager;
import com.vise.log.ViseLog;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    //聚合数据：http://op.juhe.cn/onebox/weather/query?cityname=重庆&key=bcb6bfa67f8db11c66f43a92c00a6855
    private String url = "http://op.juhe.cn/onebox/weather/query?cityname=重庆&key=bcb6bfa67f8db11c66f43a92c00a6855";
    private Subscription sub;
    private TextView tvSub, tvWebView;
    private SystemBarTintManager tintManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStatusBar();
        getSupportActionBar().hide();
        ViseLog.d("当前Activity名称-->" +
                ((ActivityManager.RunningTaskInfo) ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE))
                        .getRunningTasks(1).get(0)).topActivity.getClassName());
        findViewById(R.id.tv_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                weather();
                weatherData();
            }
        });
        findViewById(R.id.tv_nohttp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noHttpGetWeather();
            }
        });
        findViewById(R.id.tv_nohttp_request_json).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        findViewById(R.id.tv_nohttp_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login2();
            }
        });
        tvSub = (TextView) findViewById(R.id.tv_rxbus);
        tvSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });

        sub = RxBusUtil.getInstance().bus.subscribe(new Action1() {
            @Override
            public void call(Object o) {
                ToastUtils.defaultToast2(MainActivity.this, "收到信息了，我要更新数据啦");
                tvSub.setText(((Bean) o).getNum() + "");
            }
        });//在这里接收数据

        findViewById(R.id.tv_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.blankj.utilcode.utils.ToastUtils.init(true);
                com.blankj.utilcode.utils.ToastUtils.showLongToast("......");
            }
        });
        findViewById(R.id.tv_webView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WebViewTestActivity.class));
            }
        });
        findViewById(R.id.tv_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TestActivity.class));
            }
        });
        findViewById(R.id.tv_buttom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ButtomChangeActivity.class));
            }
        });
    }

    private void setStatusBar() {
        if (SystemUtilts.getAndroidSDKVersion() > 19 || SystemUtilts.getAndroidSDKVersion() == 19) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.colorAccent), 1);//int类型的值控制透明度
        } else {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.colorAccent));
        }
    }


    /**
     * 正常登录
     */
    private void login2() {
        //第一步 请求队列 并指定并发为3
        RequestQueue queue = NoHttp.newRequestQueue(3);
        Request<String> request = NoHttp.createStringRequest(Constant.SERVICE_API_ADDRESS + "user/rainbowlogin", RequestMethod.POST);

        Map<String, String> parameter = new HashMap<>();
        parameter.put("customerAccount", "18696855784");
        parameter.put("passWord", "555555");

        request.add(parameter);
        queue.add(2, request, responseListener);

    }

    /**
     * NoHttp 提交一个表单
     */
    private void login() {
        LoginEntity loginEntity = new LoginEntity();
        loginEntity.setClient_token("fjahfalkfhalkfnalkfmaf");
        ;
        loginEntity.setClient_type("android");
        loginEntity.setCurrent_version("1.0.0");
        loginEntity.setSignature("MTg2OTY4NTU3ODQ6NTU1NTU=");

        final Gson gson = new Gson();
        String json = gson.toJson(loginEntity);

        final RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(Constant.LOGIN_BASE_URL, RequestMethod.POST);
        // 提交json字符串
        request.setDefineRequestBodyForJson(json);

        requestQueue.add(1, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, com.yolanda.nohttp.rest.Response<JSONObject> response) {
                try {
//                    ViseLog.d("result-->"+response.get().toString());
                    JSONObject resultJsobject = response.get();
                    String status = resultJsobject.getString("status");
                    if (status.equals("1")) {
                        ViseLog.d(resultJsobject);
                    } else {
                        ViseLog.d(resultJsobject);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, com.yolanda.nohttp.rest.Response<JSONObject> response) {
                ViseLog.e("error-->" + response.get());
            }

            @Override
            public void onFinish(int what) {

            }
        });


    }

    private void noHttpGetWeather() {
        // 如果要指定并发值，传入数字即可：NoHttp.newRequestQueue(3);
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.GET);
        requestQueue.add(0, request, responseListener);
    }

    private void weatherData() {
        Map<String, String> parameter = ApiManager.getBasicMap();
        //http://op.juhe.cn/onebox/weather/query?cityname=重庆&key=bcb6bfa67f8db11c66f43a92c00a6855
        parameter.put("cityname", "重庆");
        parameter.put("key", "bcb6bfa67f8db11c66f43a92c00a6855");

        ApiManager.getApiService().weatherEn(parameter)
                .enqueue(new Callback<WeatherEntity>() {
                    @Override
                    public void onResponse(Call<WeatherEntity> call, Response<WeatherEntity> response) {
                        WeatherEntity entity = response.body();
                        if (entity.getReason().equals("successed!")) {
                            Toast.makeText(MainActivity.this, "成功", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherEntity> call, Throwable throwable) {
                        Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void weather() {
        Map<String, String> parameter = ApiManager.getBasicMap();
        //http://op.juhe.cn/onebox/weather/query?cityname=重庆&key=bcb6bfa67f8db11c66f43a92c00a6855
        parameter.put("cityname", "重庆");
        parameter.put("key", "bcb6bfa67f8db11c66f43a92c00a6855");

        ApiManager.getApiService()
                .weather(parameter)
                .enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        Result result = response.body();
                        if (result.getReason().equals("successed!")) {
                            Toast.makeText(MainActivity.this, "成功", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable throwable) {
                        Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_LONG).show();
                    }
                });
    }

    /**
     * 请求监听
     */
    private OnResponseListener<String> responseListener = new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {

        }

        @Override
        public void onSucceed(int what, com.yolanda.nohttp.rest.Response<String> response) {
            switch (what) {
                case 0:
                    ViseLog.d("result-->" + response.get());
                    Gson gson = new Gson();
                    WeatherEntity weatherEntity = gson.fromJson(response.get(), WeatherEntity.class);
                    ViseLog.d("----------->" + weatherEntity.getReason());
                    break;
                case 2:
                    ViseLog.d("result-->" + response.get());
                    break;
            }

        }

        @Override
        public void onFailed(int what, com.yolanda.nohttp.rest.Response<String> response) {
            ViseLog.e("error-->" + response.get());
        }

        @Override
        public void onFinish(int what) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sub != null) {
            sub.unsubscribe();
        }
    }
}
