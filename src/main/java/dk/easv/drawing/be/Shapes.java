package dk.easv.drawing.be;

public class Shapes {
    private int size;
    private String shape;

    public Shapes(int size, String shape) {
        this.size = size;
        this.shape = shape;
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

    @Override
    public String toString() {
        return shape + " " + size;
    }
}
