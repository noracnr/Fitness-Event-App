package com.example.fitnessevent.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessevent.EventbriteApi;
import com.example.fitnessevent.R;
import com.example.fitnessevent.model.Event;
import com.example.fitnessevent.model.PaginatedEvents;
import com.example.fitnessevent.model.Pagination;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private final String TAG="firebaseTest";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference eventRef = db.collection("events");

    private EventAdapter eventAdapter;

    private EventbriteApi eventbriteApi;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        final RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        setUpRecyclerView(recyclerView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.eventbriteapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        eventbriteApi = retrofit.create(EventbriteApi.class);

        getEvents();


        return root;
    }
    private void setUpRecyclerView(RecyclerView recyclerView) {
        Query query = eventRef.orderBy("changed", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Event> options = new FirestoreRecyclerOptions.Builder<Event>()
                .setQuery(query, Event.class)
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
                eventIntent.putExtra("address", event.getId());
                startActivity(eventIntent);

            }
        });
    }

    private void getEvents() {
/*
        Integer[] pages = new Integer[200];
        for (int i = 0; i < pages.length; i++) {
            pages[i] = i+1;
        }
*/
        Call<PaginatedEvents> call = eventbriteApi.getPaginatedEvents(108, new Integer[]{1,2,3,4,5,6,7},"venue","IRN4X6MKLUWHLIFGBEDY");

        call.enqueue(new Callback<PaginatedEvents>() {
            @Override
            public void onResponse(Call<PaginatedEvents> call, Response<PaginatedEvents> response) {
                if (!response.isSuccessful()) {
                    Log.d("CallonResponse","failed! Code: " + response.code());
                    return;
                }

                PaginatedEvents paginatedEvents = response.body();



                Pagination pagination = paginatedEvents.getPagination();
                db.collection("Pagination").document("test1").
                        set(pagination).
                        addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("Pagination","successfully add");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("Pagination","error adding document",e);
                            }
                        });

                for (Event event : paginatedEvents.getEvents()) {

                    db.collection("events").document(event.getId())
                            .set(event)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot added with events ");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });

                }


            }

            @Override
            public void onFailure(Call<PaginatedEvents> call, Throwable t) {
                Log.w("CallonFailure","Throwable:" + t.getMessage());
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