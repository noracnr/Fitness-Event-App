package com.example.trestapi2firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.trestapi2firebase.model.Event;
import com.example.trestapi2firebase.model.PaginatedEvents;
import com.example.trestapi2firebase.model.Text;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private final String TAG="firebaseTest";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private TextView textViewResult;

    private ArrayList<ExampleItem> exampleList;
    private FirebaseFirestore db;

    //private JsonPlaceHolderApi jsonPlaceHolderApi;
    private EventbriteApi eventbriteApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);

        //recyclerView = findViewById(R.id.recycler_view);



/*
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
*/
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl("https://www.eventbriteapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

         //jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
         eventbriteApi = retrofit1.create(EventbriteApi.class);

        //getPosts();
        //getComments();
        getEvents();
/*
        exampleList = new ArrayList<>();
        exampleList.add(new ExampleItem(R.drawable.ic_android,"90","Virtual Fitness Room","Sydner"));
        exampleList.add(new ExampleItem(R.drawable.ic_android,"2020", "Golf Fair Asia 2020 - Malaysia (International Event)","Kuala_Lumpur"));
        exampleList.add(new ExampleItem(R.drawable.ic_android,"2018-08-01T10:00:00", "Interessensbekundung im Rollstuhl- und Sehbehindertenbereich","Berlin"));

        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // specify an adapter
        adapter = new MyAdapter(exampleList);
        recyclerView.setAdapter(adapter);
*/

    }
/*
    private void getPosts() {
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts(4);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                List<Post> posts = response.body();

                for (Post post : posts) {
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "ID: " + post.getId() + "\n\n";

                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }


*/

    private void getEvents() {
        Call<PaginatedEvents> call = eventbriteApi.getPaginatedEvents(108, "IRN4X6MKLUWHLIFGBEDY");

        call.enqueue(new Callback<PaginatedEvents>() {
            @Override
            public void onResponse(Call<PaginatedEvents> call, Response<PaginatedEvents> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                PaginatedEvents paginatedEvents = response.body();


                db = FirebaseFirestore.getInstance();


                for (Event event : paginatedEvents.getEvents()) {
                    String content = "";
                    content += "ID: " + event.getId() + "\n";
                    content += "name: " + event.getName().getText() + "\n";
                    content += "description: " + event.getDescription().getText() + "\n";
                    content += "url: " + event.getUrl() + "\n\n";

                    textViewResult.append(content);

                    Map<String, String> user = new HashMap<>();
                    user.put("text", event.getName().getText());
                    user.put("html", event.getName().getHtml());

                    Text name = new Text(event.getName().getText(),event.getName().getHtml());

                    db.collection("events").document(event.getId())
                            .set(name)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot added with name ");
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
                textViewResult.setText(t.getMessage());
            }
        });
    }


    private ArrayList<ExampleItem> fillItems(PaginatedEvents paginatedEvents) {
        exampleList = new ArrayList<>();
        exampleList.add(new ExampleItem(R.drawable.ic_android,"90","Virtual Fitness Room","Sydner"));
        exampleList.add(new ExampleItem(R.drawable.ic_android,"2020", "Golf Fair Asia 2020 - Malaysia (International Event)","Kuala_Lumpur"));
        exampleList.add(new ExampleItem(R.drawable.ic_android,"2018-08-01T10:00:00", "Interessensbekundung im Rollstuhl- und Sehbehindertenbereich","Berlin"));
        return exampleList;
    }





}
