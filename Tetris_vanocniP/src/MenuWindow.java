import javax.swing.*;

public class MenuWindow {

    private JFrame windowFrame;
    private Menu menu;
    public static final int WINDOW_WIDTH = 400, WINDOW_HEIGHT = 300;

    public MenuWindow() {
        windowFrame = new JFrame("MENU");
        windowFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowFrame.setResizable(false);
       // windowFrame.setLocationRelativeTo(null); //neni potreba je to jen proto at nemusime presouvat okno (hru - treba doprostred)

        menu = new Menu();
        windowFrame.add(menu);
        windowFrame.setVisible(true);
    }
}
