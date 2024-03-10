package headers;

public class TextPixel {
    String text;
    int x, y;

    String color;

    float fontSize;

    String fontName;

    public TextPixel(String text, int x, int y, String color) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.color = color;
        this.fontSize = 30f;
        this.fontName = "OLDENGL";
    }

    public TextPixel(String text, int x, int y, String color, float fontSize) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.color = color;
        this.fontSize = fontSize;
        this.fontName = "OLDENGL";
    }

    public TextPixel(String text, int x, int y, String color, String fontName) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.color = color;
        this.fontName = fontName;
    }

    public TextPixel(String text, int x, int y, String color, float fontSize, String fontName) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.color = color;
        this.fontSize = fontSize;
        this.fontName = fontName;
    }

}