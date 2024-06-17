package headers;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

// Class representing the trait of the player
public class Trait implements Serializable {
    private final String name;

    // Image representing the trait
    private transient Image img;

    // Constructor
    public Trait(String name, ImageIcon ic) {
        this.name = name;
        img = ic.getImage();
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    // Setting the immage
    public void setImageCorrect() {
        if (this.name.equals("The Strong"))
            img = new ImageIcon("assets/Traits/TheStrong.png").getImage();
        if (this.name.equals("The Wise"))
            img = new ImageIcon("assets/Traits/TheWise.png").getImage();
        if (this.name.equals("The Swift"))
            img = new ImageIcon("assets/Traits/TheSwift.png").getImage();
    }


    public Image getImg() {
        return img;
    }
}
