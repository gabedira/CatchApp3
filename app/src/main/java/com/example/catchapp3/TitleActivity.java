package com.example.catchapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class TitleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        Button button = (Button) findViewById(R.id.buttonList);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switchActivities();
            }
        });
    }

    private void switchActivities(){
        Intent switchActivityIntent = new Intent(this, ListActivity.class);
        startActivity(switchActivityIntent);
    }
}