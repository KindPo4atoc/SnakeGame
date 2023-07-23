import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow(){
        setTitle("Змейка");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(new GameField());
        setSize(368,392);
        setLocation(500,500);
        setVisible(true);

    }

    public static void main(String[] args){
        MainWindow mw = new MainWindow();
    }
}
