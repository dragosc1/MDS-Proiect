package headers;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<Option> options;
    private int currentPosition;

    public Menu() {
        options = new ArrayList<>();
        currentPosition = -5;
    }

    public void addOption(String text, int x, int y) {
        options.add(new Option(text, x, y));
    }

    public Option getOptionAt(int index) {
        return options.get(index);
    }

    public void displayMenu() {
        for (int i = 0; i < options.size(); i++) {
            String option = options.get(i).getText();
            if (option.startsWith("> ")) {
                option = option.substring(2);
            }
            if (i == currentPosition) {
                option = "> " + option;
            }
            options.get(i).setText(option); // Update the option in the list
        }
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void moveUp() {
        if (currentPosition == -5) currentPosition = 0;
        else currentPosition = (currentPosition - 1 + options.size()) % options.size();
    }

    public void moveDown() {
        if (currentPosition == -5) currentPosition = 0;
        else currentPosition = (currentPosition + 1) % options.size();
    }
}