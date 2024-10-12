package com.fit2081.fit2081assignment3.provider;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.fit2081.fit2081assignment3.Category;
import com.fit2081.fit2081assignment3.Event;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Event.class, Category.class}, version = 1)
public abstract class EntityDatabase extends RoomDatabase {
    public static final String ENTITY_DATABASE_NAME = "entity_database";

    // reference to DAO, here RoomDatabase parent class will implement this interface
    public abstract EntityDAO entityDAO();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile EntityDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static EntityDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (EntityDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    EntityDatabase.class, ENTITY_DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
