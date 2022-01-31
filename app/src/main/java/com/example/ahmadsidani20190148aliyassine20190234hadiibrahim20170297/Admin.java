package com.example.ahmadsidani20190148aliyassine20190234hadiibrahim20170297;

import android.content.Intent;
import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.text.SimpleDateFormat;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class Admin extends AppCompatActivity {
    String email;
    long maxid = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                 email = profile.getEmail();
            }
        }


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("news");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String[] spinnerContents = {"Breaking News","Local", "Global", "Politics",  "Economics", "Health", "Sports", "Technology"};
        Spinner s = (Spinner) findViewById(R.id.category);

        ArrayAdapter<String> myadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerContents);
        s.setAdapter(myadapter);

        TextView title = (TextView) findViewById(R.id.title);
        TextView description = (TextView) findViewById(R.id.description);
        TextView keywords = (TextView) findViewById(R.id.keywords);




    Button add = (Button) findViewById(R.id.addbutton);
        add.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            String s_title = title.getText().toString();
            String s_description = description.getText().toString();
            String s_keywords = keywords.getText().toString();
            String s_spinner = s.getSelectedItem().toString();
            String currentDateTime = new SimpleDateFormat("EEE, h:mm a").format(Calendar.getInstance().getTime());

            boolean check = true;

                if (s_title.equals("")) {
                    check = false;
                    title.setError("Enter title");
                }

                if (s_description.equals("")) {
                    check = false;
                    description.setError("Enter description");
                }

                if (s_keywords.equals("")) {
                    check = false;
                    keywords.setError("Enter keywords");
                }

            if (check == true){
                s.setSelection(0);
                title.setText("");
                description.setText("");
                keywords.setText("");
                save(s_title, s_spinner, s_description,s_keywords,currentDateTime,email,v);
            }

        }
    });
}


    public void save(String s_title, String s_spinner, String s_description, String s_keywords, String currentDateTime,String admin, View v)
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("news");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists() ){
                    maxid = (snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Intent intent = new Intent(this,MainActivity.class);

        HandlerThread handlerThread = new HandlerThread("hideTextHandlerThread");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        Handler mainHandler = new Handler(Admin.this.getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        news c = new news(maxid+1, s_spinner, s_title, s_description,s_keywords,currentDateTime, admin);
                        ref.child(String.valueOf(maxid+1)).setValue(c);


                        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);



                        Toast.makeText(getApplication(), "News Added Successfully", Toast.LENGTH_SHORT).show();
                        HandlerThread handlerThread = new HandlerThread("hideTextHandlerThread");
                        handlerThread.start();
                        Handler handler = new Handler(handlerThread.getLooper());
                        Handler mainHandler = new Handler(Admin.this.getMainLooper());

                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                mainHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                });
                                handler.getLooper().quit();
                            }
                        };
                        handler.post(runnable);
                    }
                });
                handler.getLooper().quit();
            }
        };
        handler.post(runnable);

    }
}

