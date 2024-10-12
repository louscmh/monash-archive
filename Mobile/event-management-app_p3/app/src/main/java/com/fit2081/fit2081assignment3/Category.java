package com.fit2081.fit2081assignment3;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Random;

@Entity(tableName="categories")
public class Category {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "columnCategoryId")
    private String categoryId;

    @ColumnInfo(name = "columnCategoryName")
    private String categoryName;

    @ColumnInfo(name = "columnEventCount")
    private int eventCount;

    @ColumnInfo(name = "columnIsActive")
    private boolean isActive;

    @ColumnInfo(name = "columnEventLocation")
    private String eventLocation;

    public Category(String categoryName, int eventCount, boolean isActive, String eventLocation) {
        this.categoryName = categoryName;
        this.eventCount = eventCount;
        this.isActive = isActive;
        this.eventLocation = eventLocation;

        Random r = new Random();
        char charOne = (char)(r.nextInt(26) + 'A');
        char charTwo = (char)(r.nextInt(26) + 'A');
        int randomInt = r.nextInt(1001);
        this.categoryId = String.format("C%1$s%2$s-%3$04d",charOne,charTwo,randomInt);
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getEventCount() {
        return eventCount;
    }

    public void setEventCount(int eventCount) {
        this.eventCount = eventCount;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
