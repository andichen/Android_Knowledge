package com.example.mlmmusic.http.service;


import com.example.mlmmusic.http.converter.UnGsonConverterFactory;

import java.io.IOException;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by clf on 2019/5/19.
 */
public class HttpMethod {

    public static final String BASE_URL = "http://pacc.radio.cn/";
    private static Retrofit retrofit;

    //构造方法私有
    private HttpMethod() {
        retrofit = new Retrofit.Builder()
                .client(genericClient())
                .addConverterFactory(UnGsonConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }


    public static <T> T createApi(Class<T> clazz) {

        return retrofit.create(clazz);
    }


    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpMethod INSTANCE = new HttpMethod();
    }

    //获取单例
    public static HttpMethod getInstance() {
        return SingletonHolder.INSTANCE;
    }


    public static OkHttpClient genericClient() {
        //loggin one
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        //loggin two
        com.example.mlmmusic.http.interceptor.HttpLoggingInterceptor httpLoggingInterceptor = new com.example.mlmmusic.http.interceptor.HttpLoggingInterceptor("clf---", true);
        httpLoggingInterceptor.setLevel(com.example.mlmmusic.http.interceptor.HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .build();


                        Response response = chain.proceed(request);
                        return response;
                    }

                }).
                        addInterceptor(logging).
//                        addInterceptor(httpLoggingInterceptor).
        connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .proxy(Proxy.NO_PROXY)//不用代理（防止代理抓包）
                .build();

        return httpClient;
    }

//    public static final String BASE_URL = "http://pacc.radio.cn/";
//    private static Retrofit retrofit;
//
//    //构造方法私有
//    private HttpMethod() {
//        retrofit = new Retrofit.Builder()
//                .client(genericClient())
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .baseUrl(BASE_URL)
//                .build();
//    }
//
//
//    public static <T> T createApi(Class<T> clazz) {
//
//        return retrofit.create(clazz);
//    }
//
//
//    //在访问HttpMethods时创建单例
//    private static class SingletonHolder {
//        private static final HttpMethod INSTANCE = new HttpMethod();
//    }
//
//    //获取单例
//    public static HttpMethod getInstance() {
//        return SingletonHolder.INSTANCE;
//    }
//
//
//    public static OkHttpClient genericClient() {
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        // set your desired log level
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient httpClient = new OkHttpClient.Builder()
////                .addInterceptor(new Interceptor() {
////                    @Override
////                    public Response intercept(Chain chain) throws IOException {
////                        Request request = chain.request()
////                                .newBuilder()
////                                .build();
////                        Response response = chain.proceed(request);
////                        return response;
////                    }
////
////                }).
//                .addInterceptor(logging).
//                        connectTimeout(10, TimeUnit.SECONDS)
//                .readTimeout(20, TimeUnit.SECONDS)
//                .proxy(Proxy.NO_PROXY)//不用代理（防止代理抓包）
//                .build();
//
//        return httpClient;
//    }

}
