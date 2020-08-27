package com.example.mlmmusic;

import android.app.Application;

import com.example.mlmmusic.http.clfhttp.NetWorkManager;

/**
 * Created by ywl on 2018-1-13.
 */

public class MyApplication extends Application {

    private static MyApplication instance;
    private Long token;

    //Application全局只有一个，它本身就已经是单例了，无需再用单例模式去为它做多重实例保护了
    public static MyApplication getInstance()
    {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
//        NetWorkManager.getInstance();
    }

    public String getToken() {
        return String.valueOf(token);
    }

    public void setToken(Long token) {
        this.token = token;
    }
}
