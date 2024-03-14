package headers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;

public class NewGame implements Scene {
    private Screen window;

    private ArrayList<Trait> traits;

    private ArrayList<Status> statuses;

    private KeyListener newGameKeyListener;

    private Menu menu;

    private int currentPosition;
    private int currentTraitPosition;

    private int points;

    private Trait chosenTrait;

    private StringBuilder enteredName; // Added StringBuilder to store the name

    private boolean canWriteName;
    private boolean haveEnteredName;
    private String name;
    private JButton newGameButton;

    public NewGame(Screen _window, Menu menu) {
        window = _window;
        window.setBackground("assets/new_game.png");
        initTraits();
        initStatuses();
        this.menu = menu;
        currentPosition = -5;
        currentTraitPosition = 0;
        points = 10;
        enteredName = new StringBuilder(); // Initialize the StringBuilder
        canWriteName = false;
        haveEnteredName = false;
    }

    private void startNewGame() {
        // Implement the logic to start a new game here
        // You might want to reset game-related variables, initialize a new game state, etc.
        // For now, let's just print a message indicating that the new game is starting
        System.out.println("Starting a new game...");
    }

    private void updateButtonState() {
        // Example condition: Enable the button if the player has entered their name
        if (haveEnteredName) {
            newGameButton.setEnabled(true); // Enable the button
        } else {
            newGameButton.setEnabled(false); // Disable the button
        }
    }

    void initTraits() {
        traits = new ArrayList<Trait>();
        traits.add(new Trait("Trait 1"));
        traits.add(new Trait("Trait 2"));
        traits.add(new Trait("Trait 3"));
    }

    void initStatuses() {
        statuses = new ArrayList<Status>();
        statuses.add(new Status("Strength", 1));
        statuses.add(new Status("Dexterity", 1));
        statuses.add(new Status("Intellect", 1));
        statuses.add(new Status("Charisma", 1));
    }

    String calculateIfHere(int index) {
        if (index == getCurrentPosition())
            return "> ";
        else return " ";
    }

    String calculateIfHereTrait(int position, int index1, int index2) {
        if (position == 0 && index1 == index2)
            return "> ";
        else return " ";
    }

    int calculateIfHereMinus(int index) {
        if (index == getCurrentPosition())
            return 17;
        else return 0;
    }

    void drawTexts() {
        window.addTextAtPixel("Choose trait", 175, 90, "WHITE", 0f);
        window.addTextAtPixel(calculateIfHere(1) + "Name: ", 10, 250, "BLACK", 0f, "Arial");
        window.addTextAtPixel("Points: ", 165, 325, "WHITE", 0f);
    }

    void drawPoints() {
        window.addTextAtPixel(Integer.toString(points), 260, 325, "GREEN", 0f);
    }

    String colorTrait(int index) {
        if (chosenTrait == traits.get(index))
            return "GREEN";
        else return "WHITE";
    }

    String colorName() {
        if (haveEnteredName == true)
            return "PURPLE";
        else return "WHITE";
    }

    void drawEnteredName() {
        window.addTextAtPixel(enteredName.toString(), 130, 250, colorName(), 0f);
    }

    @Override
    public void display() {
        drawTexts();
        drawPoints();
        drawEnteredName();

        // draw traits
        for (int i = 0; i < traits.size(); i++) {
            window.addTextAtPixel(calculateIfHereTrait(currentPosition, currentTraitPosition, i) + traits.get(i).getName(), 50 + i * traits.get(i).getName().length() * 20, 150, colorTrait(i), 0f);

        }

        // draw statuses
        for (int i = 0; i < statuses.size(); i++) {
            window.addTextAtPixel(calculateIfHere(i + 2) + statuses.get(i).getName(), 25 - calculateIfHereMinus(i + 2), 415 + i * 68, "WHITE", 0f);
            for (int j = 0; j < statuses.get(i).getPoints(); j++)
                window.addSquareAtPixel(167 + 30 * j, 394 + 68 * i, "WHITE", 25, 25);
        }

    }

    void removeKeyAdaptor() {
        window.removeKeyListener(newGameKeyListener);
    }

    private void goToMainMenu() {
        removeKeyAdaptor();
        window.clearScreen();
        window.setCurentScene(menu);
        window.setBackground("assets/mmu.png");
    }

    @Override
    public void listenToInput() {
        newGameKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    goToMainMenu();
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    moveUp();
                    currentTraitPosition = 0;
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    moveDown();
                    currentTraitPosition = 0;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (getCurrentPosition() == 0) {
                        currentTraitPosition = (currentTraitPosition + 1) % (traits.size());
                    }
                    else if (getCurrentPosition() >= 2) {
                        if (points > 0) {
                            int p = statuses.get(getCurrentPosition() - 2).getPoints();
                            if (p < 9) {
                                statuses.get(getCurrentPosition() - 2).setPointsInStatus(p + 1);
                                points--;
                            }
                        }
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (getCurrentPosition() == 0) {
                        currentTraitPosition = (currentTraitPosition - 1 + traits.size()) % (traits.size());
                    }
                    else if (getCurrentPosition() >= 2) {
                        int p = statuses.get(getCurrentPosition() - 2).getPoints();
                        if (p > 1) {
                            statuses.get(getCurrentPosition() - 2).setPointsInStatus(p - 1);
                            points++;
                        }
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (getCurrentPosition() == 0)
                        chosenTrait = traits.get(currentTraitPosition);
                    if (getCurrentPosition() == 1) {
                        if (canWriteName == false) {
                            canWriteName = true;
                            haveEnteredName = false;
                        }
                        else if (enteredName.length() > 0) {
                            haveEnteredName = true;
                            canWriteName = false;
                            name = enteredName.toString();
                            moveDown();
                        }
                    }
                    else canWriteName = false;
                } else if (getCurrentPosition() == 1 && canWriteName) { // Check if the name field is selected
                    if (Character.isAlphabetic(e.getKeyChar()) || e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                        // Only allow alphabetic characters or backspace
                        if (e.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
                            if (enteredName.length() < 20) { // Limit the name length to 10 characters
                                enteredName.append(e.getKeyChar());
                            }
                        } else if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE && enteredName.length() > 0) {
                            enteredName.deleteCharAt(enteredName.length() - 1);
                        }
                    }
                }
            }
        };

        window.addKeyListener(newGameKeyListener);
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void moveUp() {
        if (currentPosition == -5) currentPosition = 0;
        else currentPosition = (currentPosition - 1 + (2 + statuses.size())) % (2 + statuses.size());
    }

    public void moveDown() {
        if (currentPosition == -5) currentPosition = 0;
        else currentPosition = (currentPosition + 1) % (2 + statuses.size());
    }

}