package dev.xalpol12.wheretoeat.data.utility;


import androidx.annotation.NonNull;

public enum PriceLevel {
    FREE("0"),
    INEXPENSIVE("1"),
    MODERATE("2"),
    EXPENSIVE("3"),
    VERY_EXPENSIVE("4");

    private final String priceLevel;

    PriceLevel(String priceLevel) {
        this.priceLevel = priceLevel;
    }

    public String toString() {
        return this.priceLevel;
    }

}
