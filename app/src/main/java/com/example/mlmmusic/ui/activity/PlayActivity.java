package com.example.mlmmusic.ui.activity;

import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

//import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mlmmusic.Constants;
import com.example.mlmmusic.MusicService;
import com.example.mlmmusic.R;
import com.example.mlmmusic.base.BaseMusicActivity;
import com.example.mlmmusic.beans.RXBusBean;
import com.example.mlmmusic.util.CommonUtil;
import com.example.mlmmusic.util.RxBus;
import com.example.mlmmusic.util.Trace;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayActivity extends BaseMusicActivity {

    @BindView(R.id.iv_point)
    ImageView ivPoint;
    @BindView(R.id.rl_cd)
    RelativeLayout rlCd;
    @BindView(R.id.tv_nowtime)
    TextView tvNowTime;
    @BindView(R.id.tv_totaltime)
    TextView tvTotalTime;
    @BindView(R.id.seek_bar)
    SeekBar seekBar;
    @BindView(R.id.iv_status)
    ImageView ivStatus;
    @BindView(R.id.iv_center)
    ImageView ivCenter;
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.pb_load)
    ProgressBar pbLoad;
    @BindView(R.id.tv_subtitle)
    TextView tvSubTitle;
    @BindView(R.id.tv_tip)
    TextView tvTip;

    private Animation animation;
    private ValueAnimator pointAnimator;
    private ValueAnimator cdAnimator;
    private LinearInterpolator lin;
    private NetworkChangeReceiver networkChangeReceiver;
//    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
//        EventBus.getDefault().register(this);

        setTitleTrans(R.color.color_trans);
        setTitleLine(R.color.color_trans);
        setBackView();
        setRightView(R.drawable.svg_menu_white);
        setTitle("标题");

        lin = new LinearInterpolator();
        initPointAnimat(); //初始化指针动画
        initCDAnimat(); //初始化CD动画
        Intent intent = new Intent(this, MusicService.class);
        intent.putExtra("url", getPlayBean().getUrl());
        startService(intent);
        Glide.with(this).load(getPlayBean().getImg()).apply(RequestOptions.placeholderOf(R.mipmap.icon_cd_default_bg)).into(ivCenter);
        Glide.with(this).load(R.mipmap.icon_gray_bg)
//                .apply(bitmapTransform(new BlurTransformation(25, 3)).placeholder(R.mipmap.icon_gray_bg))
                .into(ivBg);
        RxBus.getInstance().post(new RXBusBean(Constants.RxBusTag.DEMO, "chen", null));


        //系统广播 不需要主观发送广播了
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);

        //普通广播
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("普通广播");
//        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter2);
        Intent intent1 = new Intent();
        intent1.setAction("普通广播");
        intent1.putExtra("name", "normal broadcasr");
        sendBroadcast(intent1);
//        sendBroadcast(new Intent("普通广播")); //或者无携带数据

        //注册应用内广播接收器
        //Android v4兼容包中给出了封装好的LocalBroadcastManager类，用于统一处理App应用内的广播问题，使用方式上与通常的全局广播几乎相同，只是注册/取消注册广播接收器和发送广播时将主调context变成了LocalBroadcastManager的单一实例。
//        IntentFilter intentFilter4 = new IntentFilter();
//        intentFilter4.addAction("注册应用内广播接收器");
//        localBroadcastManager = LocalBroadcastManager.getInstance(this);
//        localBroadcastManager.registerReceiver(networkChangeReceiver, intentFilter4);
//
//        localBroadcastManager.sendBroadcast(new Intent("注册应用内广播接收器"));

        //自定义广播
//        sendBroadcast(new Intent("aaaaa"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
            }

            Bundle extras = intent.getExtras();
            String stringExtra = intent.getStringExtra("name"); //还可以携带数据
            Trace.I("NetworkChangeReceiver", intent.getAction() + " __stringExtra:" + stringExtra);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 初始化指针动画
     */
    private void initPointAnimat() {
        ivPoint.setPivotX(CommonUtil.dip2px(PlayActivity.this, 17)); //设置轴点
        ivPoint.setPivotY(CommonUtil.dip2px(PlayActivity.this, 15));
        pointAnimator = ValueAnimator.ofFloat(0, 0);  //属性动画
        pointAnimator.setTarget(ivPoint);
        pointAnimator.setRepeatCount(0);
        pointAnimator.setDuration(300);
        pointAnimator.setInterpolator(lin);
        pointAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float current = (Float) animation.getAnimatedValue();
                ivPoint.setRotation(current);
            }
        });
        pointAnimator.setFloatValues(0f, -40f);
        pointAnimator.start();
    }

    /**
     * 初始化CD动画
     */
    private void initCDAnimat() {
//        cdAnimator = ValueAnimator.ofFloat(rlCd.getRotation(), 360f + rlCd.getRotation());  //使之不中断（用于暂停 播放）
        cdAnimator = ValueAnimator.ofFloat(0f, 360f);
        cdAnimator.setTarget(rlCd);
        cdAnimator.setRepeatCount(ValueAnimator.INFINITE);
        cdAnimator.setDuration(15000);
        cdAnimator.setInterpolator(lin);
        cdAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float current = (Float) animation.getAnimatedValue();
//                setCdRodio(current);
                rlCd.setRotation(current);
            }
        });
        cdAnimator.start();
    }


    @OnClick(R.id.iv_point)
    public void onViewClicked() {

    }


}
