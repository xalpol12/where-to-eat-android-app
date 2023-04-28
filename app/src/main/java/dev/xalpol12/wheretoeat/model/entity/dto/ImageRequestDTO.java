package dev.xalpol12.wheretoeat.model.entity.dto;

import lombok.Data;

@Data
public class ImageRequestDTO {
    private String photoReference;
    private int height;
    private int width;
}
