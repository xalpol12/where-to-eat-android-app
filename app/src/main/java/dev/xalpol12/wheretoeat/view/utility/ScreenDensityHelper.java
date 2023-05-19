package dev.xalpol12.wheretoeat.view.utility;


public class ScreenDensityHelper {
    //xxxhdpi 640 dpi is my 1x value, scale accordingly
    private final int originalWidth = 1000;
    private final int originalHeight = 1500;
    private float multiplier;

    public ScreenDensityHelper(int screenDensity) {
        setMultiplier(screenDensity);
    }

    public ScreenDimensions getScreenDimensions() {
        int width = (int) (originalWidth * multiplier);
        int height = (int) (originalHeight * multiplier);
        return new ScreenDimensions(width, height);
    }

    private void setMultiplier(int screenDensity) {
        switch (screenDensity) {
            default:
                multiplier = 0.7f;
                break;
            case 120:
                multiplier = 0.1875f;
                break;
            case 160:
                multiplier = 0.25f;
                break;
            case 240:
                multiplier = 0.375f;
                break;
            case 320:
                multiplier = 0.5f;
                break;
            case 480:
                multiplier = 0.75f;
                break;
            case 640:
                multiplier = 1f;
                break;
        }
    }
}
