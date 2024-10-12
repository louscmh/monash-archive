package com.fit2081.fit2081assignment3.provider;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.fit2081.fit2081assignment3.Category;
import com.fit2081.fit2081assignment3.Event;

import java.util.List;
public class EntityRepository {
    // private class variable to hold reference to DAO
    private EntityDAO entityDAO;
    // private class variable to temporary hold all the items retrieved and pass outside of this class
    private LiveData<List<Event>> allEventsLiveData;
    private LiveData<List<Category>> allCategoriesLiveData;

    // constructor to initialise the repository class
    EntityRepository(Application application) {
        // get reference/instance of the database
        EntityDatabase db = EntityDatabase.getDatabase(application);

        // get reference to DAO, to perform CRUD operations
        entityDAO = db.entityDAO();

        // once the class is initialised get all the items in the form of LiveData
        allEventsLiveData = entityDAO.getAllEvents();
        allCategoriesLiveData = entityDAO.getAllCategories();
    }

    LiveData<List<Event>> getAllEvents() {
        return allEventsLiveData;
    }

    LiveData<List<Category>> getAllCategories() {
        return allCategoriesLiveData;
    }

    void deleteAllEvents() {
        EntityDatabase.databaseWriteExecutor.execute(() -> entityDAO.deleteAllEvents());
    }

    void deleteAllCategories() {
        EntityDatabase.databaseWriteExecutor.execute(() -> entityDAO.deleteAllCategories());
    }

    void insert(Event event) {
        EntityDatabase.databaseWriteExecutor.execute(() -> entityDAO.addEvent(event));
    }

    void insert(Category category) {
        EntityDatabase.databaseWriteExecutor.execute(() -> entityDAO.addCategory(category));
    }

    void deleteEvent(String eventId) {
        EntityDatabase.databaseWriteExecutor.execute(() -> entityDAO.deleteEvent(eventId));
    }

    void incrementEventCount(String categoryId) {
        EntityDatabase.databaseWriteExecutor.execute(() -> entityDAO.incrementEventCount(categoryId));
    }

    List<Category> getCategoryById(String categoryId) {
        return entityDAO.getCategoryById(categoryId);
    }
}
