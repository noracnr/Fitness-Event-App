package com.example.fitnessevent.ui.notifications;

import android.os.Bundle;
import android.widget.TextView;

import com.example.fitnessevent.BaseActivity;
import com.example.fitnessevent.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MyInterestsActivity extends BaseActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_interests);
        final TextView mInterests = findViewById(R.id.myInterestsText);

        DocumentReference docRef = FirebaseFirestore.getInstance().document("/user/uoOzYdmKBwNOMYcPmEnEYktb9nY2");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {

                    ArrayList<String> interests = (ArrayList<String>)documentSnapshot.get("interests");
                    if (interests.isEmpty())
                        return;
                    StringBuilder builder = new StringBuilder();
                    int i = 1;
                    for (String s: interests) {
                        builder.append(i++ + ": "+ s + "\n\n");
                    }
                    mInterests.setText(builder.toString().trim());
                }
            }
        });
    }
}
