package dev.xalpol12.wheretoeat.view.utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class ImageDecoder {
    public static Bitmap decode(String base64CodedImage) {
        byte[] byteArray = Base64.decode(base64CodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }
}
