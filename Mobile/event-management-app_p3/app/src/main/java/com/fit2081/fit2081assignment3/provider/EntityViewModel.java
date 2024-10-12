package com.fit2081.fit2081assignment3.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fit2081.fit2081assignment3.Category;
import com.fit2081.fit2081assignment3.Event;

import java.util.List;


/**
 * ViewModel class is used for pre-processing the data,
 * before passing it to the controllers (Activity or Fragments). ViewModel class should not hold
 * direct reference to database. ViewModel class relies on repository class, hence the database is
 * accessed using the Repository class.
 */
public class EntityViewModel extends AndroidViewModel {
    // reference to CardRepository
    private EntityRepository repository;
    // private class variable to temporary hold all the items retrieved and pass outside of this class
    private LiveData<List<Event>> allEventsLiveData;
    private LiveData<List<Category>> allCategoriesLiveData;

    public EntityViewModel(@NonNull Application application) {
        super(application);

        // get reference to the repository class
        repository = new EntityRepository(application);

        // get all items by calling method defined in repository class
        allEventsLiveData = repository.getAllEvents();
        allCategoriesLiveData = repository.getAllCategories();
    }

    public LiveData<List<Event>> getAllEvents() {
        return allEventsLiveData;
    }

    public LiveData<List<Category>> getAllCategories() {
        return allCategoriesLiveData;
    }

    public void deleteAllEvents() {
        repository.deleteAllEvents();
    }

    public void deleteAllCategories() {
        repository.deleteAllCategories();
    }

    public void insert(Event event) {
        repository.insert(event);
    }

    public void insert(Category category) {
        repository.insert(category);
    }

    public void deleteEvent(String eventId) {
        repository.deleteEvent(eventId);
    }

    public void incrementEventCount(String categoryId) {
        repository.incrementEventCount(categoryId);
    }

    public List<Category> getCategoryById(String categoryId) {
        return repository.getCategoryById(categoryId);
    }


}

