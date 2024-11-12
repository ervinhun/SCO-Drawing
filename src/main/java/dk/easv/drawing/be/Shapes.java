package dk.easv.drawing.be;

public class Shapes {
    private int size;
    private String shape;
    private int line;
    private boolean filled;
    String color;

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public Shapes(int size, String shape, int line, boolean filled, String color) {
        this.size = size;
        this.shape = shape;
        this.line = line;
        this.filled = filled;
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getColor() { return color;}

    @Override
    public String toString() {
        return shape + " " + size + " " + line + " " + (filled?color + "(F)":color);
    }


}
