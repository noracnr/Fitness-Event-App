package com.example.fitnessevent.event;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessevent.R;
import com.example.fitnessevent.model.SimpleEvent;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchEventAdapter extends RecyclerView.Adapter<SearchEventAdapter.SearchEventViewHolder> {
    private List<SimpleEvent> mSimpleEventsList;
    private OnItemClickListener mListenr;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListenr = listener;
    }

    public SearchEventAdapter(List<SimpleEvent> simpleEventsList) {
        mSimpleEventsList = simpleEventsList;
    }

    public static class SearchEventViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewdate;
        TextView textViewtitle;
        TextView textViewaddress;

        public SearchEventViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.event_img_cover);
            textViewdate = itemView.findViewById(R.id.event_txt_date);
            textViewtitle = itemView.findViewById(R.id.event_txt_title);
            textViewaddress = itemView.findViewById(R.id.event_txt_address);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(position);
                    }
                }
            });

        }
    }

    @NonNull
    @Override
    public SearchEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_event, parent, false);
        return new SearchEventViewHolder(v, mListenr);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchEventViewHolder holder, int position) {
        SimpleEvent currentSimpleEvent = mSimpleEventsList.get(position);
        if(currentSimpleEvent.getLogoUrl() != null) {
            Picasso.get().load(currentSimpleEvent.getLogoUrl()).into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.ic_insert_photo);
        }
        holder.textViewdate.setText(currentSimpleEvent.getStartTime()+"--"+currentSimpleEvent.getEndTime());
        holder.textViewtitle.setText(currentSimpleEvent.getNameText());
        holder.textViewaddress.setText(currentSimpleEvent.getId());
    }

    @Override
    public int getItemCount() {
        return mSimpleEventsList.size();
    }




}
