package dev.xalpol12.wheretoeat.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import dev.xalpol12.wheretoeat.data.entity.PlaceEntity;

@Dao
public interface PlaceDao {
    @Insert
    void insert(PlaceEntity place);

    @Insert
    void insertAll(PlaceEntity... places);

    @Query("SELECT * FROM place")
    List<PlaceEntity> getAllPlaces();

    @Query("DELETE FROM place WHERE place.id = :placeId")
    void deleteById(String placeId);
}
