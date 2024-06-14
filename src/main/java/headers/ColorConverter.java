package headers;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class ColorConverter {
    private static final Map<String, Color> colorMap = new HashMap<>();

    static {
        colorMap.put("WHITE", Color.WHITE);
        colorMap.put("BLACK", Color.BLACK);
        colorMap.put("RED", Color.RED);
        colorMap.put("GREEN", Color.GREEN);
        colorMap.put("BLUE", Color.BLUE);
        colorMap.put("YELLOW", Color.YELLOW);
        colorMap.put("PURPLE", new Color(128, 0, 128));
    }

    public static Color getColorFromString(String colorString) {
        return colorMap.getOrDefault(colorString.toUpperCase(), Color.WHITE);
    }
}
