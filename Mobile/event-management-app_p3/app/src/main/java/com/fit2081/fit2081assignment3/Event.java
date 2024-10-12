package com.fit2081.fit2081assignment3;

import java.util.Random;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName="events")
public class Event {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "columnEventId")
    private String eventId;

    @ColumnInfo(name = "columnCategoryId")
    private String categoryId;

    @ColumnInfo(name = "columnEventName")
    private String eventName;

    @ColumnInfo(name = "columnTicketCount")
    private int ticketCount;

    @ColumnInfo(name = "columnIsActive")
    private boolean isActive;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Event(String categoryId, String eventName, int ticketCount, boolean isActive) {
        this.categoryId = categoryId;
        this.eventName = eventName;
        this.ticketCount = ticketCount;
        this.isActive = isActive;

        Random r = new Random();
        char charOne = (char)(r.nextInt(26) + 'A');
        char charTwo = (char)(r.nextInt(26) + 'A');
        int randomInt = r.nextInt(10001);
        this.eventId = String.format("E%1$s%2$s-%3$05d",charOne,charTwo,randomInt);
    }
}
