package com.example.colorlearn;

import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Java Code to get a color name from rgb/hex value/awt color
 * */
public class ColorUtils {
    private final Map<String, ArrayList<ColorName>> colorLists = new HashMap<>();

    ColorUtils() {
        addRU();
        addEN();
    }

    private void addRU() {
        ArrayList<ColorName> colorList = new ArrayList<>();
        colorList.add(new ColorName("Белый", 0xFF, 0xFF, 0xFF));
        colorList.add(new ColorName("Белый", 0xFA, 0xFA, 0xFA));
        colorList.add(new ColorName("Черный", 0x00, 0x00, 0x00));
        colorList.add(new ColorName("Черный", 0x24, 0x24, 0x24));
        colorList.add(new ColorName("Серый", 0xB5, 0xB8, 0xB1));
        colorList.add(new ColorName("Серый", 0x47, 0x4A, 0x51));
        colorList.add(new ColorName("Серый", 0x9C, 0x9C, 0x9C));
        colorList.add(new ColorName("Красный", 0xFF, 0x00, 0x00));
        colorList.add(new ColorName("Красный", 0xE3, 0x26, 0x36));
        colorList.add(new ColorName("Красный", 0xBF, 0x22, 0x33));
        colorList.add(new ColorName("Красный", 0x9B, 0x11, 0x1E));
        colorList.add(new ColorName("Красный", 0xE6, 0x4A, 0x3B));
        colorList.add(new ColorName("Зеленый", 0x00, 0xFF, 0x00));
        colorList.add(new ColorName("Зеленый", 0x00, 0x80, 0x00));
        colorList.add(new ColorName("Зеленый", 0x00, 0xFF, 0x7F));
        colorList.add(new ColorName("Зеленый", 0x1E, 0x59, 0x45));
        colorList.add(new ColorName("Зеленый", 0x50, 0x87, 0x32));
        colorList.add(new ColorName("Зеленый", 0xC3, 0xD7, 0x2D));
        colorList.add(new ColorName("Зеленый", 0x85, 0x7E, 0x32));
        colorList.add(new ColorName("Синий", 0x00, 0x00, 0xFF));
        colorList.add(new ColorName("Синий", 0x15, 0x60, 0xBD));
        colorList.add(new ColorName("Синий", 0x00, 0x2F, 0x55));
        colorList.add(new ColorName("Желтый", 0xFF, 0xFF, 0x00));
        colorList.add(new ColorName("Желтый", 0xFF, 0xFF, 0x66));
        colorList.add(new ColorName("Желтый", 0xCC, 0xA8, 0x17));
        colorList.add(new ColorName("Желтый", 0xEE, 0xCE, 0x37));
        colorList.add(new ColorName("Оранжевый", 0xFF, 0xA5, 0x00));
        colorList.add(new ColorName("Оранжевый", 0xF7, 0x5E, 0x25));
        colorList.add(new ColorName("Оранжевый", 0xF1, 0x3A, 0x13));
        colorList.add(new ColorName("Коричневый", 0x96, 0x4B, 0x00));
        colorList.add(new ColorName("Коричневый", 0x5A, 0x19, 0x0A));
        colorList.add(new ColorName("Коричневый", 0x73, 0x41, 0x32));
        colorList.add(new ColorName("Коричневый", 0xAF, 0x5F, 0x41));
        colorList.add(new ColorName("Коричневый", 0xCA, 0x7D, 0x23));
        colorList.add(new ColorName("Коричневый", 0xAB, 0x7D, 0x0F));
        colorList.add(new ColorName("Коричневый", 0x9E, 0x37, 0x22));
        colorList.add(new ColorName("Голубой", 0x42, 0xAA, 0xFF));
        colorList.add(new ColorName("Голубой", 0x89, 0xDA, 0xEA));
        colorList.add(new ColorName("Голубой", 0xD2, 0xF0, 0xFC));
        colorList.add(new ColorName("Фиолетовый", 0x8B, 0x00, 0xFF));
        colorList.add(new ColorName("Розовый", 0xF1, 0x9C, 0xBB));
        colorList.add(new ColorName("Розовый", 0xE1, 0x41, 0x7D));
        colorList.add(new ColorName("Розовый", 0xDC, 0x6C, 0x56));
        colorList.add(new ColorName("Розовый", 0x7B, 0x37, 0x4F));
        colorList.add(new ColorName("Розовый", 0xAA, 0x50, 0x78));

        colorLists.put("RU", colorList);
    }

    private void addEN() {
        ArrayList<ColorName> colorList = new ArrayList<>();
        colorList.add(new ColorName("White", 0xFF, 0xFF, 0xFF));
        colorList.add(new ColorName("White", 0xFA, 0xFA, 0xFA));
        colorList.add(new ColorName("Black", 0x00, 0x00, 0x00));
        colorList.add(new ColorName("Black", 0x24, 0x24, 0x24));
        colorList.add(new ColorName("Grey", 0xB5, 0xB8, 0xB1));
        colorList.add(new ColorName("Grey", 0x47, 0x4A, 0x51));
        colorList.add(new ColorName("Grey", 0x9C, 0x9C, 0x9C));
        colorList.add(new ColorName("Red", 0xFF, 0x00, 0x00));
        colorList.add(new ColorName("Red", 0xE3, 0x26, 0x36));
        colorList.add(new ColorName("Red", 0xBF, 0x22, 0x33));
        colorList.add(new ColorName("Red", 0x9B, 0x11, 0x1E));
        colorList.add(new ColorName("Red", 0xE6, 0x4A, 0x3B));
        colorList.add(new ColorName("Green", 0x00, 0xFF, 0x00));
        colorList.add(new ColorName("Green", 0x00, 0x80, 0x00));
        colorList.add(new ColorName("Green", 0x00, 0xFF, 0x7F));
        colorList.add(new ColorName("Green", 0x1E, 0x59, 0x45));
        colorList.add(new ColorName("Green", 0x50, 0x87, 0x32));
        colorList.add(new ColorName("Green", 0xC3, 0xD7, 0x2D));
        colorList.add(new ColorName("Green", 0x85, 0x7E, 0x32));
        colorList.add(new ColorName("Blue", 0x00, 0x00, 0xFF));
        colorList.add(new ColorName("Blue", 0x15, 0x60, 0xBD));
        colorList.add(new ColorName("Blue", 0x00, 0x2F, 0x55));
        colorList.add(new ColorName("Yellow", 0xFF, 0xFF, 0x00));
        colorList.add(new ColorName("Yellow", 0xFF, 0xFF, 0x66));
        colorList.add(new ColorName("Yellow", 0xCC, 0xA8, 0x17));
        colorList.add(new ColorName("Yellow", 0xEE, 0xCE, 0x37));
        colorList.add(new ColorName("Orange", 0xFF, 0xA5, 0x00));
        colorList.add(new ColorName("Orange", 0xF7, 0x5E, 0x25));
        colorList.add(new ColorName("Orange", 0xF1, 0x3A, 0x13));
        colorList.add(new ColorName("Brown", 0x96, 0x4B, 0x00));
        colorList.add(new ColorName("Brown", 0x5A, 0x19, 0x0A));
        colorList.add(new ColorName("Brown", 0x73, 0x41, 0x32));
        colorList.add(new ColorName("Brown", 0xAF, 0x5F, 0x41));
        colorList.add(new ColorName("Brown", 0xCA, 0x7D, 0x23));
        colorList.add(new ColorName("Brown", 0xAB, 0x7D, 0x0F));
        colorList.add(new ColorName("Brown", 0x9E, 0x37, 0x22));
        colorList.add(new ColorName("Light blue", 0x42, 0xAA, 0xFF));
        colorList.add(new ColorName("Light blue", 0x89, 0xDA, 0xEA));
        colorList.add(new ColorName("Light blue", 0xD2, 0xF0, 0xFC));
        colorList.add(new ColorName("Purple", 0x8B, 0x00, 0xFF));
        colorList.add(new ColorName("Pink", 0xF1, 0x9C, 0xBB));
        colorList.add(new ColorName("Pink", 0xE1, 0x41, 0x7D));
        colorList.add(new ColorName("Pink", 0xDC, 0x6C, 0x56));
        colorList.add(new ColorName("Pink", 0x7B, 0x37, 0x4F));
        colorList.add(new ColorName("Pink", 0xAA, 0x50, 0x78));

        colorLists.put("EN", colorList);
    }

    /**
     * Get the closest color name from our list
     *
     * @param r red
     * @param g green
     * @param b blue
     * @return Name of color
     */
    public String getColorNameFromRgb(String langPrefix, int r, int g, int b) {
        ColorName closestMatch = null;
        ArrayList<ColorName> list = colorLists.get(langPrefix);
        int minMSE = Integer.MAX_VALUE;
        int mse;
        for (ColorName c : Objects.requireNonNull(list)) {
            mse = c.computeMSE(r, g, b);
            if (mse < minMSE) {
                minMSE = mse;
                closestMatch = c;
            }
        }

        if (closestMatch != null) {
            return closestMatch.getName();
        } else {
            return "No matched color name.";
        }
    }

    /**
     * Convert hexColor to rgb, then call getColorNameFromRgb(r, g, b)
     *
     * @param hexColor
     * @return
     */
    public String getColorNameFromHex(String langPrefix, int hexColor) {
        int r = (hexColor & 0xFF0000) >> 16;
        int g = (hexColor & 0xFF00) >> 8;
        int b = (hexColor & 0xFF);
        return getColorNameFromRgb(langPrefix, r, g, b);
    }

    public int colorToHex(Color c) {
        return Integer.decode("0x" + Integer.toHexString(c.toArgb()).substring(2));
    }

    public String getColorNameFromColor(String langPrefix, Color color) {
        return getColorNameFromRgb(langPrefix, (int)color.red(), (int)color.green(), (int)color.blue());
    }

    public int getColorFromColorName(String langPrefix, String name) {
        ArrayList<ColorName> list = colorLists.get(langPrefix);
        ColorName colorName = Objects.requireNonNull(list).stream().filter(x -> Objects.equals(x.name, name)).findFirst().get();
        return Color.parseColor(String.format("#%02X%02X%02X", (int)colorName.r, (int)colorName.g, (int)colorName.b));
    }

    public String getRandomColorName(String langPrefix) {
        ArrayList<ColorName> list = colorLists.get(langPrefix);
        return Objects.requireNonNull(list).get((int)(Math.random() * list.size())).name;
    }

    public class ColorName {
        public int r, g, b;
        public String name;

        public ColorName(String name, int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
            this.name = name;
        }

        public int computeMSE(int pixR, int pixG, int pixB) {
            return (int) ((
                    (pixR - r) * (pixR - r) +
                    (pixG - g) * (pixG - g) +
                    (pixB - b) * (pixB - b)) / 3);
        }

        public int getR() {
            return r;
        }

        public int getG() {
            return g;
        }

        public int getB() {
            return b;
        }

        public String getName() {
            return name;
        }
    }
}