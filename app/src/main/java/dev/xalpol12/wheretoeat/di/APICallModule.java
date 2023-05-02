package dev.xalpol12.wheretoeat.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import dev.xalpol12.wheretoeat.network.dto.PlaceRequestDTO;

@Module
@InstallIn(SingletonComponent.class)
public class APICallModule {

    @Singleton
    @Provides
    public PlaceRequestDTO getPlaceRequestDTO() {
        return new PlaceRequestDTO();
    }
}
