package dk.easv.drawing.dal;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PdfSaver {
        private final static String FILEPATH = "./save/";


        public static void savePdf(Canvas canvas, String file) {
            // Step 1: Render the Canvas to a WritableImage
            WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
            canvas.snapshot(null, writableImage);

            // Step 2: Convert WritableImage to BufferedImage
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);

            // Step 3: Create a PDF document with PDFBox and add the image to it
            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);

                // Create an ImageXObject from the BufferedImage
                File tempImageFile = File.createTempFile("canvasImage", ".png");
                ImageIO.write(bufferedImage, "png", tempImageFile);
                PDImageXObject pdfImage = PDImageXObject.createFromFile(tempImageFile.getAbsolutePath(), document);

                // Define dimensions to fit the image to the PDF page
                float scale = Math.min(page.getMediaBox().getWidth() / (float) bufferedImage.getWidth(),
                        page.getMediaBox().getHeight() / (float) bufferedImage.getHeight());
                float imageWidth = bufferedImage.getWidth() * scale;
                float imageHeight = bufferedImage.getHeight() * scale;
                float x = (page.getMediaBox().getWidth() - imageWidth) / 2;
                float y = (page.getMediaBox().getHeight() - imageHeight) / 2;

                // Draw the image on the PDF
                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    contentStream.drawImage(pdfImage, x, y, imageWidth, imageHeight);
                }

                // Save the PDF document
                document.save(FILEPATH + file);
                System.out.println("Canvas saved as PDF to: " + file);

                // Delete the temporary image file
                tempImageFile.delete();
            } catch (IOException e) {
                System.err.println("Failed to save the canvas as PDF: " + e.getMessage());
            }
        }
    }

