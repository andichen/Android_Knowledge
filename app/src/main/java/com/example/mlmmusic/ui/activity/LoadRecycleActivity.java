package com.example.mlmmusic.ui.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.clf.recycleloadlibrary.container.DefaultFooter;
import com.clf.recycleloadlibrary.container.DefaultHeader;
import com.clf.recycleloadlibrary.widget.SpringView;
import com.example.mlmmusic.R;
import com.example.mlmmusic.adapter.LiveListAdapter;
import com.example.mlmmusic.base.BaseMusicActivity;
import com.example.mlmmusic.beans.LiveChannelBean;
import com.example.mlmmusic.http.serviceapi.SubjectApi;
import com.example.mlmmusic.http.subscribers.HttpSubscriber;
import com.example.mlmmusic.http.subscribers.SubscriberOnListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LoadRecycleActivity extends BaseMusicActivity {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.springview)
    SpringView springView;

    private int pageSize = 15;
    private int currentPage = 1;
    private String channelPlaceId = "3225";   //代表中央频道

    private List<LiveChannelBean> datas;
    private LiveListAdapter liveListAdapter;
    public static String liveUrl = "";//直播url


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_recycle);
        setTitle("钱大掌柜");
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() { //下啦刷新
//                new Handler().postDelayed(() -> springView.onFinishFreshAndLoad(), 1000);
                currentPage = 1;
                getLiveList2(true);

            }

            @Override
            public void onLoadmore() { //上啦
//                new Handler().postDelayed(() -> springView.onFinishFreshAndLoad(), 1000);
                getLiveList2(false);


            }
        });
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
        initAdapter();
        getLiveList2(false);
    }

    private void initAdapter() {
        datas = new ArrayList<>();
        liveListAdapter = new LiveListAdapter(this, datas);
        liveListAdapter.setOnItemClickListeren(new LiveListAdapter.OnItemClickListeren() {
            @Override
            public void onItemClick(LiveChannelBean liveChannelBean, int position) {

                Log.i("clf position: ", position + "");

                getPlayBean().setName(liveChannelBean.getName());
                getPlayBean().setSubname(liveChannelBean.getLiveSectionName());
                getPlayBean().setChannelId(liveChannelBean.getId());
                getPlayBean().setImg(liveChannelBean.getImg());
                getPlayBean().setUrl(liveChannelBean.getStreams().get(0).getUrl());
                getPlayBean().setTiming(1);
                liveUrl = liveChannelBean.getStreams().get(0).getUrl();//记录当前直播url
                startSystemActivity(LoadRecycleActivity.this, PlayActivity.class);
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setAdapter(liveListAdapter);

    }

    private void getLiveList2(boolean uptodate) {
        SubjectApi.getInstance().getLiveList("", channelPlaceId, pageSize, currentPage, new HttpSubscriber<List<LiveChannelBean>>(new SubscriberOnListener<List<LiveChannelBean>>() {

            @Override
            public void onSucceed(List<LiveChannelBean> data) {

                if (data != null) {

                    if (currentPage == 1) {
                        datas.clear();
                    }
                    if (data.size() == 0) {

                    } else {
                        currentPage++;
                    }
                    if (data.size() > 0) {
                        datas.addAll(data);
                    }
                    liveListAdapter.notifyDataSetChanged();

                    springView.onFinishFreshAndLoad();

                }

            }

            @Override
            public void onError(int code, String msg) {
                if (uptodate) {
                    springView.onFinishFreshAndLoad();
                }


            }
        }, this));


    }

}
