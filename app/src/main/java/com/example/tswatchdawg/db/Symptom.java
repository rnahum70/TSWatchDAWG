package com.example.tswatchdawg.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Symptom {

    @PrimaryKey(autoGenerate = true)
    public int symptomId;

    @ColumnInfo (name = "Tic Type")
    public String ticType;

//    @ColumnInfo (name = "Tic Intensity")
//    public int ticIntensity;



}
