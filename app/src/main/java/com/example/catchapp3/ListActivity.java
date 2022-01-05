package com.example.catchapp3;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catchapp3.adapters.RunListAdapter;
import com.example.catchapp3.dbTables.RunVals;
import com.example.catchapp3.dbTables.Runs;


public class ListActivity extends AppCompatActivity implements RunListAdapter.OnNoteListener{

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private CatchAppViewModel mCatchAppViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final RunListAdapter adapter = new RunListAdapter(new RunListAdapter.RunDiff(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get a new or existing ViewModel from the ViewModelProvider.
        mCatchAppViewModel = new ViewModelProvider(this).get(CatchAppViewModel.class);
        mCatchAppViewModel.deleteInvalidRuns();




        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mCatchAppViewModel.getAllRuns().observe(this, words -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(words);
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onNoteClick(int id) {
        Intent intent = new Intent(this, RunningActivity.class);
        intent.putExtra("id", id);

        startActivity(intent);
    }
}