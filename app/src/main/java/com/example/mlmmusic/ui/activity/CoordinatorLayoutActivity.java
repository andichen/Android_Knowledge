package com.example.mlmmusic.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_me_dot_t)
    ImageView ivMeDotT;
    @BindView(R.id.ly_system_parent)
    LinearLayout lySystemParent;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.fragment)
    FrameLayout fragment;
    @BindView(R.id.text_care)
    TextView textCare;
    private ArrayList<Fragment> fragments = new ArrayList<>();
//    private View inclde_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        ButterKnife.bind(this);
        lySystemParent.setBackgroundColor(Color.TRANSPARENT);
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
                Log.i("clf_", "appbar" + verticalOffset + "");
                //verticalOffset始终为0以下的负数
                float percent = (Math.abs(verticalOffset * 1.0f) / appBarLayout.getTotalScrollRange());
                toolbar.setBackgroundColor(changeAlpha(Color.WHITE, percent));


                if (percent == 1) {
                    textCare.setVisibility(View.VISIBLE);

                    Animation animation = AnimationUtils.loadAnimation(CoordinatorLayoutActivity.this,R.anim.in_care_anim);
                    textCare.startAnimation(animation);
                } else {
                    textCare.setVisibility(View.GONE);
                }


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
