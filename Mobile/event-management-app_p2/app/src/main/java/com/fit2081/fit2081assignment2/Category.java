package com.fit2081.fit2081assignment2;

import java.util.Random;

public class Category {
    private String categoryId;
    private String categoryName;
    private int eventCount;
    private boolean isActive;

    public Category(String categoryName, int eventCount, boolean isActive) {
        this.categoryName = categoryName;
        this.eventCount = eventCount;
        this.isActive = isActive;

        Random r = new Random();
        char charOne = (char)(r.nextInt(26) + 'A');
        char charTwo = (char)(r.nextInt(26) + 'A');
        int randomInt = r.nextInt(1001);
        this.categoryId = String.format("C%1$s%2$s-%3$04d",charOne,charTwo,randomInt);
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
