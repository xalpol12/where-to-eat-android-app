package dev.xalpol12.wheretoeat.data.utility;

public enum PlaceType {
    RESTAURANT("restaurant"),
    BAKERY("bakery"),
    CAFE("cafe"),
    BAR("bar");

    private final String placeType;

    PlaceType(String placeType) {
        this.placeType = placeType;
    }

    public String toString() {
        return this.placeType;
    }
}
