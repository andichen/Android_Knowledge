package com.example.mlmmusic.draggrecycle;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mlmmusic.R;
import com.example.mlmmusic.adapter.DraggExistAdapter;
import com.example.mlmmusic.adapter.FunctionAdapter;
import com.example.mlmmusic.base.BaseActivity;
import com.example.mlmmusic.beans.CeshiTextBean;
import com.example.mlmmusic.beans.FunctionItemsBean;
import com.example.mlmmusic.util.BeanUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DraggRecycleviewActivity extends BaseActivity {

    @BindView(R.id.titletext)
    TextView titletext;
    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.title)
    RelativeLayout title;
    @BindView(R.id.recyclerViewExist)
    RecyclerView recyclerViewExist;
    @BindView(R.id.rg_tab)
    RadioGroup rgTab;
    @BindView(R.id.horizonLScrollView)
    HorizontalScrollView horizonLScrollView;
    @BindView(R.id.recyclerViewAll)
    RecyclerView recyclerViewAll;
    private JSONArray jsonArray;
    private DraggExistAdapter draggExistAdapter;
    private List<String> stringList = new ArrayList<>();
    private String mystring = "充值中心,信用卡还款,生活缴费,城市服务,生活号,我的客服,我的快递";


    private static String MyData = "我的应用";
    private String selectData;
    private CeshiTextBean ceshiTextBean;
    private CustomItemTouchCallback itemTouchHelper;
    private FunctionAdapter functionAdapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dragg_recycleview);
        stringList.add("充值中心");
        stringList.add("信用卡还款");
        stringList.add("生活缴费");
        stringList.add("城市服务");
        stringList.add("生活号");
        stringList.add("我的客服");
        stringList.add("我的快递");
        ButterKnife.bind(this);
        setTitle("仿支付宝应用编辑");

        init();
        addListener();
    }

    private void init() {
        //sp保存
        SharedPreferences spMyData = getSharedPreferences(MyData, Context.MODE_PRIVATE);
        selectData = spMyData.getString("selectData", "");
        Gson gson = new Gson();
        if ("".equals(selectData)) {
            String ceshi = BeanUtil.readAssetsTxt(this, "ceshi");
            List<CeshiTextBean> ceshiAllTextBean = gson.fromJson(ceshi, new TypeToken<List<CeshiTextBean>>() {
            }.getType());
            ceshiTextBean = ceshiAllTextBean.get(0);  //吧第一个作为固定的编辑页
            List<FunctionItemsBean> functionItems = ceshiTextBean.getFunctionItems();
            selectData = gson.toJson(functionItems);
            spMyData.edit().putString("selectData", selectData).apply();
        }

        List<FunctionItemsBean> functionItemsBeans = gson.fromJson(selectData, new TypeToken<List<FunctionItemsBean>>() {
        }.getType());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewExist.setLayoutManager(gridLayoutManager);


        draggExistAdapter = new DraggExistAdapter(this, functionItemsBeans, mystring);
        recyclerViewExist.setAdapter(draggExistAdapter);

        //拖拽
        itemTouchHelper = new CustomItemTouchCallback(draggExistAdapter);
        itemTouchHelper.isDrag(false);

        DefaultItemTouchHelper defaultItemTouchHelper = new DefaultItemTouchHelper(itemTouchHelper);
        defaultItemTouchHelper.attachToRecyclerView(recyclerViewExist);


        //底部recycleview
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 4);
        gridLayoutManager2.setOrientation(RecyclerView.VERTICAL);
        functionAdapter = new FunctionAdapter(this,functionItemsBeans);
        recyclerViewAll.setLayoutManager(gridLayoutManager2);
        recyclerViewAll.setAdapter(functionAdapter);


    }

    private void addListener() {
        draggExistAdapter.setOnItemClickListener(new DraggExistAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(List<FunctionItemsBean> functionItemsBeans, int position) {
                draggExistAdapter.notifyDataSetChanged();
            }
        });
    }


    @OnClick(R.id.submit)
    public void onViewClicked() {
        if (draggExistAdapter != null && functionAdapter != null) {
            if (submit.getText().toString().equals("编辑")) {
                //
                itemTouchHelper.isDrag(true);
                submit.setText("完成");
                draggExistAdapter.setEdit(true);
                functionAdapter.setEdit(true);


            } else {
                itemTouchHelper.isDrag(false);
                submit.setText("编辑");
                draggExistAdapter.setEdit(false);
                functionAdapter.setEdit(false);
            }

        }


    }
}
