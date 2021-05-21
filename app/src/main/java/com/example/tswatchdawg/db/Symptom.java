package com.example.tswatchdawg.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Symptom {

    @PrimaryKey(autoGenerate = true)
    public int symptomId;

    @ColumnInfo (name = "Tic Time")
    public String ticTime;

    @ColumnInfo (name = "Tic Time Millis")
    public String ticTimeMillis;

    @ColumnInfo (name = "Tic Type")
    public String ticType;

    @ColumnInfo (name = "Tic Intensity")
    public String ticIntensity;

    @ColumnInfo (name = "Tic Feeling")
    public String ticFeeling;

    @ColumnInfo (name = "Tic Notes")
    public String ticNotes;


}
