import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Menu extends JPanel implements ActionListener {

    private JButton button;
    private Tetris tetris;
    private boolean playing;
    public static final int WIDTH = 300, HEIGHT = 60;
    public static final int WIDTH2 = 517, HEIGHT2 = 1039;

   public Menu(){
       playing = false;
       button = new JButton("PLAY");
       button.setFont(new Font("Arial", Font.BOLD,35));
       button.setPreferredSize(new Dimension(WIDTH, HEIGHT));
      button.addActionListener(this);
      this.add(button);

   }


    @Override
    public void actionPerformed(ActionEvent e) {
       if(!playing){
           playing = true;
           button.setText("PLAYING");

           JFrame window = new JFrame("TETRIS");
           window.setSize(WIDTH2, HEIGHT2);
           window.setResizable(false);
           window.setLocationRelativeTo(null);

           tetris = new Tetris();
           window.add(tetris);
           window.addKeyListener(tetris);
           window.setVisible(true);

       }


    }
}
