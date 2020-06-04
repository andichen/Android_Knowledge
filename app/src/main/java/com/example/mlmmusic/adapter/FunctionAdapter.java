package com.example.mlmmusic.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mlmmusic.R;
import com.example.mlmmusic.beans.FunctionItemsBean;
import com.google.gson.Gson;

import java.util.List;

public class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.ViewHolder> {


    private Context context;
    private List<FunctionItemsBean> datas;
    private boolean isEdit = false;
    public FunctionAdapter(Context context, List<FunctionItemsBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public FunctionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_grid_item, null);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FunctionAdapter.ViewHolder holder, int position) {
        holder.text.setText(datas.get(position).getName());
        holder.btn.setImageResource(R.mipmap.ic_block_add);
        if (isEdit) {
            holder.btn.setVisibility(View.VISIBLE);
        } else {
            holder.btn.setVisibility(View.GONE);

        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
    public void setEdit(boolean isEdit) { //点击编辑.完成
        this.isEdit = isEdit;
        if (!isEdit) { //如果是完成，则需要保存到sp中
//            Gson gson = new Gson();
//            SharedPreferences spMyData = context.getSharedPreferences(MyData, Context.MODE_PRIVATE);
//            spMyData.edit().putString("selectData",gson.toJson(datas)).apply();
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv, btn;
        private TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            text = (TextView) itemView.findViewById(R.id.text);
            btn = (ImageView) itemView.findViewById(R.id.btn);
        }
    }
}
