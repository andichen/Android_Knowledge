package com.example.mlmmusic.ui.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mlmmusic.R;
import com.example.mlmmusic.base.BaseActivity;
import com.example.mlmmusic.beans.LiveChannelBean;
import com.example.mlmmusic.http.serviceapi.SubjectApi;
import com.example.mlmmusic.http.subscribers.HttpSubscriber;
import com.example.mlmmusic.http.subscribers.SubscriberOnListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseRecyclerViewAdapterHelperActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swipRefresh)
    SwipeRefreshLayout swipRefresh;
    private int pageSize = 15;
    private int currentPage = 1;
    private String channelPlaceId = "3225";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_recycler_view_adapter_helper);
        ButterKnife.bind(this);


        getLiveList2();  //优化网络请求的请求
    }

    private void getLiveList2() {
        SubjectApi.getInstance().getLiveList("", channelPlaceId, pageSize, currentPage, new HttpSubscriber<List<LiveChannelBean>>(new SubscriberOnListener<List<LiveChannelBean>>() {

            @Override
            public void onSucceed(List<LiveChannelBean> data) {

            }

            @Override
            public void onError(int code, String msg) {

            }
        }, this));


    }
}
