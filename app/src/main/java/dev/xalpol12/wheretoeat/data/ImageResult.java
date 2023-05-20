package dev.xalpol12.wheretoeat.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageResult {
    private final String imageData;
    private final String photoReference;
}
