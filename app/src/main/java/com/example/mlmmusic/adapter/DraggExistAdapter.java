package com.example.mlmmusic.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mlmmusic.R;
import com.example.mlmmusic.beans.CeshiTextBean;
import com.example.mlmmusic.beans.FunctionItemsBean;
import com.example.mlmmusic.beans.PlaceBean;
import com.example.mlmmusic.draggrecycle.CustomItemTouchCallback;
import com.example.mlmmusic.draggrecycle.ItemTouchStatus;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ywl on 2017-7-25.
 */

public class DraggExistAdapter extends RecyclerView.Adapter<DraggExistAdapter.TypeHolder> implements ItemTouchStatus {

    private Context context;
    private List<FunctionItemsBean> datas;
    private static String MyData = "我的应用";

    private OnItemClickListener onItemClickListener;
    private boolean isEdit = false;

    private List<String> guding;

    private String mydata;

    public DraggExistAdapter(Context context, List<FunctionItemsBean> datas, String stringList) {
        this.context = context;
        this.datas = datas;
//        this.guding = stringList;
        this.mydata = stringList;

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public TypeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_grid_item, null);
        TypeHolder holder = new TypeHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(TypeHolder holder, final int position) {

        holder.text.setText(datas.get(position).getName());
        holder.btn.setImageResource(R.mipmap.ic_block_delete);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    if (!mydata.contains(datas.get(position).getName())) {
                        datas.remove(position);
                        onItemClickListener.onItemClick(datas, position);
                    } else {
                        Toast.makeText(context,"不可删除，只能移动",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

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

    @Override
    public void onItemMove(RecyclerView.ViewHolder holder, int fromPosition, int targetPosition) {

        //设置位置移动对应的datas顺序
        if (fromPosition < targetPosition) {
            for (int i = fromPosition; i < targetPosition; i++) {
                Collections.swap(datas, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > targetPosition; i--) {
                Collections.swap(datas, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, targetPosition);

//        if (fromPosition < datas.size() && targetPosition < datas.size()) {
//            Collections.swap(datas, fromPosition, targetPosition);
//            notifyItemMoved(fromPosition, targetPosition);
//        }
    }

    @Override
    public void onItemSelect(RecyclerView.ViewHolder holder) {
        holder.itemView.setScaleX(0.8f);
        holder.itemView.setScaleY(0.8f);
    }

    @Override
    public void onItemClear(RecyclerView.ViewHolder holder) {
        holder.itemView.setScaleX(1.0f);
        holder.itemView.setScaleY(1.0f);
    }

    public void setEdit(boolean isEdit) { //点击编辑.完成
        this.isEdit = isEdit;
        if (!isEdit) { //如果是完成，则需要保存到sp中
            Gson gson = new Gson();
            SharedPreferences spMyData = context.getSharedPreferences(MyData, Context.MODE_PRIVATE);
            spMyData.edit().putString("selectData",gson.toJson(datas)).apply();
        }
        notifyDataSetChanged();
    }


    public class TypeHolder extends RecyclerView.ViewHolder {
        private ImageView iv, btn;
        private TextView text;

        public TypeHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            text = (TextView) itemView.findViewById(R.id.text);
            btn = (ImageView) itemView.findViewById(R.id.btn);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(List<FunctionItemsBean> functionItemsBeans, int position);
    }

}
