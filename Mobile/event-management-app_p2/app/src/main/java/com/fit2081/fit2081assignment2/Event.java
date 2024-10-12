package com.fit2081.fit2081assignment2;

import java.util.Random;

public class Event {
    private String eventId;
    private String categoryId;
    private String eventName;
    private int ticketCount;
    private boolean isActive;

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
