import headers.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.ArrayList;

public class bd {
    public static void main(String[] args) {
        option options;
        class Menu {
            private List<option> options;
            private int currentPosition;

            public Menu() {
                options = new ArrayList<>();
                currentPosition = -5;
            }

            public void addOption(String text, int x, int y) {
                options.add(new option(text, x, y));
            }

            public option getOptionAt(int index) {
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

        // initialize the window screen with the menu image
        screen window = new screen("assets/mmu.png");
        
        Menu menu = new Menu();
        
        // window 
        menu.addOption("New Game", 180, 460);
        menu.addOption("Options", 180, 500);
        menu.addOption("Exit", 180, 540);
        menu.displayMenu();

        for (int i = 0; i < 3; i++) {
            option option = menu.getOptionAt(i);
            window.addTextAtPixel(option.getText(), option.getX(), option.getY());
        }

        // add key listener
        window.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    menu.moveUp();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    menu.moveDown();
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (menu.getCurrentPosition() == 2) {
                        window.dispose(); // Close the window
                        System.exit(0); // Exit the program
                    }
                }

                // refresh screen
                window.clearTextPixels();
                menu.displayMenu();
                for (int i = 0; i < 3; i++) {
                    option option = menu.getOptionAt(i);
                    window.addTextAtPixel(option.getText(), option.getX(), option.getY());
                }
            }
        });


        // make the window visible
        window.setVisible(true);
    }
}