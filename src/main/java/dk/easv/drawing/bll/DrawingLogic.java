package dk.easv.drawing.bll;

import dk.easv.drawing.be.Shapes;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Circle;

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
        for (Shapes shape : shapes) {
            if (maxHeight < shape.getSize())
                maxHeight = shape.getSize();
            if (!firstToDraw) {
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
            switch (shape.getShape()) {
                case "Circle":
                    gc.setLineWidth(2.0);
                    gc.strokeOval(startX, startY, shape.getSize(), shape.getSize());
                    break;
                case "Rectangle":
                    gc.setLineWidth(2.0);
                    gc.strokeRect(startX, startY, shape.getSize()/1.5, shape.getSize());
                    break;
                case "Square":
                    gc.setLineWidth(2.0);
                    gc.strokeRect(startX, startY, shape.getSize(), shape.getSize());
                    break;
                case "Triangle":
                    gc.setLineWidth(2.0);
                    startX += shape.getSize() / 2.0;
                    double height = (Math.sqrt(3) / 2) * shape.getSize();
                    double[] xPoints = {startX, startX - shape.getSize() / 2.0, startX + shape.getSize() / 2.0};
                    double[] yPoints = {startY, startY + height, startY + height};
                    gc.strokePolygon(xPoints, yPoints, 3);
                    break;
            }
            firstToDraw = false;
            prevSize = shape.getSize();
        }
    }
}
