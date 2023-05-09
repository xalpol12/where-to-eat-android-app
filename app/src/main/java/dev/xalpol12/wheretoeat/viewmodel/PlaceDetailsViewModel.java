package dev.xalpol12.wheretoeat.viewmodel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dev.xalpol12.wheretoeat.data.Place;
import lombok.NonNull;

public class PlaceDetailsViewModel extends ViewModel {
    private final Place place;
    private final Bitmap bitmap;

    public PlaceDetailsViewModel(Place place, Bitmap bitmap) {
        this.place = place;
        this.bitmap = bitmap;
    }

    //TODO: is there a better way?
    public static final class Factory implements ViewModelProvider.Factory {
        private final Place place;
        private final Bitmap bitmap;

        public Factory(Place place, byte[] photo) {
            this.place = place;
            bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new PlaceDetailsViewModel(place, bitmap);
        }
    }

    public String getPlaceName() {
        return place.getName();
    }

    public String getPlaceVicinity() {
        return place.getVicinity();
    }

    public float getPlaceRating() {
        return place.getRating();
    }

    public int getPlaceRatingsTotal() {
        return place.getUserRatingsTotal();
    }

    public boolean isPlaceOpenNow() {
        return place.isOpenNow();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
