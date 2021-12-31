package com.example.catchapp3;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.catchapp3.dbTables.RunVals;
import com.example.catchapp3.dbTables.Runs;
import com.example.catchapp3.dbTables.User;

import java.util.List;

public class CatchAppViewModel extends AndroidViewModel {

    private CatchAppRepository mRepository;

    private final LiveData<List<RunVals>> mAllRunVals;
    private LiveData<List<Runs>> mAllRuns;

    public CatchAppViewModel(Application application) {
        super(application);
        mRepository = new CatchAppRepository(application);
        mAllRuns = mRepository.getAllRuns();

        mAllRunVals = null;
    }

    LiveData<List<Runs>> getAllRuns() { return mAllRuns; }
    LiveData<List<RunVals>> getAllRunVals(int id) { return mAllRunVals; }

    public void insert(User user) { mRepository.insert(user); }
    public void insert(Runs run) { mRepository.insert(run); }
    public void insert(RunVals runVals) { mRepository.insert(runVals); }
}