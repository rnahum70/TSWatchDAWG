package com.example.tswatchdawg.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// bump version number if your schema changes
@Database(
        entities={Symptom.class},
        version = 1
)

public abstract class TicDatabase extends RoomDatabase {
    // Declare your data access objects as abstract
    public abstract UserDao userDao();


    private static TicDatabase INSTANCE;

    // Database name to be used
    public static final String NAME = "TicDatabase";

    public static TicDatabase getDbInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TicDatabase.class, "TicDatabase")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}


