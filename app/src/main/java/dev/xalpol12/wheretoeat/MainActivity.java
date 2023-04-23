package dev.xalpol12.wheretoeat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    // screen diagonal:     5 inches
    // screen dimensions:   W 2.45 : H 4.36
    // aspect ratio:        16:9 (~294 ppi density)
    // pixel resolution:    720x1080
    // dp resolution:       391x587
    // density bucket:      xhdpi

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
    }
}