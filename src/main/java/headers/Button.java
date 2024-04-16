package headers;

public class Button {
    private String Image;
    private boolean active;
    private int x, y;
    private int dimX, dimY;

    public Button(String image, boolean active, int x, int y, int dimX, int dimY) {
        Image = image;
        this.active = active;
        this.x = x;
        this.y = y;
        this.dimX = dimX;
        this.dimY = dimY;
        if (dimX == 0)
            this.dimX = 250;
        if (dimY == 0)
            this.dimY = 75;
        if (active == true)
            Image = "assets/buttons/buton_activ.png";
        else  Image = "assets/buttons/buton_inactiv.png";
    }

    public void setToActive() {
        this.active = true;
        Image = "assets/buttons/buton_activ.png";
    }

    public void setToInactive() {
        this.active = false;
        Image = "assets/buttons/buton_inactiv.png";
    }

    public String getImage() {
        return Image;
    }

    public boolean isActive() {
        return active;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setCoord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getDimX() {
        return dimX;
    }

    public int getDimY() {
        return dimY;
    }
}
