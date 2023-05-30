package dev.xalpol12.wheretoeat.database;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import dev.xalpol12.wheretoeat.database.dao.PlaceDao;
import dev.xalpol12.wheretoeat.database.entity.PlaceEntity;

public class PlaceRepository {
    private final PlaceDao placeDao;
    private final LiveData<List<PlaceEntity>> allPlaceEntities;

    @Inject
    public PlaceRepository(PlaceDao placeDao) {
        this.placeDao = placeDao;
        allPlaceEntities = placeDao.getAll();
    }

    //Database operations:
    public void insertAllPlaces(PlaceEntity... places) {
        PlaceDatabase.dbWriteExecutor.execute(() -> placeDao.insertAll(places));
    }

    public void deletePlaceById(String id) {
        PlaceDatabase.dbWriteExecutor.execute(() -> placeDao.deleteById(id));
    }

    //Repository access:
    public LiveData<List<PlaceEntity>> getAllPlaceEntities() {
        return allPlaceEntities;
    }

    public boolean isInDatabase(String placeId) {
        if (dbIsNotNullAndNotZero()) {
            return findPlaceWithGivenId(placeId);
        }
        return false;
    }

    private boolean dbIsNotNullAndNotZero() {
        return (allPlaceEntities.getValue() != null &&
                allPlaceEntities.getValue().size() != 0);
    }

    private boolean findPlaceWithGivenId(String placeId) {
        //noinspection ConstantConditions
        for (PlaceEntity entry : allPlaceEntities.getValue()) {
            if (entry.getPlace().getPlaceId().equals(placeId)) {
                return true;
            }
        }
        return false;
    }
}

