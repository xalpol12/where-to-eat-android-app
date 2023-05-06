package dev.xalpol12.wheretoeat.data.utility;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public enum PriceLevel {

    @SerializedName("0")
    FREE("0"),
    @SerializedName("1")
    INEXPENSIVE("1"),
    @SerializedName("2")
    MODERATE("2"),
    @SerializedName("3")
    EXPENSIVE("3"),
    @SerializedName("4")
    VERY_EXPENSIVE("4");

    private final String priceLevel;

    PriceLevel(String priceLevel) {
        this.priceLevel = priceLevel;
    }

    @NonNull
    public String toString() {
        return this.priceLevel;
    }

}
