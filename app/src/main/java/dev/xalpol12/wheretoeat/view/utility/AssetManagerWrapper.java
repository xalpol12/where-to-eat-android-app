package dev.xalpol12.wheretoeat.view.utility;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

import lombok.Data;

@Data
public class AssetManagerWrapper {
    private Context context;

    public InputStream openAssetFile(String fileName) throws IOException {
        AssetManager assetManager = context.getAssets();
        return assetManager.open(fileName);
    }
}
