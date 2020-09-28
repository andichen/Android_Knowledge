package com.example.mlmmusic.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mlmmusic.MainActivity;
import com.example.mlmmusic.R;
import com.example.mlmmusic.base.BaseActivity;
import com.example.mlmmusic.draggrecycle.DraggRecycleviewActivity;
import com.example.mlmmusic.util.customview.AttachButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QueryActivity extends BaseActivity {

    @BindView(R.id.attachbutton_view)
    AttachButton attachbuttonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        ButterKnife.bind(this);
        setTitle("知识点");
        setBackView();


    }

    @OnClick({R.id.click_MainActivity, R.id.click_DataBaseActivty, R.id.click_DraggRecycleview, R.id.click_BaseRecyclerViewAdapterHelperActivity, R.id.LoadRecycleActivity, R.id.CoordinatorLayoutActivity, R.id.RxJavaActivity, R.id.JavaKnowledgeActivity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.click_MainActivity:
//                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                startSystemActivity(this, MainActivity.class);
                break;
            case R.id.click_DataBaseActivty:
                startSystemActivity(this, DatabaseActivity.class);
                break;
            case R.id.click_DraggRecycleview:
                startSystemActivity(this, DraggRecycleviewActivity.class);
                break;
            case R.id.click_BaseRecyclerViewAdapterHelperActivity:
                startSystemActivity(this, BaseRecyclerViewAdapterHelperActivity.class);
                break;
            case R.id.LoadRecycleActivity:
                startSystemActivity(this, LoadRecycleActivity.class);
                break;
            case R.id.CoordinatorLayoutActivity:
                startSystemActivity(this, CoordinatorLayoutActivity.class);
                break;
            case R.id.RxJavaActivity:
                startSystemActivity(this, RxJavaActivity.class);
                break;
            case R.id.JavaKnowledgeActivity:
                startSystemActivity(this, JavaKnowledgeActivity.class);
                break;

        }
    }

    @OnClick(R.id.attachbutton_view)
    public void onViewClicked() {
    }
}
