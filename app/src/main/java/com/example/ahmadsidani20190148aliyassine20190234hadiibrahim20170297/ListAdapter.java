package com.example.ahmadsidani20190148aliyassine20190234hadiibrahim20170297;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;

public class ListAdapter extends ArrayAdapter  {
    private Activity Context;
    TextView numOfLikes;
    private FirebaseAuth mAuth;

    List<news>  newsList;
    public ListAdapter(Activity Context,List<news>  newsList){
        super(Context,R.layout.row,newsList);
        this.Context=Context;
        this.newsList=newsList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        mAuth = FirebaseAuth.getInstance();
        LayoutInflater inflater=Context.getLayoutInflater();
        View row=inflater.inflate(R.layout.row,null,true);
        TextView titleTextView=row.findViewById(R.id.titleTextView);
        TextView descriptionTextView=row.findViewById(R.id.descriptionTextView);
        TextView dateTextView=row.findViewById(R.id.dateTextView);
        ImageView newsImage= row.findViewById(R.id.newsImage);
        CheckBox delete=row.findViewById(R.id.delete);
        CheckBox like=row.findViewById(R.id.like);
        numOfLikes=row.findViewById(R.id.numOfLikes);

        news n=newsList.get(position);
        titleTextView.setText(n.getTitle());
        dateTextView.setText(n.getDate());
        descriptionTextView.setText(n.getDescription());
        numOfLikes.setText(String.valueOf(n.getLikes()));
        DatabaseReference newssRef = FirebaseDatabase.getInstance().getReference("news");
        String tempId=String.valueOf(n.getId());
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            delete.setVisibility(View.VISIBLE);
        }else{
            delete.setVisibility(View.INVISIBLE);

        }
        delete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                newssRef.child(tempId).child("isactive").setValue(false);
                Toast.makeText(Context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
            }
        });

        like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(like.isChecked()){
                    like.setButtonDrawable(R.drawable.like_gray);
                    numOfLikes.setText(String.valueOf(Integer.parseInt(numOfLikes.getText().toString())+1));
                }
                else{
                    like.setButtonDrawable(R.drawable.like_empty);
                    numOfLikes.setText(String.valueOf(Integer.parseInt(numOfLikes.getText().toString())-1));
                }
            }
        });


        if(n.getCategory().equals("Breaking News")){
            newsImage.setImageResource(R.mipmap.breakingnews_round);
        }
        else if(n.getCategory().equals("Health")){
            newsImage.setImageResource(R.mipmap.healthnews_round);
        }
        else if(n.getCategory().equals("Sports")){
            newsImage.setImageResource(R.mipmap.sportsnews_round);
        }
        else if(n.getCategory().equals("Technology")){
            newsImage.setImageResource(R.mipmap.technologynews_round);
        }
        else if(n.getCategory().equals("Economics")){

            newsImage.setImageResource(R.mipmap.economicsnews_round);
        }
        else if(n.getCategory().equals("Local")){
            newsImage.setImageResource(R.mipmap.localnews_round);
            //okay
        }
        else if(n.getCategory().equals("Global")){
            newsImage.setImageResource(R.mipmap.globalnews_round);
            //okay
        }
        else if(n.getCategory().equals("Politics")){
            newsImage.setImageResource(R.mipmap.politicsnews_round);
            //okay
        }

        else{
            newsImage.setImageResource(R.drawable.ic_launcher_background);
        }
        return row;
    }

}
