package dk.easv.drawing.dal;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class CanvasSaver {
    private static final String FILEPATH = "./data/";

    public static void saveCanvasAsPng(Canvas canvas, String filename) throws IOException {
        // Step 1: Render the Canvas to a WritableImage
        WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        canvas.snapshot(null, writableImage);

        // Step 2: Convert WritableImage to BufferedImage and save as PNG
        File file = new File(FILEPATH + filename);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
            System.out.println("Canvas saved as PNG to: " + FILEPATH);
        } catch (IOException e) {
            System.err.println("Failed to save the canvas as PNG: " + e.getMessage());
        }
    }
}
