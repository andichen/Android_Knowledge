package com.example.mlmmusic;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.keepalivelibrary.KeepLive;
import com.example.keepalivelibrary.config.ForegroundNotification;
import com.example.keepalivelibrary.config.ForegroundNotificationClickListener;
import com.example.keepalivelibrary.config.KeepLiveService;
import com.example.mlmmusic.ui.activity.PlayActivity;
import com.example.mlmmusic.adapter.HeaderAndFooterAdapter;
import com.example.mlmmusic.adapter.LiveListAdapter;
import com.example.mlmmusic.base.BaseMusicActivity;
import com.example.mlmmusic.base.LiveListBean;
import com.example.mlmmusic.beans.BlueToothBean;
import com.example.mlmmusic.beans.LiveChannelBean;
import com.example.mlmmusic.beans.PlaceBean;
import com.example.mlmmusic.beans.RXBusBean;
import com.example.mlmmusic.dialog.BlueToothDialog;
import com.example.mlmmusic.dialog.LocalTypeDialog;
import com.example.mlmmusic.http.clfhttp.NetWorkManager;
import com.example.mlmmusic.http.serviceapi.SubjectApi;
import com.example.mlmmusic.http.subscribers.HttpSubscriber;
import com.example.mlmmusic.http.subscribers.SubscriberOnListener;
import com.example.mlmmusic.log.MyLog;
import com.example.mlmmusic.util.RxBus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseMusicActivity {


    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swipRefresh)
    SwipeRefreshLayout swipRefresh;
    @BindView(R.id.tvblue_tooth)
    TextView tvblue_tooth;

    private TextView tvLoadMsg;
    private int pageSize = 15;
    private int currentPage = 1;
    private String channelPlaceId = "3225";   //代表中央频道
    private boolean isLoading = false;

    private List<PlaceBean> placeBeans;
    public static String liveUrl = "";//直播url


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 启动保活
//        if (PrivacySettingHelper.getPrivacySettings(this).getIsKeepalive() == 1) {
//            initKeepLive();
//        }
//        initKeepLive();
        // 注册这个 BroadcastReceiver蓝牙用的
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);

//        ButterKnife.bind(this);
        setTheme(R.style.AppTheme_Launcher);
        setTitle("钱大掌柜");
        setRightView(R.mipmap.icon_more);
        showDadaLoad();
        initAdapter();
        swipRefresh.setColorSchemeColors(getResources().getColor(R.color.color_ec4c48));
        swipRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isLoading) {
                    isLoading = true;
                    currentPage = 1;
                    getLiveList2();
                }
            }
        });
        getLiveList();  //没封装的请求(很简单的封装)
        getLiveList2();  //优化网络请求的请求
        getLocalData(); //

        //RxBus 事件处理
        RxBus.getInstance().subscribe(RXBusBean.class, new Consumer<RXBusBean>() {

            @Override
            public void accept(RXBusBean busBean) throws Exception {
                if (busBean != null && busBean.getFlagId() == Constants.RxBusTag.DEMO) {
                    Log.i("clf_mainactivity", busBean.getNickName());
                }

            }
        });

    }

    private ArrayList<RxBus> rxBusList = new ArrayList<>();

    private List<LiveChannelBean> datas;
    private LiveListAdapter liveListAdapter;
    private HeaderAndFooterAdapter headerAndFooterAdapter;

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
                startSystemActivity(MainActivity.this, PlayActivity.class);
            }
        });
        headerAndFooterAdapter = new HeaderAndFooterAdapter(liveListAdapter);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);

        View footerView = LayoutInflater.from(this).inflate(R.layout.footer_layout, recyclerview, false);
        tvLoadMsg = footerView.findViewById(R.id.tv_loadmsg);
        headerAndFooterAdapter.addFooter(footerView);

        recyclerview.setAdapter(headerAndFooterAdapter);
        headerAndFooterAdapter.notifyDataSetChanged();

        headerAndFooterAdapter.setOnloadMoreListener(recyclerview, new HeaderAndFooterAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!isLoading) {
                    if (hasLoadMsg) {
                        getLiveList2();
                    }
                }
            }

            @Override
            public void onClickLoadMore() { //点击footview的加载更多
                if (!isLoading) {
                    getLiveList2();
                }
            }

            @Override
            public void onItemClick(View view, int position) {

            }
        });
    }

    private boolean hasLoadMsg = true;
    private void getLiveList2() {
        tvLoadMsg.setText("正在加载...");
        SubjectApi.getInstance().getLiveList("", channelPlaceId, pageSize, currentPage, new HttpSubscriber<List<LiveChannelBean>>(new SubscriberOnListener<List<LiveChannelBean>>() {

            @Override
            public void onSucceed(List<LiveChannelBean> data) {
                hasLoadMsg = true;
                if (data != null) {
                    if (currentPage == 1) {
                        datas.clear();
                    }
                    if (data.size() == 0) {
                        tvLoadMsg.setText("没有更多了");
                        hasLoadMsg = false;

                    } else {
                        tvLoadMsg.setText("加载更多");
                        currentPage++;
                    }
                    if (data.size() > 0) {
                        datas.addAll(data);
                    }
                    if (datas.size() < pageSize) {
                        tvLoadMsg.setVisibility(View.GONE);
                    } else {
                        tvLoadMsg.setVisibility(View.VISIBLE);
                    }
                    headerAndFooterAdapter.notifyDataSetChanged();


                }

                hideDataLoad();
                isLoading = false;
                swipRefresh.setRefreshing(false);
            }

            @Override
            public void onError(int code, String msg) {
                isLoading = false;
                swipRefresh.setRefreshing(false);
                MyLog.d(msg);
                showToast(msg);
                hideDataLoad();

            }
        }, this));


    }


    private void getLocalData() {
        SubjectApi.getInstance().getLivePlace("", new HttpSubscriber<>(new SubscriberOnListener<List<PlaceBean>>() {
            @Override
            public void onSucceed(List<PlaceBean> data) {
                if (data != null) {
                    placeBeans = new ArrayList<>();
                    placeBeans.addAll(data);
                }
            }

            @Override
            public void onError(int code, String msg) {
                hideDataLoad();

            }

        }, this));

    }

    private LocalTypeDialog localTypeDialog;

    @Override
    public void onClickMenu() {
        super.onClickMenu();
        if (placeBeans != null) {
            if (localTypeDialog == null) { //不用每次都创建了
                localTypeDialog = new LocalTypeDialog(this);
                localTypeDialog.show();
                localTypeDialog.setDatas(placeBeans, channelPlaceId);
                localTypeDialog.setOnClickYes(new LocalTypeDialog.onClickYes() {
                    @Override
                    public void clickYes(PlaceBean selectPlaceBean) {
                        //一：这种也可以
//                    showDadaLoad();
//                    channelPlaceId = selectPlaceBean.getId();
//                    currentPage = 1;
//                    getLiveList2();
                        //二：这种也可以
                        channelPlaceId = selectPlaceBean.getId();
                        swipRefresh.post(new Runnable() {
                            @Override
                            public void run() {
                                swipRefresh.setRefreshing(true);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (!isLoading) {
                                            isLoading = true;
                                            currentPage = 1;
                                            getLiveList2();
                                        }
                                    }
                                }, 500);
                            }
                        });
                    }
                });
            } else {
                localTypeDialog.show();
                localTypeDialog.setDatas(placeBeans, channelPlaceId);
            }
        } else {
            getLocalData(); //从新请求
        }
    }


    private LiveListBean liveListBean;

    @SuppressLint("CheckResult")
    private void getLiveList() {
        NetWorkManager.getInstance(); //这个可以在MyAllpication中初始化
        //没封装的请求
        NetWorkManager.getRequest().getLiveByParam("", channelPlaceId, pageSize, currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LiveListBean>() {
                    @Override
                    public void accept(LiveListBean liveListBean) throws Exception {
                        if (liveListBean == null) {
                            return;
                        }
                        List<LiveListBean.LiveChannelBean> liveChannel = liveListBean.getLiveChannel();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        int a = 0;

                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

//        for (int i = 0; i < rxBusList.size(); i++) {
//            rxBusList.get(i).unSubcribe();
//        }
    }

    @OnClick(R.id.tvblue_tooth)
    public void onViewClicked() {
        BluetoothDemo();
    }

    private BluetoothAdapter bluetoothAdapter;
    private BlueToothBean blueToothBean;
    private static int REQUEST_ENBLE_BT = 0x001;
    private List<BlueToothBean> blueToothBeans = new ArrayList<>();


    private void BluetoothDemo() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            //说明此设备不支持蓝牙
            return;
        }
        if (!bluetoothAdapter.isEnabled()) {
            //如果没有开启蓝牙,提示用户让他开启
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            startActivityForResult(intent, REQUEST_ENBLE_BT);
            return;
        }
        Log.i("clf", "开启了蓝牙");

        //获取蓝牙信息列表（查询配对的设备）
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        blueToothBean = new BlueToothBean();
        if (bondedDevices.size() > 0) {
//            for (BluetoothDevice device : bondedDevices) {
//                String name = device.getName();
//            }
            blueToothBeans.clear();
            Iterator<BluetoothDevice> it = bondedDevices.iterator();
            while (it.hasNext()) {
                BluetoothDevice device
                        = it.next();
                blueToothBean.setAddress(device.getAddress() + "");
                blueToothBean.setName(device.getName() + "");
                blueToothBean.setBondState(device.getBondState() + "");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    blueToothBean.setType(device.getType() + "");
                }
                blueToothBeans.add(blueToothBean);
            }
        }
        BlueToothDialog blueToothDialog = new BlueToothDialog(this);
        blueToothDialog.show();
        blueToothDialog.setData(blueToothBeans);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENBLE_BT) {
//            BluetoothDemo();//缺陷 如果拒绝了 会无限提示开启蓝牙这个，所以要用广播来做一个监测
        }
    }

    // 创建一个接受 ACTION_FOUND 的 BroadcastReceiver
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // 当 Discovery 发现了一个设备
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // 从 Intent 中获取发现的 BluetoothDevice
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // 将名字和地址放入要显示的适配器中
                Log.i("clf_device:", device.getName());
//                mArrayAdapter.add(device.getName + "\n" + device.getAddress());

            }
        }
    };
    private void initKeepLive() {
        // 定义前台服务的默认样式。即标题、描述和图标
        ForegroundNotification foregroundNotification = new ForegroundNotification("IM核心", "进程守护中", R.mipmap.icon,
                //定义前台服务的通知点击事件
                new ForegroundNotificationClickListener() {
                    @Override
                    public void foregroundNotificationClick(Context context, Intent intent) {

                    }
                });
        //启动保活服务
        KeepLive.startWork(MainActivity.this, KeepLive.RunMode.ROGUE, foregroundNotification,
                //你需要保活的服务，如socket连接、定时任务等，建议不用匿名内部类的方式在这里写
                new KeepLiveService() {
                    /**
                     * 运行中
                     * 由于服务可能会多次自动启动，该方法可能重复调用
                     */
                    @Override
                    public void onWorking() {
                        Log.e("xuan", "onWorking: ");
                    }

                    /**
                     * 服务终止
                     * 由于服务可能会被多次终止，该方法可能重复调用，需同onWorking配套使用，如注册和注销broadcast
                     */
                    @Override
                    public void onStop() {
                        Log.e("xuan", "onStop: ");
                    }
                }
        );
    }


    // 在 onDestroy 中 unRegister
}


