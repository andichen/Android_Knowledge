package com.example.mlmmusic.adapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

/*******************************************
 * @author  : 奥特曼
 * @Email   : luckkof@foxmail.com
 * @Description : 适配器 示例类
 *******************************************/
public class MeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public MeAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    public MeAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, String item) {
        //添加Item子控件的点击事件
//        helper.addOnClickListener(R.id.tv_name);
//        //赋值
//        viewHolder.setText(R.id.tv_me_title_name,"Hello："+item);
//        ImageView iv_cover = viewHolder.getView(R.id.iv_cover);
//        iv_cover.setImageResource(mContext.getResources().getIdentifier("ic_palette_0" + viewHolder.getLayoutPosition() % 4, "drawable", mContext.getPackageName()));
    }

}
