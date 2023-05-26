//package dev.xalpol12.wheretoeat.di;
//
//import android.content.Context;
//
//import androidx.room.Room;
//
//import javax.inject.Singleton;
//
//import dagger.Module;
//import dagger.Provides;
//import dagger.hilt.InstallIn;
//import dagger.hilt.android.qualifiers.ApplicationContext;
//import dagger.hilt.components.SingletonComponent;
//import dev.xalpol12.wheretoeat.data.dao.PlaceDao;
//import dev.xalpol12.wheretoeat.data.database.PlaceDatabase;
//
//@Module
//@InstallIn(SingletonComponent.class)
//public class DatabaseModule {
//
//    @Singleton
//    @Provides
//    public PlaceDatabase getPlaceDatabase(@ApplicationContext Context context) {
//        return Room.databaseBuilder(context, PlaceDatabase.class, "place-db").build();
//    }
//
//    @Singleton
//    @Provides
//    public PlaceDao getPlaceDao(PlaceDatabase db) {
//        return db.placeDao();
//    }
//}
