package com.example.mlmmusic.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mlmmusic.R;
import com.example.mlmmusic.base.BaseActivity;
import com.example.mlmmusic.ui.fragment.PartClassifyFragment;
import com.example.mlmmusic.util.BlurImageView;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoordinatorLayoutActivity extends BaseActivity {

    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.iv_useravator)
    ImageView ivUseravator;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.iv_lv)
    ImageView ivLv;
    @BindView(R.id.tv_lv)
    TextView tvLv;
    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @BindView(R.id.ll_username)
    LinearLayout llUsername;
    @BindView(R.id.ll_personal_me)
    RelativeLayout llPersonalMe;
    @BindView(R.id.lay_header)
    RelativeLayout layHeader;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.fragment)
    FrameLayout fragment;
    //    @BindView(R.id.system_bar_view)
//    View systemBarView;
//    @BindView(R.id.tv1)
//    TextView tv1;
//    @BindView(R.id.tv2)
//    TextView tv2;
    @BindView(R.id.iv_me_dot_t)
    ImageView ivMeDotT;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ly_system_parent)
    LinearLayout ly_system_parent;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private String[] titles = new String[]{"热门", "最新"};
    private ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        ButterKnife.bind(this);
        setTitle("aaa");
        tvTitle.setTextColor(Color.BLACK);
        ly_system_parent.setBackgroundColor(Color.TRANSPARENT);
        init();
        setAvatorChange();

    }

    private void init() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PartClassifyFragment fragment = new PartClassifyFragment();
        fragmentTransaction.add(R.id.fragment, fragment);
        fragmentTransaction.commit();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.internet_star);
        ivBg.setImageBitmap(BlurImageView.BlurImages(bitmap));  //高斯模糊
    }

    /**
     * 渐变toolbar背景
     */
    private void setAvatorChange() {
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //verticalOffset始终为0以下的负数
                float percent = (Math.abs(verticalOffset * 1.0f) / appBarLayout.getTotalScrollRange());
//
                toolbar.setBackgroundColor(changeAlpha(Color.WHITE, percent));
            }
        });
    }

    /**
     * 根据百分比改变颜色透明度
     */
    public int changeAlpha(int color, float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }

}
