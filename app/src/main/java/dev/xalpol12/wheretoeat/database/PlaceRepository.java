package dev.xalpol12.wheretoeat.database;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dev.xalpol12.wheretoeat.database.dao.PlaceDao;
import dev.xalpol12.wheretoeat.database.entity.PlaceEntity;

public class PlaceRepository {
    private final PlaceDao placeDao;

    @Inject
    public PlaceRepository(PlaceDao placeDao) {
        this.placeDao = placeDao;
    }

    public void insertPlace(PlaceEntity place) {
        new Thread(() -> {
            placeDao.insert(place);
        }).start();
    }

    public void insertAllPlaces(PlaceEntity... places) {
        new Thread(() -> {
            placeDao.insertAll(places);
        }).start();
    }

    public LiveData<List<PlaceEntity>> getAllPlaces() {
        return placeDao.getAll();
    }

    public void deletePlaceById(String id) {
        placeDao.deleteById(id);
    }

    public boolean isInDatabase(String placeId) {
        return placeDao.existsById(placeId);
    }
}
