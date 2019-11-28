package com.example.fitnessevent.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.algolia.search.saas.Client;
import com.algolia.search.saas.Index;
import com.example.fitnessevent.event.EventActivity;
import com.example.fitnessevent.event.EventAdapter;
import com.example.fitnessevent.event.EventbriteApi;
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
    private Client client;
    private Index index;

    private RecyclerView recyclerView;

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
        recyclerView = root.findViewById(R.id.recycler_view);
        Query query = eventRef.orderBy("changed", Query.Direction.DESCENDING);
        setUpRecyclerView(recyclerView, query);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.eventbriteapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        eventbriteApi = retrofit.create(EventbriteApi.class);

        // Algolia:Initial
        client = new Client("JNVPF41HNP", "b1159b70bbd154bf6c8ed3d195dd1405");
        index = client.getIndex("FitnessEvents");

        getEvents();
        setHasOptionsMenu(true);

        return root;
    }
    private void setUpRecyclerView(RecyclerView recyclerView, Query query) {

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
                eventIntent.putExtra("address", event.getVenue().getLatitude());
                eventIntent.putExtra("address2", event.getVenue().getLongitude());
                startActivity(eventIntent);

            }
        });
    }

    private void getEvents() {

        Integer[] pages = new Integer[200];
        for (int i = 0; i < pages.length; i++) {
            pages[i] = i+1;
        }

        Call<PaginatedEvents> call = eventbriteApi.getPaginatedEvents(108, pages,"venue","IRN4X6MKLUWHLIFGBEDY");

        call.enqueue(new Callback<PaginatedEvents>() {
            @Override
            public void onResponse(Call<PaginatedEvents> call, Response<PaginatedEvents> response) {
                if (!response.isSuccessful()) {
                    Log.d("CallonResponse","failed! Code: " + response.code());
                    return;
                }

                PaginatedEvents paginatedEvents = response.body();

                Pagination pagination = paginatedEvents.getPagination();
                db.collection("Pagination").document("SearchTest").
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
                //index.addObjectsAsync(new JSONArray(paginatedEvents.getEvents()), null);

            }

            @Override
            public void onFailure(Call<PaginatedEvents> call, Throwable t) {
                Log.w("CallonFailure","Throwable:" + t.getMessage());
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        inflater.inflate(R.menu.filter, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView mSearchView = (SearchView) searchItem.getActionView();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent searchIntent = new Intent(getActivity(), SearchActivity.class);
                searchIntent.putExtra("searchKeywords",query);
                startActivity(searchIntent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.filter) {
            Intent filterIntent = new Intent(getActivity(), FilterActivity.class);
            startActivityForResult(filterIntent, 0);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getStringExtra("category").length() != 0) {
            Query query = eventRef.whereEqualTo("subcategoryId", data.getStringExtra("category"));
            setUpRecyclerView(recyclerView, query);
        }
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