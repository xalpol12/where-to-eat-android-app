package dev.xalpol12.wheretoeat.network.dto;

import lombok.Data;

@Data
public class ImageRequestDTO {
    private String photoReference;
    private int height;
    private int width;
}
