package com.krak.storeexample.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.krak.storeexample.R;

import java.util.HashSet;

public class StepIndicatorAdapter extends RecyclerView.Adapter<StepIndicatorAdapter.ViewHolder> {

    private int count;
    private LayoutInflater inflater;
    private ViewHolder[] holders;
    private HashSet<Integer> filledPositions;

    public StepIndicatorAdapter(int count, Context context) {
        this.count = count;
        inflater = LayoutInflater.from(context);
        holders = new ViewHolder[count];
        filledPositions = new HashSet<>(count);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.indicator, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holders[position] = holder;
        if (filledPositions.contains(position)){
            holder.setFilled();
        }
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public void setFilledCells(int count){
        for (int i = 0; i < Math.max(this.count, count); i++) {
            setFilled(i);
        }
        for (int i = count; i < Math.max(this.count, count); i++) {
            setUnfilled(i);
        }
    }

    public void setUnfilled(int position) {
        if (holders[position] != null) {
            holders[position].setUnfilled();
        }
    }

    public void setFilled(int position) {
        if (holders[position] != null) {
            holders[position].setFilled();
        } else {
            filledPositions.add(position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.indicatorImage);
        }

        public void setFilled(){
            image.setImageResource(R.drawable.filled_indicator);
        }

        public void setUnfilled() {
            image.setImageResource(R.drawable.unfilled_indicator);
        }
    }
}
