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
    public static PlaceDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (PlaceDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PlaceDatabase.class, "place.db")
                            .addCallback(insertEntitiesOnCreate)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private final static RoomDatabase.Callback insertEntitiesOnCreate = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            dbWriteExecutor.execute(() -> {
                PlaceDao dao = INSTANCE.placeDao();
                dao.deleteAll();

                PlaceEntity place = new PlaceEntity(new Place(), new ImageResult());
                dao.insert(place);

                place = new PlaceEntity(new Place(), new ImageResult());
                dao.insert(place);
            });
        }
    };
}
