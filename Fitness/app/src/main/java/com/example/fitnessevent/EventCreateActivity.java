package com.example.fitnessevent;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessevent.model.Address;
import com.example.fitnessevent.model.CropMask;
import com.example.fitnessevent.model.End;
import com.example.fitnessevent.model.Event;
import com.example.fitnessevent.model.Logo;
import com.example.fitnessevent.model.Original;
import com.example.fitnessevent.model.Start;
import com.example.fitnessevent.model.Text;
import com.example.fitnessevent.model.TopLeft;
import com.example.fitnessevent.model.Venue;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventCreateActivity extends AppCompatActivity implements View.OnClickListener, DatePicker.OnDateChangedListener, TimePicker.OnTimeChangedListener {

//    private Time time;

    private Context context;
    private LinearLayout llDate, llTime, ll1Date, ll1Time;
    private TextView tvDate, tvTime, tv1Date, tv1Time;
    private ScrollView scrollView;
    private int year, month, day, hour, minute;
    //display on textview
    private StringBuffer date, time;
    ////////////////////////
    private static final String TAG = "EVENTCREATE";
    private static final String KEY_TITLE = "CreateEvent";
    private static final String KEY_LOCATION = "Location";
    private static final String KEY_QUANTITY = "Quantity";
    private static final String KEY_PRICE = "Price";
    private static final String KEY_DESCRIPTION = "Description";
    private static final String KEY_START_DATE = "Start Date";
    private static final String KEY_START_TIME = "Start Time";
    private static final String KEY_END_DATE = "End Date";
    private static final String KEY_END_TIME = "End Time";

    private static final String KEY_CURRENCY = "Currency";

    private EditText edit_event_title, edit_location, edit_description, edit_quantity, edit_price;
    private Button button;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    ///////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);

        context = this;
        date = new StringBuffer();
        time = new StringBuffer();
        initView();
        initDateTime();

        ////////////////////////////////////////////////////////////////




    }

    private void initDateTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
    }

    private void initView() {
        llDate = findViewById(R.id.ll_date);
        ll1Date = findViewById(R.id.ll1_date);
        tvDate =  findViewById(R.id.tv_date);
        llTime =  findViewById(R.id.ll_time);
        ll1Time = findViewById(R.id.ll1_time);
        tvTime =  findViewById(R.id.tv_time);
        tv1Date = findViewById(R.id.tv1_date);
        tv1Time =findViewById(R.id.tv1_time);
        llDate.setOnClickListener(this);
        llTime.setOnClickListener(this);
        ll1Date.setOnClickListener(this);
        ll1Time.setOnClickListener(this);


        edit_event_title = findViewById(R.id.editText);
        edit_location = findViewById(R.id.input_location);
        edit_description = findViewById(R.id.input_description);
        edit_quantity = findViewById(R.id.input_quantity);
        edit_price = findViewById(R.id.input_price);
        button = findViewById(R.id.submit_button);
    }

    public void submit(View v){
        String event_title = edit_event_title.getText().toString();
        String location = edit_location.getText().toString();
        String description = edit_description.getText().toString();
        String quantity = edit_quantity.getText().toString();
        String price = edit_price.getText().toString();
        String start_date = tvDate.getText().toString();
        String start_time = tvTime.getText().toString();
        String end_date = tv1Date.getText().toString();
        String end_time = tv1Time.getText().toString();

        Map<String,Object> note = new HashMap<>();
        note.put(KEY_TITLE,event_title);
        note.put(KEY_LOCATION,location);
        note.put(KEY_DESCRIPTION,description);
        note.put(KEY_QUANTITY,quantity);
        note.put(KEY_PRICE,price);
        note.put(KEY_START_DATE,start_date);
        note.put(KEY_START_TIME,start_time);
        note.put(KEY_END_DATE,end_date);
        note.put(KEY_END_TIME,end_time);
        note.put(KEY_CURRENCY, "USD");


//
//        SmallCity smallCity = new SmallCity("Peter", "4/19/20");


//
//        City city = new City(null, "CA", "USA",
//                false, 5000000L, Arrays.asList("west_coast", "sorcal"),smallCity);
//        db.collection("cities").document("LA").set(city);
        String[] tagArray = {"hello", "hi"};
        List<String> tag = Arrays.asList(tagArray);


        Address address = new Address(null,null,"Boston","U.S","-34.618","San Rafael, Mendoza 5600","San Rafael, M",
                "-68.43","5600","Mendoza",tag);
        Venue venue = new Venue(address,null,null,"2197864","-34.618","-68.334","Los","http://github.com");

        Start start = new Start("2019-11-24T10:00:00","America","2019-11-24");

        Text name = new Text("html","text");

        Original original = new Original("http://",1255,1129);

        TopLeft topLeft = new TopLeft(247,0);

        CropMask cropMask = new CropMask(topLeft,1285,2500);

        Logo logo = new Logo(cropMask,original,"2","http://github.com","76264781","#989b72",true);

        End end = new End("2019-11-24T 13:00:00","America","2019-11-24T 16:00:00Z");

/// Description1 need change to description
        Text description1 = new Text("html","text");

        Event event = new Event(name,description1,"23452134","http://",start,end,null,null,null,null,null,false,
                "USA","USD",false,false,false,45,false,false,"Boston",false,"set",false,
                false,"3.0",false,true,true,false,"http://",true,
                "3.0","summary","2153768","6772819","671","108","8018","f643289","https://",true,logo,venue);


        db.collection("events").document("10000002").set(event);







//        Name name = new Name("html:123","text:456");
//        Start start = new Start("NY","ETC", "2019/5/4");
//        Original original = new Original(171,"urlhttps",1);
//        TopLeft topLeft = new TopLeft(3,4);
//        CropMask cropMask = new CropMask(154,topLeft,1);
//        Logo logo = new Logo("1.4", cropMask,"white",false,"1234556",original,"1234567");
//        End end = new End("Boston","ETC","2019/5/7");
//        // Description
//        Description description1 = new Description("html","hello world");
//
//        Events events = new Events(null,null,11,null,null,
//                "USD",description1,end,null,null,null,"1234567",
//                null,false,false,false,false,
//                false,false, false,"local1",logo, "321456",
//                name, false, "23221323",null,null,null,
//                null,false, false, false,false,
//                "2345",start,null,null,null,13,null,null,null
//        );
//
//        db.collection("user").document("EventCreate").set(events);







        db.collection("user").document("CreateEvent").set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EventCreateActivity.this,"Save!!!", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EventCreateActivity.this,"NOT SAVED !!!", Toast.LENGTH_SHORT).show();
                Log.d(TAG,e.toString());

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_date:
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (date.length() > 0) {
                            date.delete(0, date.length());
                        }
                        tvDate.setText(date.append((month)).append("/").append(day).append("/").append(year));
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                final AlertDialog dialog = builder.create();
                View dialogView = View.inflate(context, R.layout.dialog_date, null);
                final DatePicker datePicker = dialogView.findViewById(R.id.datePicker);

                dialog.setTitle("Setting");
                dialog.setView(dialogView);
                dialog.show();
                //init listener
                datePicker.init(year, month - 1, day, this);
                break;

            case R.id.ll1_date:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (date.length() > 0) {
                            date.delete(0, date.length());
                        }
                        tv1Date.setText(date.append((month)).append("/").append(day).append("/").append(year));
                        dialog.dismiss();
                    }
                });
                builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                final AlertDialog dialog1 = builder1.create();
                View dialogView1 = View.inflate(context, R.layout.dialog_date, null);
                final DatePicker datePicker1 = dialogView1.findViewById(R.id.datePicker);

                dialog1.setTitle("Setting");
                dialog1.setView(dialogView1);
                dialog1.show();

                datePicker1.init(year, month - 1, day, this);
                break;

            case R.id.ll_time:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                builder2.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (time.length() > 0) {
                            time.delete(0, time.length());
                        }
                        tvTime.setText(time.append(String.valueOf(hour)).append(":").append(String.valueOf(minute)));
                        dialog.dismiss();
                    }
                });
                builder2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog2 = builder2.create();
                View dialogView2 = View.inflate(context, R.layout.dialog_time, null);
                TimePicker timePicker = dialogView2.findViewById(R.id.timePicker);
                timePicker.setCurrentHour(hour);
                timePicker.setCurrentMinute(minute);
                timePicker.setIs24HourView(true);
                // 24h
                timePicker.setOnTimeChangedListener(this);
                dialog2.setTitle("Setting");
                dialog2.setView(dialogView2);
                dialog2.show();
                break;
            case R.id.ll1_time:
                AlertDialog.Builder builder3 = new AlertDialog.Builder(context);
                builder3.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (time.length() > 0) {
                            time.delete(0, time.length());
                        }
                        tv1Time.setText(time.append(String.valueOf(hour)).append(":").append(String.valueOf(minute)));
                        dialog.dismiss();
                    }
                });
                builder3.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog3 = builder3.create();
                View dialogView3 = View.inflate(context, R.layout.dialog_time, null);
                TimePicker timePicker3 = (TimePicker) dialogView3.findViewById(R.id.timePicker);
                timePicker3.setCurrentHour(hour);
                timePicker3.setCurrentMinute(minute);
                timePicker3.setIs24HourView(true); //设置24小时制
                timePicker3.setOnTimeChangedListener(this);
                dialog3.setTitle("Setting");
                dialog3.setView(dialogView3);
                dialog3.show();
                break;

        }

    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear;
        this.day = dayOfMonth;

    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        this.hour = hourOfDay;
        this.minute = minute;

    }
}