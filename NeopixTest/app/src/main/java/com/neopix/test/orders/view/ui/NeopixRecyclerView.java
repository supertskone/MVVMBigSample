package com.neopix.test.orders.view.ui;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NeopixRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements HeaderItemDecoration.HeaderInterface {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getHeaderPositionForItem(int itemPosition) {
        return 0;
    }

    @Override
    public int getHeaderLayout(int headerPosition) {
        return 0;
    }

    @Override
    public void bindHeaderData(View header, int headerPosition) {

    }

    @Override
    public boolean isHeader(int itemPosition) {
        return false;
    }
}
