import javax.swing.JFrame;

public class GameFrame extends JFrame {

    GameFrame() {

        GamePanel panel = new GamePanel();
        this.add(panel);
        this.setTitle("SNAKE GAME");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false); // user can set the size of their preference
        this.pack(); //will be sized to fit its contents
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }

}
