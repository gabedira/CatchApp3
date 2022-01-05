package com.example.catchapp3;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Query;

import com.example.catchapp3.dbTables.RunVals;
import com.example.catchapp3.dbTables.Runs;
import com.example.catchapp3.dbTables.User;

import java.util.List;

class CatchAppRepository {

    private com.example.catchapp3.RunsDao mRunsDao;
    private LiveData<List<RunVals>> mAllRunVals;
    private LiveData<List<Runs>> mAllRuns;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    CatchAppRepository(Application application) {
        CatchAppRoomDatabase db = CatchAppRoomDatabase.getDatabase(application);
        mRunsDao = db.RunsDao();
        mAllRuns = mRunsDao.getAllRuns();

    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Runs>> getAllRuns() {
        return mAllRuns;
    }

    LiveData<List<RunVals>> getAllRunVals(int id){
        mAllRunVals = mRunsDao.getRunVals(id);
        return mAllRunVals;
    }

    LiveData<Integer> getID(){
        return mRunsDao.getID();
    }

    void updateLenAndTime(double totLen, double totTime){
        mRunsDao.updateLenAndTime(totLen, totTime);
    }

    void updateName(String name){
        mRunsDao.updateName(name);
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(User user) {
        CatchAppRoomDatabase.databaseWriteExecutor.execute(() -> {
            mRunsDao.insert(user);
        });
    }

    void insert(Runs run) {
        CatchAppRoomDatabase.databaseWriteExecutor.execute(() -> {
            mRunsDao.insert(run);
        });
    }

    void insert(RunVals runVals) {
        CatchAppRoomDatabase.databaseWriteExecutor.execute(() -> {
            mRunsDao.insert(runVals);
        });
    }

    public void deleteAllRuns() {
        CatchAppRoomDatabase.databaseWriteExecutor.execute(() -> {
            mRunsDao.deleteAllRuns();
        });
    }

    public void deleteAllRunVals() {
        CatchAppRoomDatabase.databaseWriteExecutor.execute(() -> {
            mRunsDao.deleteAllRunVals();
        });
    }

    public LiveData<Runs> getLatestRun(){
        return mRunsDao.getLatestRun();
    }

    public LiveData<Double> getLatestMaxSpeed(){
        return mRunsDao.getLatestMaxSpeed();
    }

    public void deleteLatestRunsAndRunVals(){
        CatchAppRoomDatabase.databaseWriteExecutor.execute(() -> {
            mRunsDao.deleteLatestRunVals();
            mRunsDao.deleteLatestRun();
        });
    }

    public void deleteInvalidRuns() {
        CatchAppRoomDatabase.databaseWriteExecutor.execute(() -> {
            mRunsDao.deleteInvalidRunVals();
            mRunsDao.deleteInvalidRuns();
        });
    }
}