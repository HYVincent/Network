package com.lwx.study.network.api;

import com.lwx.study.bean.Result;
import com.lwx.study.bean.WeatherEntity;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @className：ApiManagerService
 */
public interface ApiService {

	/**
	 * retrofit rxjava模式
	 * @return
	 */
	@GET("weather/query")
	Observable<WeatherEntity> getWeather(@QueryMap Map<String, String> option);

	@GET("weather/query")
	Call<Result> weather(@QueryMap Map<String,String> paramster);

	@GET("weather/query")
	Call<WeatherEntity> weatherEn(@QueryMap Map<String,String> paramster);

}
