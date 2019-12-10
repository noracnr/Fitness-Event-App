package com.example.fitnessevent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessevent.model.Address;
import com.example.fitnessevent.model.Category;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class EventCreateActivity extends AppCompatActivity implements View.OnClickListener, DatePicker.OnDateChangedListener, TimePicker.OnTimeChangedListener {
    private static final int PICK_IMAGE_REQUEST = 1;
    final String TAG = "Geocoder";
    private Context context;
    private LinearLayout llDate, llTime, ll1Date, ll1Time;
    private TextView tvDate, tvTime, tv1Date, tv1Time;
    private int year, month, day, hour, minute;
    private StringBuffer date, time;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private EditText editText;
    private EditText edit_event_title, edit_description;
    private Button uploadButton;
    private ImageView mImageView;
    private Uri imageUri;
    //save inforemation of the Address
    private String locationLat;
    private String locationLng;
    private String countryCode;
    private String postalCode;
    //init updateId
    private long idSave;
    //subCategory
    private Spinner spinner;
    private String subCategoryId;
    //set
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //listen id
    private DocumentReference idUpdate = db.collection("id_update").document("idIncrease");
    //changed time
    private String changedTime;
    //save imageUrl
    private String saveUrl;
    //new
    private String localizedAddressDisplay;
    //cloud storage
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);

        //init Storage
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        Calendar calendar = Calendar.getInstance();
        int year_now = calendar.get(Calendar.YEAR);
        int month_now = calendar.get(Calendar.MONTH) + 1;
        int day_now = calendar.get(Calendar.DAY_OF_MONTH);
        int hour_now = calendar.get(Calendar.HOUR_OF_DAY);
        int minute_now = calendar.get(Calendar.MINUTE);
        int second_now = calendar.get(Calendar.SECOND);

        changedTime = year_now + "-" + month_now + "-" + day_now + "T" + hour_now + ":" + minute_now + ":" + second_now + "Z";

        Log.d(TAG,"NOW Time: " + year_now + "/" + month_now + "/" + day_now + ":" + hour_now + minute_now + second_now);

        List<Category> categoryList = new ArrayList<>();

        Category category1 = new Category("Running", "8001");
        categoryList.add(category1);

        Category category2 = new Category("Walking", "8002");
        categoryList.add(category2);

        Category category3 = new Category("Cycling", "8003");
        categoryList.add(category3);

        Category category4 = new Category("Mountain Biking", "8004");
        categoryList.add(category4);

        Category category5 = new Category("Obstacles", "8005");
        categoryList.add(category5);

        Category category6 = new Category("Basketball", "8006");
        categoryList.add(category6);

        Category category7 = new Category("Football", "8007");
        categoryList.add(category7);

        Category category8 = new Category("BaseBall", "8008");
        categoryList.add(category8);

        Category category9 = new Category("Soccer", "8009");
        categoryList.add(category9);

        Category category10 = new Category("Golf", "8010");
        categoryList.add(category10);

        Category category11 = new Category("Volleyball", "8011");
        categoryList.add(category11);

        Category category12 = new Category("Tennis", "8012");
        categoryList.add(category12);

        Category category13 = new Category("Swimming & Water Sports", "8013");
        categoryList.add(category13);

        Category category14 = new Category("Hockey", "8014");
        categoryList.add(category14);

        Category category15 = new Category("Motorsports", "8015");
        categoryList.add(category15);

        Category category16 = new Category("Flighting & Martial Arts", "8016");
        categoryList.add(category16);

        Category category17 = new Category("Snow Sports", "8017");
        categoryList.add(category17);

        Category category18 = new Category("Rugby", "8018");
        categoryList.add(category18);

        Category category19 = new Category("Yoga", "8019");
        categoryList.add(category19);

        Category category20 = new Category("Exercise", "8020");
        categoryList.add(category20);

        Category category21 = new Category("Softball", "8021");
        categoryList.add(category21);

        Category category22 = new Category("Wrestling", "8022");
        categoryList.add(category22);

        Category category23 = new Category("Lacrosse", "8023");
        categoryList.add(category23);

        Category category24 = new Category("Cheer", "8024");
        categoryList.add(category24);

        Category category25 = new Category("Camps", "8025");
        categoryList.add(category25);

        Category category26 = new Category("Weightlifting", "8026");
        categoryList.add(category26);

        Category category27 = new Category("Track & Field", "8027");
        categoryList.add(category27);

        Category category28 = new Category("Other", "8999");
        categoryList.add(category28);

        idUpdate.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            idSave = documentSnapshot.getLong("id");
                            //idSaveInt = Integer.parseInt(idSave) + 1;
                            Log.d(TAG,"Listen success");
                        } else {
                            Log.d(TAG,"Listen Error");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Listen Error","is" + e.getMessage());
                    }
                });

        context = this;
        date = new StringBuffer();
        time = new StringBuffer();
        initView();
        initDateTime();
        initAutoComplete();

        ArrayAdapter<Category> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,categoryList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category category = (Category) parent.getSelectedItem();
                subCategoryId = category.getCategoryId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
    }

    private void uploadImage() {
        if (imageUri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("uploading...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("image/" + UUID.randomUUID().toString());
            ref.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(EventCreateActivity.this,"Upload",Toast.LENGTH_SHORT).show();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    saveUrl = String.valueOf(uri);
                                    Log.d(TAG,"GetDownLoadUrl:" + saveUrl);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,e.getMessage());
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(EventCreateActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded" + (int)progress + "%");
                        }
                    });
        }
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
        edit_description = findViewById(R.id.input_description);
        editText = findViewById(R.id.input_search);
        spinner = findViewById(R.id.spinner_category);
        mImageView = findViewById(R.id.event_imageview);
        uploadButton = findViewById(R.id.upload_button);
    }

    //submit button listen
    public void submit(View v){
        String event_title = edit_event_title.getText().toString();
        String description = edit_description.getText().toString();

        String[] tagArray = {"hello", "hi"};
        List<String> tag = Arrays.asList(tagArray);

        Address address = new Address("address1","address2","Boston","U.S",postalCode,
                countryCode,locationLat,locationLng,localizedAddressDisplay,null,tag);
        Venue venue = new Venue(address,null,null,"2197864",locationLat,locationLng,locationLat,locationLng);

        Start start = new Start("America",startDate + "T" + startTime,"2019-11-24");

        Text name = new Text(event_title,"text");

        Original original = new Original(1255,saveUrl,1129);

        TopLeft topLeft = new TopLeft(247,0);

        CropMask cropMask = new CropMask(1285,topLeft,2500);

        Logo logo = new Logo("2",cropMask,"#989b72",true,"76264781",original,saveUrl);

        End end = new End("America",endDate + "T" + endTime,"2019-11-24T 16:00:00Z");

        Text description1 = new Text(description,"text");

        Event event = new Event(name,description1,Long.toString(idSave + 1),"http://",start,end,null,null,changedTime,null,14,false,
                "continue","USD",false,false,false,45,false,false,"Boston",false,"set",false,false,"invest",
                false,false,false,false,"Boston",false,"3.0","summary","34567123","2341234","231424","108",subCategoryId,
                "87964","http://",false,venue,logo);

        //CreateEvent to users
        db.collection("users").document("uoOzYdmKBwNOMYcPmEnEYktb9nY1").collection("creates").document(Long.toString(idSave + 1)).set(event)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EventCreateActivity.this,"Save",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EventCreateActivity.this,"Error!",Toast.LENGTH_SHORT).show();
                    }
                });
        //CreateEvent to events
        db.collection("events").document(Long.toString(idSave + 1)).set(event)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EventCreateActivity.this,"Save",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EventCreateActivity.this,"Error!",Toast.LENGTH_SHORT).show();
                    }
                });

        //IdSave
        Map<String,Object> saveId = new HashMap<>();
        saveId.put("id",idSave + 1);
        db.collection("id_update").document("idIncrease").set(saveId)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("idSave:","Successed");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("idSave:", "Failed");
                    }
                });
        Intent intent = new Intent(EventCreateActivity.this,MainActivity.class);
        startActivity(intent);

    }
    //intent to the file in the phone
    public void toImageFile(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null
                && data.getData() != null) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(mImageView);
        }

    }
    //update button listen
    public void update(View v) {
        String event_title = edit_event_title.getText().toString();
        String description = edit_description.getText().toString();

        String[] tagArray = {"hello", "hi"};
        List<String> tag = Arrays.asList(tagArray);

        Address address = new Address("address1","address2","Boston","U.S",postalCode,
                countryCode,locationLat,locationLng,localizedAddressDisplay,null,tag);
        Venue venue = new Venue(address,null,null,"2197864",locationLat,locationLng,locationLat,locationLng);

        Start start = new Start("America",startDate + "T" + startTime,"2019-11-24");

        Text name = new Text(event_title,"text");

        Original original = new Original(1255,"http://",1129);

        TopLeft topLeft = new TopLeft(247,0);

        CropMask cropMask = new CropMask(1285,topLeft,2500);

        Logo logo = new Logo("2",cropMask,"#989b72",true,"76264781",original,"https://github.com/Fitness-Event-APP/Fitness/blob/master/HatchfulExport-All/instagram_profile_image.png");

        End end = new End("America",endDate + "T" + endTime,"2019-11-24T 16:00:00Z");

        Text description1 = new Text(description,"text");

        Event event = new Event(name,description1,Long.toString(idSave),"http://",start,end,null,null,changedTime,null,14,false,
                "continue","USD",false,false,false,45,false,false,"Boston",false,"set",false,false,"invest",
                false,false,false,false,"Boston",false,"3.0","summary","34567123","2341234","231424","108",subCategoryId,
                "87964","http://",false,venue,logo);

        //update CreateEvent to users
        db.collection("users").document("uoOzYdmKBwNOMYcPmEnEYktb9nY1").collection("creates").document(Long.toString(idSave)).set(event)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EventCreateActivity.this,"Update Successed",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EventCreateActivity.this,"Error!",Toast.LENGTH_SHORT).show();
                    }
                });
        //update CreateEvent to events
        db.collection("events").document(Long.toString(idSave)).set(event)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EventCreateActivity.this,"Update Successed",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EventCreateActivity.this,"Error!",Toast.LENGTH_SHORT).show();
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
                        startDate = year + "-" + month + "-" + day;
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
                        endDate = year + "-" + month + "-" + day;

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
                        startTime = hour + ":" + minute + ":" + "00";
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
                        endTime = hour + ":" + minute + ":" + "00";
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
                TimePicker timePicker3 = dialogView3.findViewById(R.id.timePicker);
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
        this.month = monthOfYear + 1;
        this.day = dayOfMonth;

    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        this.hour = hourOfDay;
        this.minute = minute;

    }

    public void initAutoComplete() {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        || event.getAction() == KeyEvent.KEYCODE_ENTER) {
                    geoLocate();
                }

                return false;
            }


        });

    }

    public void geoLocate() {
        String searchString = editText.getText().toString();
        Geocoder geocoder = new Geocoder(EventCreateActivity.this);

        List<android.location.Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString,1);
        }catch (IOException e){
            Log.e(TAG,"geoLocate: exception: " + e.getMessage());
        }

        if(list.size() > 0){
            android.location.Address address = list.get(0);

            Log.d(TAG, "Geolocate: found a location: " + address.toString() + "!!" +address.getAddressLine(0));
            locationLat = "" + address.getLatitude();
            locationLng = "" + address.getLongitude();
            countryCode = address.getCountryCode();
            postalCode = address.getPostalCode();
            localizedAddressDisplay = address.getAddressLine(0);
        }
    }

}