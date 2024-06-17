package headers;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class Trait implements Serializable {
    private final String name;

    private transient Image img;

    public Trait(String name, ImageIcon ic) {
        this.name = name;
        img = ic.getImage();
    }

    public String getName() {
        return name;
    }

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
