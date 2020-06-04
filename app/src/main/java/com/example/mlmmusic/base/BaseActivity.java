package com.example.mlmmusic.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mlmmusic.R;
import com.example.mlmmusic.util.CommonUtil;
import com.example.mlmmusic.util.RxBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BaseActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.ly_system_parent)
    LinearLayout lySystemParent;

    @Nullable
    @BindView(R.id.iv_line)
    ImageView ivLine;

    @Nullable
    @BindView(R.id.ly_system_bar)
    LinearLayout lySystemBar;

    @Nullable
    @BindView(R.id.tv_title)
    TextView mtvTitle;

    @Nullable
    @BindView(R.id.iv_back)
    ImageView mivBack;

    @Nullable
    @BindView(R.id.iv_right)
    ImageView mivRight;

    @Nullable
    @BindView(R.id.tv_right)
    TextView tvRight;

    @Nullable
    @BindView(R.id.ly_data_load)
    LinearLayout lyDataLoad;
    private ArrayList<RxBus> rxBusList = new ArrayList<>();

    public void set(RxBus rxBus) {

        rxBusList.add(rxBus);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
            //状态栏 手机图标变白
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);

            //状态栏 手机图标变黑
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    |  View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);

        }
        //透明状态栏
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4全透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        if (lySystemBar != null) {
            initSystembar(lySystemBar);
        }
        if (mivBack != null) {
            mivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        if (mivRight != null) {
            mivRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickMenu();
                }
            });
        }

        if (tvRight != null) {
            tvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickTxtMenu();

                }
            });
        }

    }

    private void onClickTxtMenu() {
    }

    public void onClickMenu() {
    }

    public void initSystembar(View lySystemBar) {
        if (Build.VERSION.SDK_INT >= 19) {
            if (lySystemBar != null) {
                lySystemBar.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) lySystemBar.getLayoutParams();
                lp.height = CommonUtil.getStatusHeight(this);
                lySystemBar.requestLayout();
            }
        } else {
            if (lySystemBar != null) {
                lySystemBar.setVisibility(View.GONE);
            }
        }
    }

    public void showDadaLoad() {
        if (lyDataLoad != null) {
            lyDataLoad.setVisibility(View.VISIBLE);
        }
    }

    public void hideDataLoad() {
        if (lyDataLoad != null) {
            lyDataLoad.setVisibility(View.GONE);
        }
    }

    /**
     * 显示右上角图标
     */
    public void setRightView(int resId) {
        if (mivRight != null) {
            mivRight.setVisibility(View.VISIBLE);
            mivRight.setImageResource(resId);
        }
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        if (mtvTitle != null) {
            mtvTitle.setText(title);
        }
    }

    public void setTitleColor(int color) {
        if (mtvTitle != null) {
            mtvTitle.setTextColor(getResources().getColor(color));
        }
    }

    public void setTitleTrans(int color) {
        if (lySystemParent != null) {
            lySystemParent.setBackgroundColor(getResources().getColor(color));
        }
    }

    public void setTitleLine(int color) {
        if (ivLine != null) {
            ivLine.setBackgroundColor(getResources().getColor(color));
        }
    }

    /**
     * 显示返回图标
     */
    public void setBackView() {
        if (mivBack != null) {
            mivBack.setVisibility(View.VISIBLE);
        }
    }


    //有动画
    public static void startAnimActivity(Context context, Class clz) {
        Intent intent = new Intent(context, clz);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

    }

    //无动画
    public static void startSystemActivity(Context context, Class clz) {
        Intent intent = new Intent(context, clz);
        context.startActivity(intent);
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 取消该页面所有订阅 好想没什么用
     */
    private void clearSubscription() {
        Log.i("sss", "cc");
        for (int i = 0; i < rxBusList.size(); i++) {
            String name = rxBusList.get(i).getClass().getName();
            rxBusList.get(i).unSubcribe();
        }
    }

    @Override
    protected void onDestroy() {
//        clearSubscription();
        super.onDestroy();
        RxBus.getInstance().unSubcribe();

//        RxBus instance = RxBus.getUnInstance();  //先判断当前的rxbus有没有初始化，如果初始化了则unSubcribe()，否则无需在重新创建然后在unSubcribe();了
//        if (instance != null) { //但是并没有什么用啊
//            RxBus.getInstance().unSubcribe();
//        }
    }


}
