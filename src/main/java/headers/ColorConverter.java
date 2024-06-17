package headers;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class ColorConverter {
    // Map to store color names and corresponding Color objects
    private static final Map<String, Color> colorMap = new HashMap<>();

    // Static block to initialize the color map
    static {
        colorMap.put("WHITE", Color.WHITE);
        colorMap.put("BLACK", Color.BLACK);
        colorMap.put("RED", Color.RED);
        colorMap.put("GREEN", Color.GREEN);
        colorMap.put("BLUE", Color.BLUE);
        colorMap.put("YELLOW", Color.YELLOW);
        colorMap.put("PURPLE", new Color(128, 0, 128));
    }

    // Method to get a Color object from a given color name string
    public static Color getColorFromString(String colorString) {
        return colorMap.getOrDefault(colorString.toUpperCase(), Color.WHITE);
    }
}
