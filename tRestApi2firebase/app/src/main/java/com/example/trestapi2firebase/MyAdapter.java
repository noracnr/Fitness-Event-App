package com.example.trestapi2firebase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<ExampleItem> mExampleList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textViewdate;
        public TextView textViewtitle;
        public TextView textViewaddress;
        public MyViewHolder(View v) {
            super(v);
            imageView = v.findViewById(R.id.event_img_cover);
            textViewdate = v.findViewById(R.id.event_txt_date);
            textViewtitle = v.findViewById(R.id.event_txt_title);
            textViewaddress = v.findViewById(R.id.event_txt_address);
        }
    }

    public MyAdapter(ArrayList<ExampleItem> exampleList) {
        mExampleList = exampleList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_event, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ExampleItem currentItem = mExampleList.get(position);

        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.textViewdate.setText(currentItem.getdate());
        holder.textViewtitle.setText(currentItem.gettitle());
        holder.textViewaddress.setText(currentItem.getMaddress());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }





}
