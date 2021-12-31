package com.example.catchapp3;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.catchapp3.dbTables.RunVals;
import com.example.catchapp3.dbTables.Runs;
import com.example.catchapp3.dbTables.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Runs.class, RunVals.class}, version = 1, exportSchema = false)
public abstract class CatchAppRoomDatabase extends RoomDatabase {

    public abstract RunsDao RunsDao();

    private static volatile CatchAppRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static CatchAppRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CatchAppRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CatchAppRoomDatabase.class, "catchapp_database")
                            .addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                RunsDao dao = INSTANCE.RunsDao();

                dao.deleteAllRunVals();
                dao.deleteAllRuns();
                dao.deleteAllUsers();
                Log.v("testing", "populating the database");
                User user1 = new User(1, "Jeff");
                Runs run1 = new Runs(1, "testRun", "12/29/2021", 1, 6.8, 3000);

                RunVals rv1 = new RunVals(1, 0.3, 5.4);
                RunVals rv2 = new RunVals(1, 0.5, 5.7);

                dao.insert(user1);
                dao.insert(run1);
                dao.insert(rv1);
                dao.insert(rv2);
            });
        }
    };

}