package com.example.fitnessevent.event;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import com.example.fitnessevent.R;
import com.example.fitnessevent.model.Event;
import com.example.fitnessevent.model.SimpleEvent;
import com.example.fitnessevent.ui.notifications.NotificationsFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;


public class EventActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "EventActivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference likesRef = db.collection("users").document("uoOzYdmKBwNOMYcPmEnEYktb9nY1").collection("likes");

    private FirebaseAuth mAuth;
    private SimpleEvent msimpleEvent;
    private DocumentReference dRef = FirebaseFirestore.getInstance().document("/user/uoOzYdmKBwNOMYcPmEnEYktb9nY1");

    private Event event;
    private String docIds = "-1";
    private String userId;

    private String longitude;
    private String latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);

        supportMapFragment.getMapAsync(this);

        getIncomingIntent();

        setBtnLike(event);
    }

    public void onMapReady(GoogleMap googleMap) {
        Log.d("longitude: ", longitude + "**********************");
        Log.d("latitude: ", latitude + "**********************");
        if (longitude == null || latitude == null) {
            longitude = "42.35";
            latitude = "-71.10";
        }
        double d_longitude = Double.parseDouble(longitude);
        double d_latitude = Double.parseDouble(latitude);
        LatLng coordinate = new LatLng(d_latitude,d_longitude);
        googleMap.addMarker(new MarkerOptions().position(coordinate));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate,12));

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
            latitude = getIntent().getStringExtra("latitude");
            longitude = getIntent().getStringExtra("longitude");
            String id = getIntent().getStringExtra("id");
            docIds = Integer.toString(getIntent().getIntExtra("length",0) + 1);
            event =(Event) getIntent().getSerializableExtra("object");
            setEvent(imageUrl, title, description, startTime, endTime, latitude, longitude);
        }
    }

    private void setEvent(String imageUrl, String title, String description, String startTime, String endTime, String latitude, String longitude) {
        Log.d(TAG,"Set Event");

        ImageButton imageButton = findViewById(R.id.location_icon);

        ImageView imageView = findViewById(R.id.imageViewHR);
        Picasso.get().load(imageUrl).into(imageView);

        TextView textViewTitle = findViewById(R.id.title_eventPage);
        textViewTitle.setText(title);

        TextView textViewDescription = findViewById(R.id.contentOfDescription);
        textViewDescription.setText(description);

        TextView textViewtime = findViewById(R.id.time);

        textViewtime.setText(startTime+"\n"+endTime);

        TextView textViewAddress = findViewById(R.id.location);
        if (event.getVenue() != null) {
            if (event.getVenue().getAddress() != null) {
                if (event.getVenue().getAddress().getLocalizedAddressDisplay() != null)
                    textViewAddress.setText(event.getVenue().getAddress().getLocalizedAddressDisplay().replace(", ", "\n"));
                else
                    textViewAddress.setText("null");
            } else {
                textViewAddress.setText("null");
            }
        } else {
            textViewAddress.setText("null");
        }
    }





    private void setBtnLike(final Event event) {
        Log.d(TAG, "id" + userId);
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

    public void ToMapApp(View v) {
        // Create a Uri from an intent string. Use the result to create an Intent.
        Log.d(TAG,"Go to Google Map *****");
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude);

        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        // Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");

        // Attempt to start an activity that can handle the Intent
        startActivity(mapIntent);

    }


}
