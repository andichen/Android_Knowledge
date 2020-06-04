package com.example.mlmmusic.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mlmmusic.R;
import com.example.mlmmusic.beans.LiveChannelBean;

import java.util.List;

public class LiveListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<LiveChannelBean> datas;

    public LiveListAdapter(Context context, List<LiveChannelBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_live_channel_layout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        myHolder.tvName.setText(datas.get(position).getName());
        if (!TextUtils.isEmpty(datas.get(position).getLiveSectionName())) {
            myHolder.tvLiveName.setVisibility(View.VISIBLE);
            myHolder.tvLiveName.setText("正在直播：" + datas.get(position).getLiveSectionName());
        } else {
            myHolder.tvLiveName.setVisibility(View.GONE);
        }
        Glide.with(context).load(datas.get(position).getImg()).into(((MyHolder) holder).ivImg);

        //点击事件
        if (onItemClickListeren != null) {
            myHolder.rlRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListeren.onItemClick(datas.get(position),position);
                }
            });
        }

    }

    private OnItemClickListeren onItemClickListeren;

    public interface OnItemClickListeren {
        void onItemClick(LiveChannelBean channelBean,int position);
    }

    public void setOnItemClickListeren(OnItemClickListeren onItemClickListeren) {
        this.onItemClickListeren = onItemClickListeren;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private ImageView ivImg;
        private TextView tvName;
        private TextView tvLiveName;
        private RelativeLayout rlRoot;

        public MyHolder(View itemView) {
            super(itemView);
            ivImg = itemView.findViewById(R.id.iv_img);
            tvName = itemView.findViewById(R.id.tv_name);
            tvLiveName = itemView.findViewById(R.id.tv_live_name);
            rlRoot = itemView.findViewById(R.id.rl_root);
        }
    }
}
