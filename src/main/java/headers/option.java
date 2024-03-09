package headers;

public class option {
    private String text;
    private int x, y;

    public option(String text, int x, int y) {
        this.text = text;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String other) {
        this.text = other;
    }
}