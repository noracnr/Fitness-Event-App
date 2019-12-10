package com.example.fitnessevent.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessevent.R;
import com.example.fitnessevent.event.EventActivity;
import com.example.fitnessevent.event.EventAdapter;
import com.example.fitnessevent.model.Event;
import com.example.fitnessevent.ui.home.SearchActivity;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private EventAdapter eventAdapter;
    private RecyclerView recyclerView;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference createsRef = db.collection("users").document("uoOzYdmKBwNOMYcPmEnEYktb9nY1").collection("creates");


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = root.findViewById(R.id.createsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Query query = createsRef.orderBy("changed", Query.Direction.DESCENDING);
        setUpRecyclerView(recyclerView, query);
        return root;
    }
    private void setUpRecyclerView(RecyclerView recyclerView, Query query) {

        FirestoreRecyclerOptions<Event> options = new FirestoreRecyclerOptions.Builder<Event>()
                .setQuery(createsRef, Event.class)
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

