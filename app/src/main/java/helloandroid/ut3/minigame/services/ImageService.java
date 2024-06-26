package helloandroid.ut3.minigame.services;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.Optional;

public class ImageService {
    public static final int WHITE_PIXEL_COLOR = Color.WHITE;
    public static final int BLACK_PIXEL_COLOR = Color.BLACK;
    private static volatile ImageService instance;
    private Bitmap photo;

    private Bitmap thresholdedBitmap;

    private ImageService() {
    }

    public static ImageService getInstance() {
        if (instance == null) {
            synchronized (ImageService.class) {
                if (instance == null) {
                    instance = new ImageService();
                }
            }
        }
        return instance;
    }

    public static Bitmap flattenedVersion(Bitmap bitmap, int threshold) {
        Bitmap greyVersion = createGreyVersion(bitmap);
        Bitmap flattenedVersion = Bitmap.createBitmap(greyVersion.getWidth(), greyVersion.getHeight(), Bitmap.Config.ARGB_8888);

        for (int y = 0; y < greyVersion.getHeight(); y++) {
            for (int x = 0; x < greyVersion.getWidth(); x++) {
                int pixel = greyVersion.getPixel(x, y);
                int greyValue = Color.red(pixel);

                int newPixelColor = (greyValue > threshold) ? WHITE_PIXEL_COLOR : BLACK_PIXEL_COLOR;

                flattenedVersion.setPixel(x, y, newPixelColor);
            }
        }
        return flattenedVersion;
    }

    public static Bitmap createGreyVersion(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap grayBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = bitmap.getPixel(x, y);

                // Calculate grayscale value
                int red = Color.red(pixel);
                int green = Color.green(pixel);
                int blue = Color.blue(pixel);
                int gray = (int) (red * 0.3 + green * 0.59 + blue * 0.11);

                // Set the pixel to gray
                int grayPixel = Color.rgb(gray, gray, gray);
                grayBitmap.setPixel(x, y, grayPixel);
            }
        }
        return grayBitmap;
    }

    public Optional<Bitmap> createGreyVersion() {
        if (photo != null) {
            int width = photo.getWidth();
            int height = photo.getHeight();

            Bitmap grayBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = photo.getPixel(x, y);

                    // Calculate grayscale value
                    int red = Color.red(pixel);
                    int green = Color.green(pixel);
                    int blue = Color.blue(pixel);
                    int gray = (int) (red * 0.3 + green * 0.59 + blue * 0.11);

                    // Set the pixel to gray
                    int grayPixel = Color.rgb(gray, gray, gray);
                    grayBitmap.setPixel(x, y, grayPixel);
                }
            }
            return Optional.of(grayBitmap);
        } else {
            return Optional.ofNullable(null);
        }
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public Bitmap getThresholdedBitmap() {
        return thresholdedBitmap;
    }

    public void setThresholdedBitmap(Bitmap thresholdedBitmap) {
        this.thresholdedBitmap = thresholdedBitmap;
    }
}
