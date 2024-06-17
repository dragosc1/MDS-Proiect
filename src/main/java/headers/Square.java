package headers;

// Class for drawing rectangles on screen
public class Square {
    // position (x, y)
    private int x;
    private int y;
    // color
    private String color;

    // dimensions X and Y
    private int dimX;
    private int dimY;

    // Constructor
    public Square(int x, int y, String color, int dimX, int dimY) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.dimX = dimX;
        this.dimY = dimY;
    }

    // Getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getColor() {
        return color;
    }

    public int getDimX() {
        return dimX;
    }

    public int getDimY() {
        return dimY;
    }

}
