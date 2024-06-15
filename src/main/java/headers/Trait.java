package headers;

import javax.swing.*;
import java.awt.*;

public class Trait {
    private final String name;

    private Image img;

    public Trait(String name, ImageIcon ic) {
        this.name = name;
        img = ic.getImage();
    }

    public String getName() {
        return name;
    }

    public Image getImg() {
        return img;
    }
}
