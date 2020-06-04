package com.example.mlmmusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mlmmusic.R;
import com.example.mlmmusic.beans.BlueToothBean;
import com.example.mlmmusic.beans.PlaceBean;

import java.util.List;

/**
 * Created by ywl on 2017-7-25.
 */

public class BlueListAdapter extends RecyclerView.Adapter<BlueListAdapter.TypeHolder> {

    private Context context;
    private OnItemClickListener onItemClickListener;
    private List<BlueToothBean> blueToothBeans;


    public BlueListAdapter(Context context, List<BlueToothBean> datas) {
        this.context = context;
        this.blueToothBeans = datas;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public TypeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bluetooth, null);
        TypeHolder holder = new TypeHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(TypeHolder holder, final int position) {
        holder.name.setText(blueToothBeans.get(position).getName());
        holder.address.setText(blueToothBeans.get(position).getAddress());
        holder.type.setText(blueToothBeans.get(position).getType());
        holder.bondstate.setText(blueToothBeans.get(position).getBondState());
    }


    @Override
    public int getItemCount() {
        return blueToothBeans.size();
    }

    public class TypeHolder extends RecyclerView.ViewHolder {


        private TextView name, address, type, bondstate;

        public TypeHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            address = (TextView) itemView.findViewById(R.id.tv_address);
            type = (TextView) itemView.findViewById(R.id.tv_type);
            bondstate = (TextView) itemView.findViewById(R.id.tv_bondState);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(PlaceBean placeBean);
    }

}
