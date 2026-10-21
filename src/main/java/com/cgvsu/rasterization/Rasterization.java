package com.cgvsu.rasterization;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class Rasterization {

    public static void drawRectangle(
            final GraphicsContext graphicsContext,
            final int x, final int y,
            final int width, final int height,
            final Color color)
    {
        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();

        for (int row = y; row < y + height; ++row)
            for (int col = x; col < x + width; ++col)
                pixelWriter.setColor(col, row, color);
    }

    // заполнение эллипса
    public static void drawEllipse(
            final GraphicsContext graphicsContext,
            final double xc, final double yc,
            final double a, final double b,
            final Color colorCenter,
            final Color colorEdge)
    {
        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();

        int startX = (int) (xc - a);
        int endX   = (int) (xc + a);
        int startY = (int) (yc - b);
        int endY   = (int) (yc + b);

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {

                double dx = (x - xc) / a;
                double dy = (y - yc) / b;
                double value = dx * dx + dy * dy;

                if (value <= 1.0) {
                    double distance = Math.sqrt(value);
                    Color pixelColor = interpolateColor(colorCenter, colorEdge, distance);
                    pixelWriter.setColor(x, y, pixelColor);
                }
            }
        }
    }

    private static Color interpolateColor(Color c1, Color c2, double t) {
        double red   = c1.getRed()   * (1 - t) + c2.getRed()   * t;
        double green = c1.getGreen() * (1 - t) + c2.getGreen() * t;
        double blue  = c1.getBlue()  * (1 - t) + c2.getBlue()  * t;
        return new Color(red, green, blue, 1.0);
    }
}