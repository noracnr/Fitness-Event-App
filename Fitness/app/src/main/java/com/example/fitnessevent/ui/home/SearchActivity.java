package com.example.fitnessevent.ui.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Client;
import com.algolia.search.saas.CompletionHandler;
import com.algolia.search.saas.Index;
import com.example.fitnessevent.R;
import com.example.fitnessevent.event.SearchEventAdapter;
import com.example.fitnessevent.model.Event;
import com.example.fitnessevent.event.EventActivity;
import com.example.fitnessevent.event.EventAdapter;
import com.example.fitnessevent.model.SimpleEvent;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity" ;

    private RecyclerView recyclerView;
    private EditText searchField;
    private ImageButton btn_search;

    private EventAdapter eventAdapter;
    private SearchEventAdapter searchEventAdapter;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference eventRef = db.collection("events");

    private Query query;

    private com.algolia.search.saas.Query query_algolia;
    private Client client;
    private Index index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.recyclerview_search);
        searchField = findViewById(R.id.search_field);
        btn_search = findViewById(R.id.btn_search);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Algolia:Initial
        client = new Client("JNVPF41HNP", "b1159b70bbd154bf6c8ed3d195dd1405");
        index = client.getIndex("FitnessEvents");

        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final String a = s.toString();
                searchAndDisplay(a);
            }
        });

        getFragmentData();
        //setUpRecyclerView(recyclerView,query);
    }




//SearchFragment
    private void getFragmentData() {
        Log.d(TAG,"getFragmentData: Checking for incoming intents.");
        if (getIntent().hasExtra("searchKeywords")) {
            Log.d(TAG,"getFragmentData: found intent extras");
            String keywords = getIntent().getStringExtra("searchKeywords");
            searchAndDisplay(keywords);
            searchField.setText(keywords,TextView.BufferType.EDITABLE);
        } else {
            Log.d(TAG,"getFragmentData: don't find and intent extras");
            //query = eventRef.orderBy("changed", Query.Direction.DESCENDING);

        }
    }

//Search RecyclerView
    private void searchAndDisplay(String keyword) {
        query_algolia = new com.algolia.search.saas.Query(keyword)
                .setAttributesToRetrieve("id","name","logo","description","start","end","venue")
                .setHitsPerPage(50);
        index.searchAsync(query_algolia, new CompletionHandler() {
            @Override
            public void requestCompleted(JSONObject content, AlgoliaException error) {
                try {
                    JSONArray hits = content.getJSONArray("hits");
                    final List<SimpleEvent> simpleEventlist = new ArrayList<>();
                    for (int i = 0; i < hits.length(); i++) {
                        JSONObject jsonObject = hits.getJSONObject(i);
                        String title = jsonObject.getJSONObject("name").getString("text");
                        String id = jsonObject.getString("id");
                        String logoUrl = null;
                        if (jsonObject.has("logo")) {
                            if (!jsonObject.isNull("logo")) {
                                logoUrl = jsonObject.getJSONObject("logo").getString("url");
                            }
                        }
                        String description = jsonObject.getJSONObject("description").getString("text");
                        String latitude = jsonObject.getJSONObject("venue").getString("latitude");
                        String longitude = jsonObject.getJSONObject("venue").getString("longitude");
                        String endTime = jsonObject.getJSONObject("end").getString("utc");
                        String startTime = jsonObject.getJSONObject("start").getString("utc");
                        SimpleEvent event = new SimpleEvent(id,title,description,startTime,endTime,latitude,longitude,logoUrl);
                        simpleEventlist.add(event);
                    }
                    searchEventAdapter = new SearchEventAdapter(simpleEventlist);

                    recyclerView.setAdapter(searchEventAdapter);

                    searchEventAdapter.setOnItemClickListener(new SearchEventAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            SimpleEvent clickedSimpleEvent = simpleEventlist.get(position);
                            Intent eventIntent = new Intent(SearchActivity.this, EventActivity.class);

                            if (clickedSimpleEvent.getLogoUrl() != null) {
                                eventIntent.putExtra("image_url",clickedSimpleEvent.getLogoUrl());
                            } else {
                                eventIntent.putExtra("image_url","https://github.com/Fitness-Event-APP/Fitness/blob/master/HatchfulExport-All/instagram_profile_image.png");
                            }

                            eventIntent.putExtra("title",clickedSimpleEvent.getNameText());
                            eventIntent.putExtra("description",clickedSimpleEvent.getDescriptionText());
                            eventIntent.putExtra("startTime",clickedSimpleEvent.getStartTime());
                            eventIntent.putExtra("endTime",clickedSimpleEvent.getEndTime());
                            eventIntent.putExtra("latitude", clickedSimpleEvent.getLatitude());
                            eventIntent.putExtra("longitude", clickedSimpleEvent.getLongtitude());
                            eventIntent.putExtra("id", clickedSimpleEvent.getId());
                            startActivity(eventIntent);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
//RecyclerView
/*
    private void setUpRecyclerView(RecyclerView recyclerView, Query query) {


        FirestoreRecyclerOptions<Event> options = new FirestoreRecyclerOptions.Builder<Event>()
                .setQuery(query, Event.class)
                .build();

        eventAdapter = new EventAdapter(options);

        recyclerView.setAdapter(eventAdapter);

        eventAdapter.setOnClickListener(new EventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Event event = documentSnapshot.toObject(Event.class);
                Intent eventIntent = new Intent(SearchActivity.this, EventActivity.class);

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
*/

}
