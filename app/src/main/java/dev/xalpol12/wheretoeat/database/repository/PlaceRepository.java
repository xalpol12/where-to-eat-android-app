package dev.xalpol12.wheretoeat.database.repository;

import java.util.List;

import javax.inject.Inject;

import dev.xalpol12.wheretoeat.database.dao.PlaceDao;
import dev.xalpol12.wheretoeat.database.entity.PlaceEntity;

public class PlaceRepository {
    private final PlaceDao placeDao;

    @Inject
    public PlaceRepository(PlaceDao placeDao) {
        this.placeDao = placeDao;
    }

    public void insert(PlaceEntity place) {
        placeDao.insertAll(place);
    }

    public void insertAll(PlaceEntity... places) {
        placeDao.insertAll(places);
    }

    public List<PlaceEntity> getAllPlaces() {
        return placeDao.getAllPlaces();
    }

    public void deleteById(String id) {
        placeDao.deleteById(id);
    }
}
