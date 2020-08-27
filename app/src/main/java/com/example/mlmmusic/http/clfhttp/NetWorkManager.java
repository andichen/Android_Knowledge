package com.example.mlmmusic.http.clfhttp;

import com.example.mlmmusic.http.serviceapi.ApiService;

import java.io.IOException;
import java.net.Proxy;

import okhttp3.CertificatePinner;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetWorkManager {

    private static NetWorkManager mInstance;
    private static Retrofit retrofit;
    private static volatile ApiService request = null;
    private static final String BASE_URL = "http://pacc.radio.cn/";

    public static NetWorkManager getInstance() {
        if (mInstance == null) {
            synchronized (NetWorkManager.class) {
                if (mInstance == null) {
                    mInstance = new NetWorkManager();
                }
            }
        }
        return mInstance;
    }

    //构造方法私有
    private NetWorkManager() {
        // 初始化okhttp

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)  //还可以对OkHttp和Retrofit添加更多的拓展。比如：需要对OkHttp添加Log可以这样写
                .addInterceptor(new Interceptor() { //添加请求头
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("mac", "f8:00:ea:10:45")
                                .addHeader("uuid", "gdeflatfgfg5454545e")
                                .addHeader("userId", "Fea2405144")
                                .addHeader("netWork", "wifi")
                                .build();
                        return chain.proceed(request);

                    }
                })
                .proxy(Proxy.NO_PROXY)//不用代理（防止代理抓包）
                .build();

        // 初始化Retrofit
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


    }

    public static ApiService getRequest() {
        if (request == null) {
            synchronized (ApiService.class) {
                request = retrofit.create(ApiService.class);
            }
        }
        return request;
    }


}

