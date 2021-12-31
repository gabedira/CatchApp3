package com.example.catchapp3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catchapp3.adapters.RunListAdapter;
import com.example.catchapp3.dbTables.Runs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity implements RunListAdapter.OnNoteListener{

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private CatchAppViewModel mCatchAppViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final RunListAdapter adapter = new RunListAdapter(new RunListAdapter.RunDiff(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get a new or existing ViewModel from the ViewModelProvider.
        mCatchAppViewModel = new ViewModelProvider(this).get(CatchAppViewModel.class);

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mCatchAppViewModel.getAllRuns().observe(this, words -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(words);
        });

//        TextView tv2 = (TextView) findViewById(R.id.tv2);
//
//
//        mCatchAppViewModel.getAllRunVals(1).observe(this, words -> {
//            // Update the cached copy of the words in the adapter.
//            tv2.setText(Double.toString(words.get(0).updateTime));
//        });



//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(view -> {
//            Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
//            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
//        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
//            Runs run = new Runs(data.getStringExtra(NewWordActivity.EXTRA_REPLY));
//            mCatchAppViewModel.insert(word);
//        } else {
//            Toast.makeText(
//                    getApplicationContext(),
//                    R.string.empty_not_saved,
//                    Toast.LENGTH_LONG).show();
//        }
    }

    @Override
    public void onNoteClick(int id) {
        Intent intent = new Intent(this, RunningActivity.class); // TODO: update this to new activity
        intent.putExtra("id", id);

        startActivity(intent);
    }
}