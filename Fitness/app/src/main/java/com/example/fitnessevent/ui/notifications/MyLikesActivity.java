package com.example.fitnessevent.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessevent.BaseActivity;
import com.example.fitnessevent.R;
import com.example.fitnessevent.event.EventActivity;
import com.example.fitnessevent.event.EventAdapter;
import com.example.fitnessevent.event.SearchEventAdapter;
import com.example.fitnessevent.model.Event;
import com.example.fitnessevent.model.SimpleEvent;
import com.example.fitnessevent.ui.home.SearchActivity;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyLikesActivity extends BaseActivity {
    private SearchEventAdapter searchEventAdapter;
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference likeRef = db.collection("users").document("uoOzYdmKBwNOMYcPmEnEYktb9nY1").collection("likes");
    private CollectionReference eventRef = db.collection("events");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_likes);
        //final TextView mLikes = findViewById(R.id.myLikesText);

//
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();;
//        FirebaseUser user = mAuth.getCurrentUser();
//        String userID = getString(R.string.firebase_status_fmt, user.getUid());
//
//        DocumentReference docRef = FirebaseFirestore.getInstance().document("/user/" + userID);
/*
        DocumentReference docRef = FirebaseFirestore.getInstance().document("/likes/uoOzYdmKBwNOMYcPmEnEYktb9nY1");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    final List<SimpleEvent> likes = (ArrayList<SimpleEvent>) documentSnapshot.getData();
                    //final List<SimpleEvent> likes = (ArrayList<SimpleEvent>)documentSnapshot.get("likes");
                    if (likes.isEmpty())
                        return;

                    StringBuilder builder = new StringBuilder();
                    int i = 1;
                    for (SimpleEvent s: likes) {
                        builder.append(i++ + ": "+ s + "\n\n");
                    }


                }
            }
        });
*/


        Query query = eventRef.orderBy("changed", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Event> options = new FirestoreRecyclerOptions.Builder<Event>()
                .setQuery(query, Event.class)
                .build();

        eventAdapter = new EventAdapter(options);

        recyclerView = findViewById(R.id.myLikesRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(eventAdapter);


    }



}
