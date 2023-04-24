package dev.xalpol12.wheretoeat.model.entity;

import lombok.Data;

@Data
public class ImageResult {
    public final byte[] imageData;
    public final String contentType;

    public ImageResult(byte[] imageData, String contentType) {
        this.imageData = imageData;
        this.contentType = contentType;
    }
}
