package dev.xalpol12.wheretoeat.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import dev.xalpol12.wheretoeat.database.entity.PlaceEntity;

@Dao
public interface PlaceDao {

    @Insert
    void insertAll(PlaceEntity... places);

    @Query("SELECT * FROM place")
    LiveData<List<PlaceEntity>> getAll();

    @Query("DELETE FROM place WHERE place.placeId = :placeId")
    void deleteById(String placeId);
}
