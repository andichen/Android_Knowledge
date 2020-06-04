package com.example.mlmmusic.dialog;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mlmmusic.R;
import com.example.mlmmusic.adapter.BlueListAdapter;
import com.example.mlmmusic.base.BaseDialog;
import com.example.mlmmusic.beans.BlueToothBean;
import com.example.mlmmusic.beans.PlaceBean;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ywl on 2018/1/15.
 */

public class BlueToothDialog extends BaseDialog {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    public BlueToothDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bluetooth_layout);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        Window window = getWindow();
        if (window != null) {
            window.setGravity(Gravity.BOTTOM); //dialog底部
            window.setWindowAnimations(R.style.BottomDialog_Animation); //dialog 弹出退出动画
            window.setBackgroundDrawableResource(R.color.color_trans); //window背景颜色
            window.setLayout(width * 7 / 8, WindowManager.LayoutParams.WRAP_CONTENT);
//            WindowManager.LayoutParams params = window.getAttributes();
//            params.width = width ;
//            params.height = height / 2;
//            window.setAttributes(params);

        }
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) recyclerView.getLayoutParams();
        lp.weight = LinearLayout.LayoutParams.MATCH_PARENT;
        lp.height = height / 3;
        recyclerView.setLayoutParams(lp);
    }

    private onClickYes onClickYes;

    @OnClick(R.id.tv_selected)
    public void onViewClicked() {
        dismiss();
    }


    public void setData(List<BlueToothBean> blueToothBeans) {
        if (blueToothBeans != null) {
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
            dividerItemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.list_item_divide)));
            recyclerView.addItemDecoration(dividerItemDecoration);

            BlueListAdapter blueListAdapter = new BlueListAdapter(context, blueToothBeans);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);


            recyclerView.setAdapter(blueListAdapter);
        }


    }

    public interface onClickYes {
        void clickYes(PlaceBean selectPlacebean);
    }

    public void setOnClickYes(onClickYes onClickYes) {
        this.onClickYes = onClickYes;
    }


}
