package com.example.catchapp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

public class StatActivity extends AppCompatActivity {
    private CatchAppViewModel mCatchAppViewModel;
    private TextView distanceTV, timeTV, lapsTV, maxSpeedTV, avgSpeedTV;
    private Button saveButton, deleteButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        mCatchAppViewModel = new ViewModelProvider(this).get(CatchAppViewModel.class);

        distanceTV = (TextView) findViewById(R.id.DistanceValue);
        timeTV = (TextView) findViewById(R.id.TimeValue);
        lapsTV = (TextView) findViewById(R.id.LapsValue);
        maxSpeedTV = (TextView) findViewById(R.id.MaxSpeedValue);
        avgSpeedTV = (TextView) findViewById(R.id.SpeedValue);

        mCatchAppViewModel.getLatestRun().observe(this, run -> {
            // Update the cached copy of the words in the adapter.
            distanceTV.setText(String.format("%.1f", run.totalLength));
            timeTV.setText(String.format("%.1f", run.totalTime));
            avgSpeedTV.setText(String.format("%.1f", run.totalLength / run.totalTime));
            lapsTV.setText(Integer.toString((int) (run.totalLength % 0.25)));
        });

        mCatchAppViewModel.getLatestMaxSpeed().observe(this, speed -> {
            // Update the cached copy of the words in the adapter.
            maxSpeedTV.setText(String.format("%.02f", speed));
        });
        saveButton = findViewById(R.id.save);
        deleteButton = findViewById(R.id.delete);
        TextInputEditText input = findViewById(R.id.input);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButton.setVisibility(View.GONE);
                deleteButton.setVisibility(View.GONE);
                input.setVisibility(View.VISIBLE);

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButton.setVisibility(View.GONE);
                deleteButton.setVisibility(View.GONE);
                Thread th = new Thread(){ public void run(){mCatchAppViewModel.deleteLatestRunsAndRunVals();}};
                th.start();
                switchActivities();
            }
        });



    }

    private void switchActivities(){
        Intent switchActivityIntent = new Intent(this, TitleActivity.class);
        startActivity(switchActivityIntent);
    }
}