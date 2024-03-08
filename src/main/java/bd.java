import headers.screen;

public class bd {
    public static void main(String[] args) {
        // initialize the window screen with the menu image
        screen window = new screen("../../../assets/mmu.png");

        // window 
        window.addTextAtPixel("New Game", 180, 460);
        window.addTextAtPixel("Options", 180, 500);
        window.addTextAtPixel("Exit", 180, 540);

        // make the window visible
        window.setVisible(true);
    }
}