package com.example.catchapp3;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.catchapp3.dbTables.RunVals;
import com.example.catchapp3.dbTables.Runs;
import com.example.catchapp3.dbTables.User;

import java.util.List;

@Dao
public interface RunsDao {

    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Runs run);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(RunVals runVals);

    @Query("DELETE FROM runs")
    void deleteAllRuns();

    @Query("DELETE FROM users")
    void deleteAllUsers();

    @Query("DELETE FROM run_vals")
    void deleteAllRunVals();

    @Query("SELECT * FROM runs")
    LiveData<List<Runs>> getAllRuns();

    @Query("SELECT * FROM run_vals WHERE run_id = :id ORDER BY update_time ASC")
    LiveData<List<RunVals>> getRunVals(int id);
}