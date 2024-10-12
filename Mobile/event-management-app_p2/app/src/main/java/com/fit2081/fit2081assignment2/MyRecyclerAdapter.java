package com.fit2081.fit2081assignment2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.CustomViewHolder> {

    ArrayList<Category> data = new ArrayList<>();

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_category, parent, false); //CardView inflated as RecyclerView list item
        CustomViewHolder viewHolder = new CustomViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        String categoryId = data.get(position).getCategoryId();
        String categoryName = data.get(position).getCategoryName();
        String categoryCount = String.valueOf(data.get(position).getEventCount());
        String categoryActive = data.get(position).isActive() ? "Yes" : "No";

        holder.tvCategoryId.setText(categoryId);
        holder.tvCategoryName.setText(categoryName);
        holder.tvEventCount.setText(categoryCount);
        holder.tvIsActive.setText(categoryActive);
    }

    @Override
    public int getItemCount() {
        if (this.data != null) { // if data is not null
            return this.data.size(); // then return the size of ArrayList
        }

        // else return zero if data is null
        return 0;
    }

    public void setData(ArrayList<Category> data) {
        this.data = data;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView tvCategoryId;
        public TextView tvCategoryName;
        public TextView tvEventCount;
        public TextView tvIsActive;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryId = itemView.findViewById(R.id.tv_id);
            tvCategoryName = itemView.findViewById(R.id.tv_name);
            tvEventCount = itemView.findViewById(R.id.tv_count);
            tvIsActive = itemView.findViewById(R.id.tv_active);
        }
    }
}
