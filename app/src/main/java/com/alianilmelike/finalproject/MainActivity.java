package com.alianilmelike.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends Activity  implements View.OnClickListener {
    private ViewGroup mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ViewGroup) findViewById(R.id.list);
        addDemo("Play Game", PlayGameActivity.class);
        addDemo("Create Game", CreateGameActivity.class);
//        addDemo("Add A Game", AddActivity.class);
//        addDemo("Gallery", PhotoActivity.class);
        addDemo("HELP!", MainActivity.class);

    }

    private void addDemo(String demoName, Class<? extends Activity> activityClass) {
        Button b = new Button(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        b.setLayoutParams(layoutParams);
        b.setText(demoName);
        b.setTag(activityClass);

        b.setOnClickListener(this);
        b.setBackgroundResource(R.drawable.shape);
        mListView.addView(b);

    }

    @Override
    public void onClick(View view) {
        Class activityClass = (Class) view.getTag();
        startActivity(new Intent(this, activityClass));
    }
}
