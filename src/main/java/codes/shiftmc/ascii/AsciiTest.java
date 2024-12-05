package codes.shiftmc.ascii;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AsciiTest {

    public static void main(String[] args) throws IOException, URISyntaxException {
        var url = new URI("https://s-media-cache-ak0.pinimg.com/236x/ac/bb/d4/acbbd49b22b8c556979418f6618a35fd.jpg").toURL();
        var image = ImageIO.read(url);

        AsciiEngine engine = new AsciiEngine();
        String text = engine.getText(image);

        System.out.println(text);

        Files.writeString(Path.of("ascii.txt"), text);
    }
}
