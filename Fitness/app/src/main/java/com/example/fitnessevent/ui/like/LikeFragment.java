package com.example.fitnessevent.ui.like;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessevent.R;
import com.example.fitnessevent.event.EventActivity;
import com.example.fitnessevent.event.EventAdapter;
import com.example.fitnessevent.model.Event;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class LikeFragment extends Fragment {

    private LikeViewModel likeViewModel;

    private EventAdapter eventAdapter;
    private RecyclerView recyclerView;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference likeRef = db.collection("users").document("uoOzYdmKBwNOMYcPmEnEYktb9nY1").collection("likes");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        likeViewModel =
                ViewModelProviders.of(this).get(LikeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_like, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        likeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        recyclerView = root.findViewById(R.id.likesRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Query query = likeRef.orderBy("changed", Query.Direction.DESCENDING);
        setUpRecyclerView(recyclerView, query);
        return root;
    }
    private void setUpRecyclerView(RecyclerView recyclerView, Query query) {

        FirestoreRecyclerOptions<Event> options = new FirestoreRecyclerOptions.Builder<Event>()
                .setQuery(likeRef, Event.class)
                .build();

        eventAdapter = new EventAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(eventAdapter);

        eventAdapter.setOnClickListener(new EventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Event event = documentSnapshot.toObject(Event.class);
                Intent eventIntent = new Intent(getActivity(), EventActivity.class);

                if (event.getLogo() != null) {
                    eventIntent.putExtra("image_url",event.getLogo().getOriginal().getUrl());
                } else {
                    eventIntent.putExtra("image_url","https://github.com/Fitness-Event-APP/Fitness/blob/master/HatchfulExport-All/instagram_profile_image.png");
                }
                eventIntent.putExtra("title",event.getName().getText());
                eventIntent.putExtra("description",event.getDescription().getText());
                eventIntent.putExtra("startTime",event.getStart().getLocal());
                eventIntent.putExtra("endTime",event.getEnd().getLocal());
                eventIntent.putExtra("latitude", event.getVenue().getLatitude());
                eventIntent.putExtra("longitude", event.getVenue().getLongitude());
                eventIntent.putExtra("id", event.getId());
                eventIntent.putExtra("object", event);
                eventIntent.putExtra("length",eventAdapter.getItemCount());
                startActivity(eventIntent);

            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        eventAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        eventAdapter.stopListening();
    }

}