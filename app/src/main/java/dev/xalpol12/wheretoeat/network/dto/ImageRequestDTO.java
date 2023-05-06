package dev.xalpol12.wheretoeat.network.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageRequestDTO {
    private String photoReference;
    private int height;
    private int width;
}
