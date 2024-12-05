package codes.shiftmc.ascii;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

import java.awt.image.BufferedImage;

public class AsciiEngine {

    private static final String ascii = "`.-':_,^=;><+!rc*/z?sLTv)J7(|Fi{C}fI31tlu[neoZ5Yxjya]2ESwqkP6h9d4VpOGbUAKXHm8RD#$Bg0MNWQ%&@";

    private static String getAnsiColor(int r, int g, int b) {
        return String.format("\u001B[38;2;%d;%d;%dm", r, g, b);
    }

    public String getText(BufferedImage image) {
        StringBuilder result = new StringBuilder();

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                PixelData pixelData = getPixelData(image.getRGB(x, y));
                result.append(getAnsiColor(pixelData.red, pixelData.green, pixelData.blue))
                        .append(pixelData.asciiChar);
            }
            result.append("\n");
        }

        return result.toString();
    }

    public Component getComponent(BufferedImage image) {
        Component component = Component.empty();

        TextColor previousColor = null;
        int repeatCount = 0;

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int color = image.getRGB(x, y);

                // Extract each color component
                int red = (color >>> 16) & 0xFF;
                int green = (color >>> 8) & 0xFF;
                int blue = color & 0xFF;

                TextColor currentColor = TextColor.color(red, green, blue);

                if (currentColor.equals(previousColor)) {
                    repeatCount++;
                    continue;
                }

                if (previousColor != null) {
                    component = component.append(Component.text("█".repeat(repeatCount), previousColor));
                }

                // Start new color block
                previousColor = currentColor;
                repeatCount = 1;
            }

            if (previousColor != null) {
                component = component.append(Component.text("█".repeat(repeatCount), previousColor));
            }

            component = component.append(Component.text("\n"));
            previousColor = null;
            repeatCount = 0;
        }

        return component;
    }

    private PixelData getPixelData(int color) {
        int red = (color >>> 16) & 0xFF;
        int green = (color >>> 8) & 0xFF;
        int blue = (color) & 0xFF;

        float luminance = (red * 0.2126f + green * 0.7152f + blue * 0.0722f) / 255;
        int index = Math.round(luminance * (ascii.length() - 1));
        char asciiChar = ascii.charAt(index);

        return new PixelData(red, green, blue, asciiChar);
    }

    private static class PixelData {
        int red, green, blue;
        char asciiChar;

        PixelData(int red, int green, int blue, char asciiChar) {
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.asciiChar = asciiChar;
        }
    }
}
