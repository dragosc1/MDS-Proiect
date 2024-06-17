package headers;

public class Button {
    private String Image; // Image path of the button
    private boolean active; // State of the button (active/inactive)
    private int x, y; // Position of the button
    private int dimX, dimY; // Dimensions (width and height) of the button

    public Button(String image, boolean active, int x, int y, int dimX, int dimY) {
        Image = image; // Set the image path
        this.active = active; // Set the button state

        // Set position
        this.x = x;
        this.y = y;

        //Set dimensions
        this.dimX = dimX;
        this.dimY = dimY;
        if (dimX == 0)
            this.dimX = 250;
        if (dimY == 0)
            this.dimY = 75;

        // Change image path based on the active state
        if (active == true)
            Image = "assets/buttons/buton_activ.png";
        else  Image = "assets/buttons/buton_inactiv.png";
    }

    // Method to set the button to active state
    public void setToActive() {
        this.active = true;
        Image = "assets/buttons/buton_activ.png";
    }

    // Method to set the button to inactive state
    public void setToInactive() {
        this.active = false;
        Image = "assets/buttons/buton_inactiv.png";
    }

    // setters and getters
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
