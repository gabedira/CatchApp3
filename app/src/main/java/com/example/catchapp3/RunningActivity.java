package com.example.catchapp3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.example.catchapp3.dbTables.RunVals;
import com.example.catchapp3.dbTables.Runs;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class RunningActivity extends AppCompatActivity {
    private List<RunVals> rvList;
    private RunsDao dao;
    private CatchAppViewModel mCatchAppViewModel;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);

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

        TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setText(Integer.toString(id)); //set text for text view
        TextView textView2 = (TextView) findViewById(R.id.textView3);

        mCatchAppViewModel.getAllRunVals(id).observe(this, rvList -> {
            // Update the cached copy of the words in the adapter.
            playGame(rvList);

            if(rvList != null) {
                textView2.setText(Double.toString(rvList.get(0).updateSpeed)); //set text for text view
            }
            else
                textView2.setText("List is null");
        });
    }

    private List<RunVals> playGame(List<RunVals> rvList){
        ListIterator<RunVals> it = rvList.listIterator();


        return rvList;
    }
}