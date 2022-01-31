package com.example.ahmadsidani20190148aliyassine20190234hadiibrahim20170297;


import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Settings extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    private Switch mySwitch;
    Spinner selection_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        reference=database.getInstance().getReference().child("news");
         mySwitch = (Switch) findViewById(R.id.mySwitch);
        selection_spinner=(Spinner)findViewById(R.id.selection_spinner);
        SharedPreferences settings = getSharedPreferences("News",MODE_PRIVATE);
        boolean silent = settings.getBoolean("switch", true);
        mySwitch.setChecked(silent);



        LinearLayout mylin=(LinearLayout)findViewById(R.id.spinnerlinear);

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mySwitch.isChecked()){
                    mylin.setVisibility(View.VISIBLE);
                }else{
                    mylin.setVisibility(View.INVISIBLE);
                }
                if(isChecked){
                    Toast.makeText(Settings.this, "NOTIFICATION ARE ENABLED", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Settings.this, "NOTIFICATION ARE DISABLED", Toast.LENGTH_SHORT).show();
                }
                SharedPreferences settings = getSharedPreferences("News",MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("switch", isChecked);
                editor.commit();
            }
        });


        SharedPreferences sharedPref = getSharedPreferences("FileName",MODE_PRIVATE);
        int spinnerValue = sharedPref.getInt("userChoiceSpinner",-1);
        if(spinnerValue != -1) {
            selection_spinner.setSelection(spinnerValue);
        }
        String s=sharedPref.getString("spinner","All News");



        selection_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int userChoice = selection_spinner.getSelectedItemPosition();
                SharedPreferences sharedPref = getSharedPreferences("FileName",0);
                SharedPreferences.Editor prefEditor = sharedPref.edit();
                prefEditor.putInt("userChoiceSpinner",userChoice);
                String spinnerChoice=selection_spinner.getSelectedItem().toString();
                prefEditor.putString("spinner",spinnerChoice);
                prefEditor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        if (!mySwitch.isChecked()){

            mylin.setVisibility(View.INVISIBLE);
        }
    }

}