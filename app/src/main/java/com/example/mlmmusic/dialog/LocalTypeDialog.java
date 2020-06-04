package com.example.mlmmusic.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mlmmusic.R;
import com.example.mlmmusic.adapter.LocalAdapter;
import com.example.mlmmusic.base.BaseDialog;
import com.example.mlmmusic.beans.PlaceBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ywl on 2018/1/15.
 */

public class LocalTypeDialog extends BaseDialog {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private List<PlaceBean> datas;
    private LocalAdapter localAdapter;
    private PlaceBean selectedPlaceBean; //记录选中的
    private int spanCount = 3;

    public LocalTypeDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_place_type_layout);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        Window window = getWindow();
        if (window != null) {
//            window.setGravity(Gravity.BOTTOM); //dialog底部
//            window.setWindowAnimations(R.style.BottomDialog_Animation); //dialog 弹出退出动画
            window.setBackgroundDrawableResource(R.color.color_trans); //window背景颜色
            window.setLayout(width * 3 / 4, WindowManager.LayoutParams.WRAP_CONTENT);
//            WindowManager.LayoutParams params = window.getAttributes();
//            params.width = width * 3 / 4;
//            params.height = height / 2;
//            window.setAttributes(params);

        }
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) recyclerView.getLayoutParams();
        lp.weight = LinearLayout.LayoutParams.MATCH_PARENT;
        lp.height = height / 3;
        recyclerView.setLayoutParams(lp);
    }

    public void setDatas(List<PlaceBean> placeBeans, String channelPlaceId) {
        if (localAdapter == null) {
            datas = new ArrayList<>();
            PlaceBean placeBean = new PlaceBean();
            placeBean.setId("3225");
            placeBean.setName("中央");
//        selectedPlaceBean = placeBean;
            datas.add(placeBean);
            datas.addAll(placeBeans);
            setSelected(channelPlaceId);
            localAdapter = new LocalAdapter(context, datas);
            localAdapter.setOnItemClickListener(new LocalAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(PlaceBean placeBean) {
                    setSelected(placeBean.getId());
                    selectedPlaceBean = placeBean;
                    localAdapter.notifyDataSetChanged();
                }
            });
            GridLayoutManager layoutManager = new GridLayoutManager(context, spanCount);
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            if (weizhi2 > 6) { //尽量使选中的处于中间...
                recyclerView.scrollToPosition(weizhi2 - spanCount * 2);
            }
        } else {
            setSelected(channelPlaceId);
            if (weizhi2 > 6) { //尽量使选中的处于中间...
                recyclerView.scrollToPosition(weizhi2 - spanCount * 2);
            }
        }
        recyclerView.setAdapter(localAdapter);


    }

    private int weizhi2 = 0;

    private void setSelected(String channelPlaceId) {
        for (int i = 0; i < datas.size(); i++) {
            PlaceBean placeBean = datas.get(i);
            if (placeBean.getId().equals(channelPlaceId)) {
                placeBean.setSelected(true);
                weizhi2 = i;
            } else {
                placeBean.setSelected(false);
            }
        }
//        for (PlaceBean placeBean : datas) {
//            if (placeBean.getId().equals(channelPlaceId)) {
//                placeBean.setSelected(true);
//            } else {
//                placeBean.setSelected(false);
//            }
//        }
    }

    private onClickYes onClickYes;

    @OnClick(R.id.tv_selected)
    public void onViewClicked() {
        if (onClickYes != null && selectedPlaceBean != null) {
            onClickYes.clickYes(selectedPlaceBean);
        }
        dismiss();

    }

    public interface onClickYes {
        void clickYes(PlaceBean selectPlacebean);
    }

    public void setOnClickYes(onClickYes onClickYes) {
        this.onClickYes = onClickYes;
    }


}
