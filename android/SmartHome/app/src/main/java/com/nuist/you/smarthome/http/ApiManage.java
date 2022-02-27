package com.nuist.you.smarthome.http;

import android.util.Log;


import com.nuist.you.smarthome.MyApplication;
import com.nuist.you.smarthome.base.HttpUrlBase;
import com.nuist.you.smarthome.http.api.LoginApi;
import com.nuist.you.smarthome.util.NetWorkUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Http访问的管理器
 */
public class ApiManage {

    public static final String TAG = "ApiManage";
    private static ApiManage sApiManage;
    private LoginApi mLoginApi;

    private Object mObject = new Object();

    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            if (NetWorkUtil.isNetWorkAvailable(MyApplication.getInstance())) {
                int maxAge = 60;  //在线缓存一分钟可读取

                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28;//离线时缓存4周

                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };

    //私有化构造器
    private ApiManage(){

    }

    //单例
    public static ApiManage getInstance() {
        if (sApiManage == null){
            synchronized (ApiManage.class){
                sApiManage = new ApiManage();
            }
        }
        return sApiManage;
    }

    //网络的log日志添加拦截器
    private static HttpLoggingInterceptor sLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            Log.d("HttpLog", "OkHttp====message " + message);
        }
    });


    //网络类功能
    private static File sHttpCacheDiretory = new File(MyApplication.getInstance().getCacheDir(),"known");
    private static int sCacheSize = 10 * 1024 * 1024;
    private static Cache sCache = new Cache(sHttpCacheDiretory,sCacheSize);
    private static OkHttpClient sOkHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .addInterceptor(sLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
            .cache(sCache)
            .build();



    public LoginApi getChatService() {

        if (mLoginApi == null) {
            synchronized (mObject) {
                if (mLoginApi == null) {
                    mLoginApi = new Retrofit.Builder()
                            .baseUrl(HttpUrlBase.URLBASE)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(sOkHttpClient)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(LoginApi.class);
                }
            }
        }

        return mLoginApi;
    }

}
