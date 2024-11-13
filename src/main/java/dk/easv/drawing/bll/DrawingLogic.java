package dk.easv.drawing.bll;

import dk.easv.drawing.be.Shapes;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DrawingLogic {
    private List<Shapes> shapes;
    private String pattern;
    private Canvas canvas;
    private double startX, startY;
    private int maxHeight;

    public DrawingLogic(List<Shapes> shapes, String pattern, Canvas canvas) {
        this.shapes = shapes;
        this.pattern = pattern;
        this.canvas = canvas;
        startX = 10.0;
        startY = 10.0;
        maxHeight = 0;
        Draw();
    }

    private void Draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        boolean firstToDraw = true;
        double prevSize = 0.0;
        double[] xPoints;
        double[] yPoints;
        Color fillColor = Color.BLACK;
        for (Shapes shape : shapes) {
            if (maxHeight < shape.getSize())
                maxHeight = shape.getSize();
            gc.setLineWidth(shape.getLine());

            /**
             * If it is not the first shape to draw or Random,
             * then changing the startpoint according to the pattern
             */
            if (!firstToDraw || pattern.equals("Random")) {
                switch (pattern) {
                    case "Grid":
                        startX += prevSize + 10.0;
                        if ((startX + shape.getSize()) >= canvas.getWidth()) {
                            startX = 10.0;
                            startY += maxHeight + 10.0;
                            if ((startY + shape.getSize()) >= canvas.getHeight())
                                return;
                        }
                        break;
                    case "Random":
                        Random rX = new Random();
                        Random rY = new Random();
                        startX = rX.nextDouble(canvas.getWidth() - shape.getSize());
                        startY = rY.nextDouble(canvas.getHeight() - shape.getSize());
                        break;
                    case "Cross":
                        startX += prevSize / 2.0;
                        if (startX >= canvas.getWidth()+shape.getSize()) {
                            startX = 10.0;
                            startY += maxHeight + 10.0;
                        }
                        break;
                }
            }

            /**
             * Setting color, for the line or for the fill
             */
            switch (shape.getColor()) {
                case "Black":
                    fillColor = Color.BLACK;
                    break;
                case "Red":
                    fillColor = Color.RED;
                    break;
                case "Green":
                    fillColor = Color.GREEN;
                    break;
                case "Blue":
                    fillColor = Color.BLUE;
                    break;
            }

            /**
             * Setting the color to the shape
             */
            if (shape.isFilled())
                gc.setFill(fillColor);
            else
                gc.setStroke(fillColor);


            /**
             * Drawing the shapes
             */
            switch (shape.getShape()) {
                case "Circle":
                    if (shape.isFilled())
                        gc.fillOval(startX, startY, shape.getSize(), shape.getSize());
                    else
                        gc.strokeOval(startX, startY, shape.getSize(), shape.getSize());
                    break;
                case "Rectangle":
                    if (shape.isFilled())
                        gc.fillRect(startX, startY, shape.getSize(), shape.getSize());
                    else
                        gc.strokeRect(startX, startY, shape.getSize()/1.5, shape.getSize());
                    break;
                case "Square":
                    if (shape.isFilled())
                        gc.fillRect(startX, startY, shape.getSize(), shape.getSize());
                    else
                        gc.strokeRect(startX, startY, shape.getSize(), shape.getSize());
                    break;
                case "Triangle":
                    double startX3 = startX + shape.getSize() / 2.0;
                    double height = (Math.sqrt(3) / 2) * shape.getSize();
                    xPoints = new double[]{startX3, startX3 - shape.getSize() / 2.0, startX3 + shape.getSize() / 2.0};
                    yPoints = new double[]{startY, startY + height, startY + height};
                    if (shape.isFilled())
                        gc.fillPolygon(xPoints, yPoints, 3);
                    else
                        gc.strokePolygon(xPoints, yPoints, 3);
                    break;
                case "Star":
                    // Calculate the points of the star
                    xPoints = new double[10];
                    yPoints = new double[10];

                    for (int i = 0; i < 10; i++) {
                        double angle = Math.toRadians(36 * i); // Each point is 36 degrees apart (360/10)
                        double radius = (i % 2 == 0) ? (double) shape.getSize() /2 : (((double) shape.getSize() / 2)/2); // Alternate between outer and inner points
                        xPoints[i] = (startX + (double) shape.getSize() /2) + radius * Math.cos(angle);
                        yPoints[i] = (startY + (double) shape.getSize() /2) - radius * Math.sin(angle); // Subtract for Y because JavaFX Y-axis is inverted
                    }
                    // Draw the outline of the star
                    if (shape.isFilled())
                        gc.fillPolygon(xPoints, yPoints, 10);
                    else
                        gc.strokePolygon(xPoints, yPoints, 10);
            }
            firstToDraw = false;
            prevSize = shape.getSize();
        }
    }
}
