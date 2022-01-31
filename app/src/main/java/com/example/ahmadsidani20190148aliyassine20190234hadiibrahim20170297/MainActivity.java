package com.example.ahmadsidani20190148aliyassine20190234hadiibrahim20170297;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String notitype="off";
    private FirebaseAuth mAuth;
    private static final String TAG = "MainActivity";
    ListView newsListView;
    List<news> newsList;
    DatabaseReference ref;
    static boolean calledAlready = false;
    static boolean calledAlready2 = false;
    ListAdapter adapter;
    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
        SharedPreferences sharedPrefences = getSharedPreferences("News", MODE_PRIVATE);
        SharedPreferences sharedPref = getSharedPreferences("FileName",MODE_PRIVATE);
        boolean switchType = sharedPrefences.getBoolean("switch",true);
        notitype=sharedPref.getString("spinner","All News");
        if(!switchType){
            notitype="off";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPrefences = getSharedPreferences("News", MODE_PRIVATE);
        boolean switchType = sharedPrefences.getBoolean("switch",true);
        SharedPreferences sharedPref = getSharedPreferences("FileName",MODE_PRIVATE);
        notitype=sharedPref.getString("spinner","All News");
        notitype=sharedPref.getString("spinner","All News");
        if(!switchType){
            notitype="off";
        }

        if (!calledAlready){
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            calledAlready = true;
        }
        mAuth = FirebaseAuth.getInstance();
        DatabaseReference newssRef = FirebaseDatabase.getInstance().getReference("news");
        newssRef.keepSynced(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        newsListView = (ListView) findViewById(R.id.newsListView);
        newsList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("news");
        if (!calledAlready2){
            ref.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    news n=snapshot.getValue(news.class);
                        if(n.isactive){
                            if(notitype.equals("All News")){
                                notification(n.getTitle(),n.getDescription());
                            }else if(notitype.equals("Sports")){
                                if(n.getCategory().equals("Sports")){
                                    notification(n.getTitle(),n.getDescription());
                                }
                            }
                            else if(notitype.equals("Breaking News")){
                                if(n.getCategory().equals("Breaking News")){
                                    notification(n.getTitle(),n.getDescription());
                                }
                            }
                            else if(notitype.equals("Local")){
                                if(n.getCategory().equals("Local")){
                                    notification(n.getTitle(),n.getDescription());
                                }
                            }
                            else if(notitype.equals("Global")){
                                if(n.getCategory().equals("Global")){
                                    notification(n.getTitle(),n.getDescription());
                                }
                            }
                            else if(notitype.equals("Politics")){
                                if(n.getCategory().equals("Politics")){
                                    notification(n.getTitle(),n.getDescription());
                                }
                            }
                            else if(notitype.equals("Economics")){
                                if(n.getCategory().equals("Economics")){
                                    notification(n.getTitle(),n.getDescription());
                                }
                            }
                            else if(notitype.equals("Health")){
                                if(n.getCategory().equals("Health")){
                                    notification(n.getTitle(),n.getDescription());
                                }
                            }
                            else if(notitype.equals("Technology")){
                                if(n.getCategory().equals("Technology")){
                                    notification(n.getTitle(),n.getDescription());
                                }
                            }
                        }

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            calledAlready2 = true;
        }

        adapter = new ListAdapter(MainActivity.this, newsList);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    newsList.clear();

                for (DataSnapshot newsSnapshot : dataSnapshot.getChildren()) {
                    news n = newsSnapshot.getValue(news.class);
                    if (n.isactive) {
                        newsList.add(0, n);
                        adapter = new ListAdapter(MainActivity.this, newsList);
                        newsListView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


        EditText search = findViewById(R.id.search);
search.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String temp = s.toString();

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                newsList.clear();
                for (DataSnapshot newsSnapshot : dataSnapshot.getChildren()) {
                    news n = newsSnapshot.getValue(news.class);
                    if(n.isactive) {
                        if (n.title.indexOf(temp) >= 0 || n.description.indexOf(temp) >= 0 || n.keyword.indexOf(temp)>0) {

                            newsList.add(0, n);
                            adapter = new ListAdapter(MainActivity.this, newsList);
                            newsListView.setAdapter(adapter);
                        }
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


    }

    @Override
    public void afterTextChanged(Editable s) {

    }
});


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // Inflate the menu; this adds items to the action bar if it is present.
        if(currentUser != null){
            getMenuInflater().inflate(R.menu.menu_admin, menu);
            return true;
        }else{
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,Settings.class);
            startActivity(intent);
        }else if(id == R.id.action_login){
            Intent intent = new Intent(this,Login.class);
            startActivity(intent);
        }
        else if(id==R.id.action_logout){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

            Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
        }else if(id==R.id.action_addnews){
            Intent intent =new Intent(this,Admin.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    private void notification(String title,String text){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("news","news", NotificationManager.IMPORTANCE_DEFAULT);


            NotificationManager manager =getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"news")
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_news)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentText(text);

        NotificationManagerCompat managerCompat =NotificationManagerCompat.from(this);
        managerCompat.notify(999,builder.build());
    }



}