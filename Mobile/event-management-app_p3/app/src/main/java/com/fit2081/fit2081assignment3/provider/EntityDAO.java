package com.fit2081.fit2081assignment3.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.fit2081.fit2081assignment3.Category;
import com.fit2081.fit2081assignment3.Event;

import java.util.List;

@Dao
public interface EntityDAO {

    @Query("select * from events")
    LiveData<List<Event>> getAllEvents();

    @Insert
    void addEvent(Event event);

    @Query("select * from categories")
    LiveData<List<Category>> getAllCategories();

    @Insert
    void addCategory(Category category);

    @Query("delete FROM events")
    void deleteAllEvents();

    @Query("delete FROM categories")
    void deleteAllCategories();

    @Query("delete from events where columnEventId=:eventId")
    void deleteEvent(String eventId);

    @Query("UPDATE categories SET columnEventCount = columnEventCount + 1 WHERE columnCategoryId = :categoryId")
    void incrementEventCount(String categoryId);

    @Query("select * from categories where columnCategoryId=:categoryId")
    List<Category> getCategoryById(String categoryId);
}
