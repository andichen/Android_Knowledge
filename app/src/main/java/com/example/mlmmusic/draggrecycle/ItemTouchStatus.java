package com.example.mlmmusic.draggrecycle;

import androidx.recyclerview.widget.RecyclerView;

public interface ItemTouchStatus {
    void onItemMove(RecyclerView.ViewHolder holder, int fromPosition, int targetPosition);

    void onItemSelect(RecyclerView.ViewHolder holder);

    void onItemClear(RecyclerView.ViewHolder holder);

//    void onItemDismiss(RecyclerView.ViewHolder holder);

}
