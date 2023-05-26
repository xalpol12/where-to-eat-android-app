package dev.xalpol12.wheretoeat.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import dev.xalpol12.wheretoeat.database.dao.PlaceDao;
import dev.xalpol12.wheretoeat.database.entity.PlaceEntity;

@Database(entities = {PlaceEntity.class}, version = 1)
public abstract class PlaceDatabase extends RoomDatabase {
    public abstract PlaceDao placeDao();

}
