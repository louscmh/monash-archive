package com.fit2081.fit2081assignment2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.EventViewHolder> {

    ArrayList<Event> data = new ArrayList<>();

    @NonNull
    @Override
    public EventRecyclerAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_event, parent, false); //CardView inflated as RecyclerView list item
        EventRecyclerAdapter.EventViewHolder viewHolder = new EventRecyclerAdapter.EventViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventRecyclerAdapter.EventViewHolder holder, int position) {
        String eventId = data.get(position).getEventId();
        String categoryId = data.get(position).getCategoryId();
        String eventName = data.get(position).getEventName();
        String ticketCount = String.valueOf(data.get(position).getTicketCount());
        String eventActive = data.get(position).isActive() ? "Active" : "Inactive";

        holder.tvEventId.setText(eventId);
        holder.tvEventCategoryId.setText(categoryId);
        holder.tvEventName.setText(eventName);
        holder.tvEventTicket.setText(ticketCount);
        holder.tvEventActive.setText(eventActive);
    }

    @Override
    public int getItemCount() {
        if (this.data != null) { // if data is not null
            return this.data.size(); // then return the size of ArrayList
        }

        // else return zero if data is null
        return 0;
    }

    public void setData(ArrayList<Event> data) {
        this.data = data;
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {

        public TextView tvEventId;
        public TextView tvEventCategoryId;
        public TextView tvEventName;
        public TextView tvEventTicket;
        public TextView tvEventActive;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEventId = itemView.findViewById(R.id.tv_event_id);
            tvEventCategoryId = itemView.findViewById(R.id.tv_category_id);
            tvEventName = itemView.findViewById(R.id.tv_event_name);
            tvEventTicket = itemView.findViewById(R.id.tv_event_tickets);
            tvEventActive = itemView.findViewById(R.id.tv_event_active);
        }
    }
}
