package com.example.catchapp3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.catchapp3.dbTables.RunVals;
import com.example.catchapp3.dbTables.Runs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class RunningActivity extends AppCompatActivity {
    private CatchAppViewModel mCatchAppViewModel;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_running);
        init();
        mCatchAppViewModel = new ViewModelProvider(this).get(CatchAppViewModel.class);

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.

        Bundle extras = getIntent().getExtras();
        int id = 0;
        if (extras != null) {
            id = extras.getInt("id");
            //The key argument here must match that used in the other activity
        }

//        TextView textView = (TextView) findViewById(R.id.);
//        textView.setText(Integer.toString(id)); //set text for text view
//        TextView textView2 = (TextView) findViewById(R.id.textView3);

        mCatchAppViewModel.getAllRunVals(id).observe(this, rvList -> {
            // Update the cached copy of the words in the adapter.
            playGame(rvList);
        });
    }

    private Timer timer;
    private double playerDistance = 0;
    private double ghostDistance = 0;
    private int playerLaps = 0;
    private int ghostLaps = 0;
    private double playerSpeed = 0;
    private double ghostSpeed = 0;
    private ListIterator<RunVals> rvIterator;
    private ArrayList<RunVals> rvPlayer;
    private double seconds = 0;
    final double PERIOD = 500;
    private boolean running = false;

    private Button runningToggle;
    private ImageButton playerSpeedUp, playerSpeedDown, ghostSpeedUp, ghostSpeedDown;
    private TextView playerDistanceTV, ghostDistanceTV, playerLapsTV, ghostLapsTV, playerSpeedTV,
            ghostSpeedTV, time;


    private void TimerMethod(){
        this.runOnUiThread(TimerTick);
    }

    private Runnable TimerTick = new Runnable() {
        @Override
        public void run() {
            seconds += PERIOD / 1000;
            time.setText(String.format("%02d:%02d", (int) seconds/60, (int) seconds%60));
        }
    };

    private void init(){
        time = (TextView) findViewById(R.id.timer);
        playerDistanceTV = (TextView) findViewById(R.id.userDisp).findViewById(R.id.DistanceValue);
        ghostDistanceTV = (TextView) findViewById(R.id.ghostDisp).findViewById(R.id.DistanceValue);
        playerLapsTV = (TextView) findViewById(R.id.userDisp).findViewById(R.id.LapsValue);
        ghostLapsTV = (TextView) findViewById(R.id.ghostDisp).findViewById(R.id.LapsValue);
        playerSpeedTV = (TextView) findViewById(R.id.userDisp).findViewById(R.id.SpeedValue);
        ghostSpeedTV = (TextView) findViewById(R.id.ghostDisp).findViewById(R.id.SpeedValue);
        runningToggle = findViewById(R.id.button);
        playerSpeedUp = findViewById(R.id.userDisp).findViewById(R.id.imageButtonUp);
        playerSpeedDown = findViewById(R.id.userDisp).findViewById(R.id.imageButtonDown);
        ghostSpeedUp = findViewById(R.id.ghostDisp).findViewById(R.id.imageButtonUp);
        ghostSpeedDown = findViewById(R.id.ghostDisp).findViewById(R.id.imageButtonDown);

        runningToggle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!running){
                    runningToggle.setBackgroundColor(Color.RED);
                    runningToggle.setText("STOP");
                    playerSpeedUp.setVisibility(View.VISIBLE);
                    playerSpeedDown.setVisibility(View.VISIBLE);
                    ghostSpeedUp.setVisibility(View.VISIBLE);
                    ghostSpeedDown.setVisibility(View.VISIBLE);
                    timer.schedule(gameIteration, 0, 500);

                }else{
                    runningToggle.setBackgroundColor(Color.GREEN);
                    runningToggle.setText("START");
                    playerSpeedUp.setVisibility(View.INVISIBLE);
                    playerSpeedDown.setVisibility(View.INVISIBLE);
                    ghostSpeedUp.setVisibility(View.INVISIBLE);
                    ghostSpeedDown.setVisibility(View.INVISIBLE);
                    timer.cancel();
                }
                running = !running;

            }});

        playerSpeedUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playerSpeed += 0.1;
                playerSpeedTV.setText(String.format("%.1f", playerSpeed));
            }});
        playerSpeedDown.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(playerSpeed >= 0.09) playerSpeed -= 0.1;
                playerSpeedTV.setText(String.format("%.1f", playerSpeed));
            }});
        ghostSpeedUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ghostSpeed += 0.1;
                ghostSpeedTV.setText(String.format("%.1f", ghostSpeed));
            }});
        ghostSpeedDown.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(ghostSpeed >= 0.09) ghostSpeed -= 0.1;
                ghostSpeedTV.setText(String.format("%.1f", ghostSpeed));
            }});

    }

    TimerTask gameIteration = new TimerTask(){
        public void run() {
            TimerMethod();
        }
    };

    private List<RunVals> playGame(List<RunVals> rvList){
        rvIterator = rvList.listIterator();
        rvPlayer = new ArrayList<RunVals>();

        timer = new Timer();


        return rvPlayer;
    }
}