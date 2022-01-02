package com.example.catchapp3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.catchapp3.dbTables.RunVals;
import com.example.catchapp3.dbTables.Runs;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;

public class RunningActivity extends AppCompatActivity {
    private CatchAppViewModel mCatchAppViewModel;
    private int newID;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_running);
        Format f = new SimpleDateFormat("M/d/yyyy");
        today = f.format(new Date());

        mCatchAppViewModel = new ViewModelProvider(this).get(CatchAppViewModel.class);
        mCatchAppViewModel.insert(new Runs("tempName", today,1, 0, 0));
        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.

        Bundle extras = getIntent().getExtras();
        int id = 0;
        if (extras != null) {
            id = extras.getInt("id");
            //The key argument here must match that used in the other activity
        }

        mCatchAppViewModel.getID().observe(this, retid -> {
            // Update the cached copy of the words in the adapter.
            newID = retid;
        });

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
    private final double LONG_PRESS_INCREASE = 0.2;
    private ListIterator<RunVals> rvIterator;
    private ArrayList<RunVals> rvPlayer;
    private double seconds = 0;
    private final double PERIOD = 250;
    private boolean running = false;
    private RunVals currentRV;
    private double prevPlayerSpeed = -1;
    String today;

    private boolean playerSpeedUpPressed = false, playerSpeedDownPressed = false,
            ghostSpeedUpPressed = false, ghostSpeedDownPressed = false;

    private Button runningToggle, finishButton;
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
            playerDistance += playerSpeed * PERIOD / 1000 / 3600;
            ghostDistance += ghostSpeed * PERIOD / 1000 / 3600;
            if(playerSpeedUpPressed) playerSpeed += LONG_PRESS_INCREASE;
            if(playerSpeedDownPressed && playerSpeed >= LONG_PRESS_INCREASE * 0.9)
                playerSpeed = Math.abs(playerSpeed - LONG_PRESS_INCREASE);
            if(ghostSpeedUpPressed) ghostSpeed += LONG_PRESS_INCREASE;
            if(ghostSpeedDownPressed && ghostSpeed >= LONG_PRESS_INCREASE * 0.9)
                ghostSpeed = Math.abs(ghostSpeed - LONG_PRESS_INCREASE);
            playerLaps = (int) (playerDistance / 0.25);
            ghostLaps = (int) (ghostDistance / 0.25);

            if(seconds >= currentRV.updateTime){
                ghostSpeed = currentRV.updateSpeed;
                if(rvIterator.hasNext())
                    currentRV = rvIterator.next();
                else
                    ghostSpeed = 0;
            }

            if(Math.abs(playerSpeed - prevPlayerSpeed) >= 0.1 ){
                rvPlayer.add(new RunVals(newID, seconds, playerSpeed));
                prevPlayerSpeed = playerSpeed;
            }

            time.setText(String.format("%02d:%02d", (int) seconds/60, (int) seconds%60));
            playerDistanceTV.setText(String.format("%.02f", playerDistance));
            ghostDistanceTV.setText(String.format("%.02f", ghostDistance));
            playerSpeedTV.setText(String.format("%.1f", playerSpeed));
            ghostSpeedTV.setText(String.format("%.1f", ghostSpeed));
            playerLapsTV.setText(Integer.toString(playerLaps));
            ghostLapsTV.setText(Integer.toString(ghostLaps));
        }
    };

    @SuppressLint("ClickableViewAccessibility")
    private void init(){
        time = (TextView) findViewById(R.id.timer);
        playerDistanceTV = (TextView) findViewById(R.id.userDisp).findViewById(R.id.DistanceValue);
        ghostDistanceTV = (TextView) findViewById(R.id.ghostDisp).findViewById(R.id.DistanceValue);
        playerLapsTV = (TextView) findViewById(R.id.userDisp).findViewById(R.id.LapsValue);
        ghostLapsTV = (TextView) findViewById(R.id.ghostDisp).findViewById(R.id.LapsValue);
        playerSpeedTV = (TextView) findViewById(R.id.userDisp).findViewById(R.id.SpeedValue);
        ghostSpeedTV = (TextView) findViewById(R.id.ghostDisp).findViewById(R.id.SpeedValue);
        runningToggle = findViewById(R.id.button);
        finishButton = findViewById(R.id.finish);
        playerSpeedUp = findViewById(R.id.userDisp).findViewById(R.id.imageButtonUp);
        playerSpeedDown = findViewById(R.id.userDisp).findViewById(R.id.imageButtonDown);
        ghostSpeedUp = findViewById(R.id.ghostDisp).findViewById(R.id.imageButtonUp);
        ghostSpeedDown = findViewById(R.id.ghostDisp).findViewById(R.id.imageButtonDown);
        currentRV = rvIterator.next();

        runningToggle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                running = !running;
                if(running){
                    runningToggle.setBackgroundColor(Color.RED);
                    runningToggle.setText("STOP");
                    playerSpeedUp.setVisibility(View.VISIBLE);
                    playerSpeedDown.setVisibility(View.VISIBLE);
                    ghostSpeedUp.setVisibility(View.VISIBLE);
                    ghostSpeedDown.setVisibility(View.VISIBLE);
                    finishButton.setVisibility(View.INVISIBLE);
                    timer = new Timer();
                    TimerTask gameIteration = new TimerTask(){ public void run(){ TimerMethod();}};
                    timer.schedule(gameIteration, 0, (int) PERIOD);
                }else{
                    runningToggle.setBackgroundColor(Color.GREEN);
                    runningToggle.setText("START");
                    playerSpeedUp.setVisibility(View.INVISIBLE);
                    playerSpeedDown.setVisibility(View.INVISIBLE);
                    ghostSpeedUp.setVisibility(View.INVISIBLE);
                    ghostSpeedDown.setVisibility(View.INVISIBLE);
                    finishButton.setVisibility(View.VISIBLE);
                    timer.cancel();
                    timer.purge();
                }
            }});

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(RunVals rv : rvPlayer) {
                    mCatchAppViewModel.insert(rv);
                }
                Thread th = new Thread(){
                    public void run(){mCatchAppViewModel.updateLenAndTime(playerDistance, seconds);}
                };
                th.start();
                switchActivities();
            }
        });
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

        playerSpeedUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    playerSpeed += 0.1;
                    playerSpeedTV.setText(String.format("%.1f", playerSpeed));
                    playerSpeedUpPressed = true; }
                else if(event.getAction() == MotionEvent.ACTION_UP) { playerSpeedUpPressed = false; }
                return true;
            }});
        playerSpeedDown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if(playerSpeed >= 0.09) playerSpeed -= 0.1;
                    playerSpeedTV.setText(String.format("%.1f", playerSpeed));
                    playerSpeedDownPressed = true; }
                else if(event.getAction() == MotionEvent.ACTION_UP) { playerSpeedDownPressed = false; }
                return true;
            }});
        ghostSpeedUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    ghostSpeed += 0.1;
                    ghostSpeedTV.setText(String.format("%.1f", ghostSpeed));
                    ghostSpeedUpPressed = true; }
                else if(event.getAction() == MotionEvent.ACTION_UP) { ghostSpeedUpPressed = false; }
                return true;
            }});
        ghostSpeedDown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if(ghostSpeed >= 0.09) ghostSpeed -= 0.1;
                    ghostSpeedTV.setText(String.format("%.1f", ghostSpeed));
                    ghostSpeedDownPressed = true; }
                else if(event.getAction() == MotionEvent.ACTION_UP) { ghostSpeedDownPressed = false; }
                return true;
            }});
    }

    private List<RunVals> playGame(List<RunVals> rvList){
        rvIterator = rvList.listIterator();
        rvPlayer = new ArrayList<RunVals>();
        timer = new Timer();
        init();
        return rvPlayer;
    }

    private void switchActivities(){
        Intent switchActivityIntent = new Intent(this, StatActivity.class);
//        switchActivityIntent.putExtra("total_distance", playerDistance);
//        switchActivityIntent.putExtra("total_time", seconds);
        startActivity(switchActivityIntent);
    }
}