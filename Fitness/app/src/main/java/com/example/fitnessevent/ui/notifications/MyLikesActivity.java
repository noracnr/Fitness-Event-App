package com.example.fitnessevent.ui.notifications;

import android.os.Bundle;
import android.widget.TextView;

import com.example.fitnessevent.BaseActivity;
import com.example.fitnessevent.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MyLikesActivity extends BaseActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_likes);
        final TextView mLikes = findViewById(R.id.myLikesText);
//
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();;
//        FirebaseUser user = mAuth.getCurrentUser();
//        String userID = getString(R.string.firebase_status_fmt, user.getUid());
//
//        DocumentReference docRef = FirebaseFirestore.getInstance().document("/user/" + userID);

        DocumentReference docRef = FirebaseFirestore.getInstance().document("/user/uoOzYdmKBwNOMYcPmEnEYktb9nY2");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    ArrayList<String> likes = (ArrayList<String>)documentSnapshot.get("likes");
                    StringBuilder builder = new StringBuilder();
                    for (String s: likes) {
                        builder.append(s + "\n");
                    }
                    mLikes.setText(builder.toString().trim());
                }
            }
        });
    }



}
