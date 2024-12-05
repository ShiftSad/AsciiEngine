package codes.shiftmc.ascii;

import java.awt.image.BufferedImage;

public class AsciiEngine {

    private final String ascii = " `.-':_,^=;><+!rc*/z?sLTv)J7(|Fi{C}fI31tlu[neoZ5Yxjya]2ESwqkP6h9d4VpOGbUAKXHm8RD#$Bg0MNWQ%&@";

    public String getText(BufferedImage image) {
        StringBuilder result = new StringBuilder();

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int color = image.getRGB(x, y);

                // extract each color component
                int red   = (color >>> 16) & 0xFF;
                int green = (color >>>  8) & 0xFF;
                int blue  = (color) & 0xFF;

                // Number from 0 to 1
                float luminance = (red * 0.2126f + green * 0.7152f + blue * 0.0722f) / 255;

                int index = Math.round(luminance * (ascii.length() - 1));
                char asciiChar = ascii.charAt(index);

                result.append(getAnsiColor(red, green, blue));
                result.append(asciiChar);
            }

            result.append("\n");
        }

        return result.toString();
    }


    private static String getAnsiColor(int r, int g, int b) {
        return String.format("\u001B[38;2;%d;%d;%dm", r, g, b);
    }
}
