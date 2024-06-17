package headers;

import java.awt.*;

public class ImageInfo {
    private Image image; // The image
    private int x; // X-coordinate of the image
    private int y; // Y-coordinate of the image
    private int dimX; // Width of the image
    private int dimY; // Height of the image

    // Constructor to initialize ImageInfo object with provided parameters

    public ImageInfo(Image image, int x, int y, int dimX, int dimY) {
        //Set image
        this.image = image;
        //Set coordinates
        this.x = x;
        this.y = y;
        //Set width and height
        this.dimX = dimX;
        this.dimY = dimY;
    }

    // Getters and setters
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDimX() {
        return dimX;
    }

    public void setDimX(int dimX) {
        this.dimX = dimX;
    }

    public int getDimY() {
        return dimY;
    }

    public void setDimY(int dimY) {
        this.dimY = dimY;
    }
}
