package dev.xalpol12.wheretoeat.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dev.xalpol12.wheretoeat.database.dao.PlaceDao;
import dev.xalpol12.wheretoeat.database.entity.PlaceEntity;

@Database(entities = {PlaceEntity.class}, version = 1)
public abstract class PlaceDatabase extends RoomDatabase {

    public abstract PlaceDao placeDao();
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService dbWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

}
