package codes.shiftmc.ascii;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.metadata.display.TextDisplayMeta;
import net.minestom.server.instance.Instance;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class NoTickingEntity extends Entity {
    private final AsciiEngine ascii = new AsciiEngine();

    public NoTickingEntity(Instance instance, Pos pos) throws URISyntaxException, IOException {
        super(EntityType.TEXT_DISPLAY);

        var meta = (TextDisplayMeta) getEntityMeta();
        meta.setLineWidth(1000000);

        setInstance(instance, pos);
    }

    @Override
    public void tick(long time) {
        var meta = (TextDisplayMeta) getEntityMeta();

        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        try {
            BufferedImage capture = new Robot().createScreenCapture(screenRect);
            capture = resizeImage(capture, screenRect.width / 15, screenRect.height / 15);
            meta.setText(ascii.getComponent(capture));
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());
        Graphics2D g2d = resizedImage.createGraphics();

        // Enable anti-aliasing and quality scaling
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the resized image
        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();

        return resizedImage;
    }

}