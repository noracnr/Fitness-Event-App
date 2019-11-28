package com.example.fitnessevent.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fitnessevent.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class FilterActivity extends AppCompatActivity {
    private static final String TAG = "FilterActivity";
    private Spinner spinner_category;

    private Map<String, String> categoryValue;
    private String selectedCategoryValue;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference eventRef = db.collection("events");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        categoryValue = new HashMap<>();
        InitCategoryValue();
        setUpCategorySpinner();


    }

    private void InitCategoryValue() {
        categoryValue.put("Baseball","8008");
        categoryValue.put("Basketball","8006");
        categoryValue.put("Camps","8025");
        categoryValue.put("Cheer","8024");
        categoryValue.put("Cycling","8003");
        categoryValue.put("Exercise","8020");
        categoryValue.put("Fighting &amp; Martial Arts","8016");
        categoryValue.put("Football","8007");
        categoryValue.put("Golf","8010");
        categoryValue.put("Hockey","8014");
        categoryValue.put("Lacrosse","8023");
        categoryValue.put("Motorsports","8015");
        categoryValue.put("Mountain Biking","8004");
        categoryValue.put("Obstacles","8005");
        categoryValue.put("Running","8001");
        categoryValue.put("Rugby","8018");
        categoryValue.put("Snow Sports","8017");
        categoryValue.put("Soccer","8009");
        categoryValue.put("Softball","8021");
        categoryValue.put("Swimming &amp; Water Sports","8013");
        categoryValue.put("Tennis","8012");
        categoryValue.put("Track &amp; Field","8027");
        categoryValue.put("Volleyball","8011");
        categoryValue.put("Walking","8002");
        categoryValue.put("Weightlifting","8026");
        categoryValue.put("Wrestling","8022");
        categoryValue.put("Yoga","8019");
        categoryValue.put("Other","8999");

    }

    private void setUpCategorySpinner() {

        spinner_category = (Spinner) findViewById(R.id.category_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.catelogy_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_category.setAdapter(adapter);

        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG,"successfully selected item");
                if (categoryValue.containsKey(parent.getItemAtPosition(position).toString())) {
                    selectedCategoryValue = categoryValue.get(parent.getItemAtPosition(position).toString());
                    Log.d(TAG,"successfully get category value" + selectedCategoryValue);
                } else {
                    Log.d(TAG,"key is not correct:" + parent.getItemAtPosition(position).toString());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.submit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.submit) {
            Intent filterIntent = new Intent();
            filterIntent.putExtra("category", selectedCategoryValue);
            setResult(0,filterIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);

    }
}
