package dev.xalpol12.wheretoeat.data.utility;

import java.util.Locale;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location {
    private double lat;
    private double lng;
}
