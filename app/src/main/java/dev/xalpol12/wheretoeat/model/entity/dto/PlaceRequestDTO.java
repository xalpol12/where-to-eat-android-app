package dev.xalpol12.wheretoeat.model.entity.dto;

import dev.xalpol12.wheretoeat.model.entity.utility.Location;
import dev.xalpol12.wheretoeat.model.entity.utility.PlaceType;
import dev.xalpol12.wheretoeat.model.entity.utility.PriceLevel;
import lombok.Data;

@Data
public class PlaceRequestDTO {
    Location location;
    int distance;
    PriceLevel minPrice;
    PriceLevel maxPrice;
    PlaceType type;
}
