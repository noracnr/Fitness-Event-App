package com.example.trestapi2firebase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trestapi2firebase.model.Event;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class EventAdapter extends FirestoreRecyclerAdapter<Event, EventAdapter.EventHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public EventAdapter(@NonNull FirestoreRecyclerOptions<Event> options) {
        super(options);
    }

    class EventHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewdate;
        TextView textViewtitle;
        TextView textViewaddress;


        public EventHolder(@NonNull View v) {
            super(v);
            imageView = v.findViewById(R.id.event_img_cover);
            textViewdate = v.findViewById(R.id.event_txt_date);
            textViewtitle = v.findViewById(R.id.event_txt_title);
            textViewaddress = v.findViewById(R.id.event_txt_address);
        }
    }

    @Override
    protected void onBindViewHolder(@NonNull EventHolder holder, int position, @NonNull Event model) {
        if(model.getLogo() != null) {
            Picasso.get().load(model.getLogo().getUrl()).into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.ic_insert_photo);
        }
        holder.textViewdate.setText(model.getStart().getLocal()+"--"+model.getEnd().getLocal());
        holder.textViewtitle.setText(model.getName().getText());
        holder.textViewaddress.setText(model.getId());
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_event, parent, false);
        return new EventHolder(v);
    }



}
