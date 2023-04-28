package dev.xalpol12.wheretoeat.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Place {
    private String name;
    private String placeId;
    private String vicinity;
    private float rating;
    private int userRatingsTotal;
    private boolean openNow;
    private String photoReference;
}
