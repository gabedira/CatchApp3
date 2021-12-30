package com.example.catchapp3.dbTables;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "runs", foreignKeys = {@ForeignKey(entity=User.class, parentColumns = "user_id",
        childColumns = "user_id", onDelete = ForeignKey.CASCADE)})
public class Runs {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "run_id")
    public int runId;

    @NonNull
    @ColumnInfo(name = "run_name")
    public String runName;

    @NonNull
    @ColumnInfo(name = "created")
    public String created;

    @NonNull
    @ColumnInfo(name = "user_id")
    public int userId;

    @NonNull
    @ColumnInfo(name = "total_length")
    public double totalLength;

    @NonNull
    @ColumnInfo(name = "total_time")
    public double totalTime;

    public Runs(@NonNull int runId, String runName, String created, int userId, double totalLength, double totalTime) {
        this.runId = runId;
        this.runName = runName;
        this.created = created;
        this.userId = userId;
        this.totalLength = totalLength;
        this.totalLength = totalTime;
    }
}

