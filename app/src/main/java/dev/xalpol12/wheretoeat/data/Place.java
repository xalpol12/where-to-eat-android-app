package dev.xalpol12.wheretoeat.data;

import dev.xalpol12.wheretoeat.data.utility.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Place {
    private String name;
    private String placeId;
    private Location location;
    private String vicinity;
    private float rating;
    private int userRatingsTotal;
    private boolean openNow;
    private String photoReference;
}
