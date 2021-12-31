package com.example.catchapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class RunningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);

        Bundle extras = getIntent().getExtras();
        int id = 0;
        if (extras != null) {
            id = extras.getInt("id");
            //The key argument here must match that used in the other activity
        }

        TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setText(Integer.toString(id)); //set text for text view
    }
}