package dev.xalpol12.wheretoeat.data.utility;

import androidx.annotation.NonNull;

public enum PlaceType {
    RESTAURANT("RESTAURANT"),
    BAKERY("BAKERY"),
    CAFE("CAFE"),
    BAR("BAR");

    private final String placeType;

    PlaceType(String placeType) {
        this.placeType = placeType;
    }

    @NonNull
    public String toString() {
        return this.placeType;
    }
}
