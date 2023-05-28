package dev.xalpol12.wheretoeat.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dev.xalpol12.wheretoeat.data.ImageResult;
import dev.xalpol12.wheretoeat.data.Place;
import dev.xalpol12.wheretoeat.database.dao.PlaceDao;
import dev.xalpol12.wheretoeat.database.entity.PlaceEntity;

@Database(entities = {PlaceEntity.class}, version = 1)
public abstract class PlaceDatabase extends RoomDatabase {

    public abstract PlaceDao placeDao();

    private static volatile PlaceDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService dbWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

}
