import headers.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class bd {
    public static void main(String[] args) {

        // initialize the window screen with the menu image
        Screen window = new Screen("assets/mmu.png");
        
        Menu menu = new Menu();
        
        // window 
        menu.addOption("New Game", 180, 460);
        menu.addOption("Options", 180, 500);
        menu.addOption("Exit", 180, 540);
        menu.displayMenu();

        for (int i = 0; i < 3; i++) {
            Option option = menu.getOptionAt(i);
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
                    Option option = menu.getOptionAt(i);
                    window.addTextAtPixel(option.getText(), option.getX(), option.getY());
                }
            }
        });


        // make the window visible
        window.setVisible(true);
    }
}