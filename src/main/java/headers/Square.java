package headers;

public class Square {
    private int x;
    private int y;
    private String color;
    private int dimX;
    private int dimY;
    public Square(int x, int y, String color, int dimX, int dimY) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.dimX = dimX;
        this.dimY = dimY;
    }

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
