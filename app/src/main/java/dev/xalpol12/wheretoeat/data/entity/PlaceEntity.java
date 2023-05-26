package dev.xalpol12.wheretoeat.data.entity;

import android.media.Image;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import dev.xalpol12.wheretoeat.data.ImageResult;
import dev.xalpol12.wheretoeat.data.Place;
import lombok.Data;

@Data
@Entity(tableName = "place")
public class PlaceEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @Embedded
    private Place place;

    @Embedded
    private ImageResult image;

    public PlaceEntity(Place place, ImageResult image) {
        this.place = place;
        this.image = image;
    }
}


