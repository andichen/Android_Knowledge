package com.example.mlmmusic.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.mlmmusic.beans.PlayBean;
import com.example.mlmmusic.log.MyLog;

import org.greenrobot.eventbus.EventBus;

public class BaseMusicActivity extends BaseActivity {

    private static PlayBean playBean;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EventBus.getDefault().register(this);
    }
    public PlayBean getPlayBean() {
        if(playBean == null)
        {
            playBean = new PlayBean();
        }
        MyLog.d("url is :" + playBean.getUrl());
        return playBean;
    }
}
