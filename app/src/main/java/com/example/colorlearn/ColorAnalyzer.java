package com.example.colorlearn;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.media.Image;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ColorAnalyzer implements ImageAnalysis.Analyzer {
    private long lastTimeStamp = 0;
    private Color averageColor = Color.valueOf(Color.BLACK);

    @Override
    public void analyze(@NonNull ImageProxy image) {
        long currentTimeStamp = System.currentTimeMillis();
        long intervalInMilliSeconds = TimeUnit.MILLISECONDS.toMillis(500);
        long deltaTime = currentTimeStamp - lastTimeStamp;
        if (deltaTime >= intervalInMilliSeconds) {
            @SuppressLint("UnsafeOptInUsageError") Bitmap bm = imageToBitmap(Objects.requireNonNull(image.getImage()));
            averageColor = getAverageRGBCircle(bm, bm.getWidth() / 2, bm.getHeight() / 2, 3);
            lastTimeStamp = currentTimeStamp;
        }
    }

    private Color getAverageRGBCircle (Bitmap imageBitmap, int x, int y, int radius) {
        float r = 0, g = 0, b = 0;
        float brightness = 1.1f, contrast = 32;
        float factor = (float)Math.pow((100 + contrast) / 100, 2);
        int num = 0;

        for (int i = x - radius; i < x + radius; i++) {
            for (int j = y - radius; j < y + radius; j++) {
                if (i < 0 || i >= imageBitmap.getWidth() || j < 0 || j >= imageBitmap.getHeight()) continue;
                double distance = Math.sqrt((j - y) * (j - y) + (i - x) * (i - x));
                if (distance > r) continue;

                int p = imageBitmap.getPixel(i, j);

                int pixelRed = Color.red(p);
                int pixelGreen = Color.green(p);
                int pixelBlue = Color.blue(p);

                //adjust brightness of pixel by 'brightness'
                pixelRed = truncateColor((int)(pixelRed * brightness));
                pixelGreen = truncateColor((int)(pixelGreen * brightness));
                pixelBlue = truncateColor((int)(pixelBlue * brightness));

                //adjust contrast of pixel by 'contrast'
                pixelRed = truncateColor((int)(((((pixelRed / 255.0) - 0.5) * factor) + 0.5) * 255.0));
                pixelGreen = truncateColor((int)(((((pixelGreen / 255.0) - 0.5) * factor) + 0.5) * 255.0));
                pixelBlue = truncateColor((int)(((((pixelBlue / 255.0) - 0.5) * factor) + 0.5) * 255.0));

                r += pixelRed * pixelRed;
                g += pixelGreen * pixelGreen;
                b += pixelBlue * pixelBlue;
                num++;
            }
        }

        float avgRed = (float)Math.sqrt(r / num);
        float avgGreen = (float)Math.sqrt(g / num);
        float avgBlue = (float)Math.sqrt(b / num);

        return Color.valueOf(avgRed, avgGreen, avgBlue);
    }

    private int truncateColor(int color)
    {
        if (color < 0) color = 0;
        if (color > 255) color = 255;
        return color;
    }

    private Bitmap imageToBitmap(Image image) {
        Image.Plane[] planes = image.getPlanes();
        ByteBuffer yBuffer = planes[0].getBuffer();
        ByteBuffer uBuffer = planes[1].getBuffer();
        ByteBuffer vBuffer = planes[2].getBuffer();

        int ySize = yBuffer.remaining();
        int uSize = uBuffer.remaining();
        int vSize = vBuffer.remaining();

        byte[] nv21 = new byte[ySize + uSize + vSize];
        //U and V are swapped
        yBuffer.get(nv21, 0, ySize);
        vBuffer.get(nv21, ySize, vSize);
        uBuffer.get(nv21, ySize + vSize, uSize);

        YuvImage yuvImage = new YuvImage(nv21, ImageFormat.NV21, image.getWidth(), image.getHeight(), null);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(new Rect(0, 0, yuvImage.getWidth(), yuvImage.getHeight()), 75, out);

        byte[] imageBytes = out.toByteArray();
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }

    public Color getAverageColor() {
        return averageColor;
    }
}
