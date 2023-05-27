package dev.xalpol12.wheretoeat.di;


import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import dev.xalpol12.wheretoeat.database.PlaceDatabase;
import dev.xalpol12.wheretoeat.database.dao.PlaceDao;
import dev.xalpol12.wheretoeat.database.PlaceRepository;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    @Singleton
    public Context getApplicationContext(Application application) {
        return application.getApplicationContext();
    }

    @Singleton
    @Provides
    public PlaceDatabase getPlaceDatabase(Context context) {
        return Room.databaseBuilder(context, PlaceDatabase.class, "place.db").build();
    }

    @Singleton
    @Provides
    public PlaceDao getPlaceDao(PlaceDatabase db) {
        return db.placeDao();
    }

    @Singleton
    @Provides
    public PlaceRepository getPlaceRepository(PlaceDao placeDao) {
        return new PlaceRepository(placeDao);
    }
}
