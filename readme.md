# ASCII Engine for Minestom

### Overview
The **ASCII Engine** is a simple and efficient engine built in Java for generating ASCII art and text-based visual representations of images. Designed specifically for use in Minecraft with the Minestom framework, it allows server developers to bring unique text-based visuals into their projects with ease.

---

### Features
- ⭐ **Dynamic ASCII Art Conversion:** Converts images into ASCII art with accurate color representation.
- ⭐ **Adventure Component Integration:** Generates Adventure Components for seamless integration with Minestom's chat and text systems.
- ⭐ **High Customizability:** Customize how images are converted to ASCII using predefined character sets.
- ⭐ **Efficient Design:** Designed for performance, even with large images.

---

### Showcase
[![Showcase Video 1](https://img.youtube.com/vi/ZM-AFZioXRE/0.jpg)](https://youtu.be/ZM-AFZioXRE)  
[![Showcase Video 2](https://img.youtube.com/vi/-8MN6c9gMxg/0.jpg)](https://youtu.be/-8MN6c9gMxg)

---

### Sample Usage
Here is how you can use the **ASCII Engine** in your Minestom project:

```java
public class AsciiEngineExample {
    public static void main(String[] args) {
        AsciiEngine engine = new AsciiEngine();

        try {
            // Load an image
            BufferedImage image = ImageIO.read(new File("path/to/your/image.png"));

            // Get ASCII art as plain text
            String asciiArt = engine.getText(image);
            System.out.println(asciiArt);

            // Get Adventure Component for Minestom
            Component asciiComponent = engine.getComponent(image);

            // Example usage in Minestom (sending to a player)
            Player player = ...; // Your player instance
            player.sendMessage(asciiComponent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

---

### How It Works
1. **ASCII Character Mapping:** The engine maps pixel brightness to a predefined set of ASCII characters for accurate representation of luminance.
2. **Color Representation:** TextColor is used to replicate the original image's colors in the ASCII art.
3. **Adventure Components:** The generated art is converted into Adventure components for rich server-side rendering.

---

### License
This project is licensed under the [Apache License](https://github.com/ShiftSad/AsciiEngine/blob/master/LICENSE).
