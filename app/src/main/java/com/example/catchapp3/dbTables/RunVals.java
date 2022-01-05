package com.example.catchapp3.dbTables;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "run_vals",
        primaryKeys = {"run_id", "update_time"})
public class RunVals {

    @NonNull
    @ColumnInfo(name = "run_id")
    public int runId;

    @NonNull
    @ColumnInfo(name = "update_time")
    public double updateTime;

    @NonNull
    @ColumnInfo(name = "update_speed")
    public double updateSpeed;

    public RunVals(@NonNull int runId, double updateTime, double updateSpeed) {
        this.runId = runId;
        this.updateTime = updateTime;
        this.updateSpeed = updateSpeed;
    }

    public RunVals(RunVals rv){
        this.runId = rv.runId;
        this.updateSpeed = rv.updateSpeed;
        this.updateTime = rv.updateSpeed;
    }

}
