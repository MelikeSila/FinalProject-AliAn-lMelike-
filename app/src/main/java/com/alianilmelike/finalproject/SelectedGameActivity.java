package com.alianilmelike.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Module.PostGame;
import Module.TargetModel;

public class SelectedGameActivity extends AppCompatActivity {

    private TextView mTextMessage;
    public final static String KEY_GAME_ID = "KeyGameId";
    private double latitude, longitude;
    public String gameId;
    public String url;
    private List<String> targetIds = new ArrayList<>(0);
    private ViewPager targetViewPager;
    private List <TargetModel> targetModelList = new ArrayList<>(0);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_game);
        gameId = getIntent().getStringExtra(KEY_GAME_ID);
        targetViewPager = (ViewPager) findViewById(R.id.pager);
        readData();
    }

    public void readData(){
        DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference("game/"+gameId+"/Targets/");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    targetIds.add(ds.getKey());
                    //Toast.makeText(getApplicationContext(),ds.getKey(),Toast.LENGTH_SHORT).show();
                    //createDataListener(ds.getKey());
                    TargetModel target = ds.getValue(TargetModel.class);
                    if (target != null){
                        targetModelList.add(target);
                    }
                }
                TargetAdapter targetAdapter = new TargetAdapter(getSupportFragmentManager(),targetModelList);
                targetViewPager.setAdapter(targetAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("hgdslkavnş", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mPostReference.addValueEventListener(postListener);
    }
}
