package com.example.fitnessevent.event;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.fitnessevent.R;
import com.example.fitnessevent.model.Event;
import com.example.fitnessevent.model.SimpleEvent;
import com.example.fitnessevent.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class EventActivity extends AppCompatActivity {
    private static final String TAG = "EventActivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference likesRef = db.collection("users").document("uoOzYdmKBwNOMYcPmEnEYktb9nY1").collection("likes");

    private FirebaseAuth mAuth;
    private SimpleEvent msimpleEvent;
    private DocumentReference dRef = FirebaseFirestore.getInstance().document("/user/uoOzYdmKBwNOMYcPmEnEYktb9nY1");

    private Event event;
    private String docIds = "-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        getIncomingIntent();

        setBtnLike(event);
    }

    private void getIncomingIntent() {
        Log.d(TAG,"getIncomingIntent: Checking for incoming intents.");
        if (getIntent().hasExtra("image_url") && getIntent().hasExtra("title")
                && getIntent().hasExtra("description") && getIntent().hasExtra("startTime")
                && getIntent().hasExtra("endTime") && getIntent().hasExtra("latitude")
                && getIntent().hasExtra("longitude") && getIntent().hasExtra("id")
                && getIntent().hasExtra("object")) {
            Log.d(TAG,"getIncomingIntent: found intent extras" );

            String imageUrl = getIntent().getStringExtra("image_url");
            String title = getIntent().getStringExtra("title");
            String description = getIntent().getStringExtra("description");
            String startTime = getIntent().getStringExtra("startTime");
            String endTime = getIntent().getStringExtra("endTime");
            String latitude = getIntent().getStringExtra("latitude");
            String longitude = getIntent().getStringExtra("longitude");
            String id = getIntent().getStringExtra("id");
            docIds = Integer.toString(getIntent().getIntExtra("length",0) + 1);
            event =(Event) getIntent().getSerializableExtra("object");
            setEvent(imageUrl, title, description, startTime, endTime, latitude, longitude);
        }
    }

    private void setEvent(String imageUrl, String title, String description, String startTime, String endTime, String latitude, String longitude) {
        Log.d(TAG,"Set Event");

        ImageView imageView = findViewById(R.id.imageViewHR);
        Picasso.get().load(imageUrl).into(imageView);

        TextView textViewTitle = findViewById(R.id.title_eventPage);
        textViewTitle.setText(title);

        TextView textViewDescription = findViewById(R.id.contentOfDescription);
        textViewDescription.setText(description);

        TextView textViewtime = findViewById(R.id.time);
        textViewtime.setText(startTime + "-" + endTime);

        TextView textViewAddress = findViewById(R.id.location);
        textViewAddress.setText(latitude + longitude);

    }

    private void setBtnLike(final Event event) {
        final ImageButton like_button = findViewById(R.id.like_button);
        like_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like_button.setImageResource(R.drawable.ic_favorite);

                db.collection("users").document("uoOzYdmKBwNOMYcPmEnEYktb9nY1").collection("likes").document(event.getId())
                        .set(event)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("User","DocumentSnapshot added with liked event");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("User","Error adding like event",e);
                            }
                        });

            }
        });

    }



}
