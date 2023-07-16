import javax.swing.*;
import java.awt.*;

public class HammurabiWindow extends JFrame {
    JLabel title;


    public static void main(String[] args){
        new HammurabiWindow();
    }


    HammurabiWindow() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Hammurabi");
        this.setLayout(new FlowLayout());
        this.setSize(350, 220);
        this.setResizable(false);
        this.setVisible(true);

        Hammurabi game = new Hammurabi();
        game.playGame();

    }



}
