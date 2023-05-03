package dev.xalpol12.wheretoeat.network.dto;

import dev.xalpol12.wheretoeat.data.utility.Location;
import dev.xalpol12.wheretoeat.data.utility.PlaceType;
import dev.xalpol12.wheretoeat.data.utility.PriceLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceRequestDTO {
    private Location location;
    private int distance;
    private PriceLevel minPrice;
    private PriceLevel maxPrice;
    private PlaceType placeType;
}
