package com.example.fitnessevent.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.fitnessevent.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;


public class EventActivity extends AppCompatActivity {
    private static final String TAG = "EventActivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        getIncomingIntent();
    }

    private void getIncomingIntent() {
        Log.d(TAG,"getIncomingIntent: Checking for incoming intents.");
        if (getIntent().hasExtra("image_url") && getIntent().hasExtra("title")
                && getIntent().hasExtra("description") && getIntent().hasExtra("startTime")
                && getIntent().hasExtra("endTime") && getIntent().hasExtra("address")) {
            Log.d(TAG,"getIncomingIntent: found intent extras");

            String imageUrl = getIntent().getStringExtra("image_url");
            String title = getIntent().getStringExtra("title");
            String description = getIntent().getStringExtra("description");
            String startTime = getIntent().getStringExtra("startTime");
            String endTime = getIntent().getStringExtra("endTime");
            String address = getIntent().getStringExtra("address");

            setEvent(imageUrl, title, description, startTime, endTime, address);
        }
    }

    private void setEvent(String imageUrl, String title, String description, String startTime, String endTime, String address) {
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
        textViewAddress.setText(address);
    }


}
