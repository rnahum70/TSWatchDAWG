package com.example.tswatchdawg.db;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM Symptom")
    List<Symptom> getAllUsers();
    //public User getById(int id);

    // TODO implement this (requires migration)
//    @Query("SELECT * FROM Symptom WHERE TicTimeMillis BETWEEN :minTime AND :maxTime")
//    public List<Symptom> loadAllUsersBetweenAges(long minTime, long maxTime);

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    public Long insertUser(User user);

    @Insert
    void insertSymptom(Symptom... symptom);

    @Delete
    void deleteSymptom(Symptom symptom);


}
