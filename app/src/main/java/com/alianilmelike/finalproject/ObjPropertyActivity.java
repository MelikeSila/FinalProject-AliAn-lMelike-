package com.alianilmelike.finalproject;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class ObjPropertyActivity extends AppCompatActivity implements View.OnClickListener {
    private Button nextButton2;
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obj_property);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nextButton2 = (Button) findViewById(R.id.nextButton2);
        nextButton2.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.nextButton2:
                openAddObjActivity();
        }
    }

    private void openAddObjActivity(){
        Intent i = new Intent(getApplicationContext(), AddObjActivity.class);
        startActivity(i);
    }
}
