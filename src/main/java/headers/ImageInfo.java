package headers;

import java.awt.*;

public class ImageInfo {
    private Image image;
    private int x;
    private int y;
    private int dimX;
    private int dimY;

    public ImageInfo(Image image, int x, int y, int dimX, int dimY) {
        this.image = image;
        this.x = x;
        this.y = y;
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
