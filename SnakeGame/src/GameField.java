import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private Image dot;
    private Image eat;
    private int count=0;
    private int eatX;
    private int eatY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = false;
    private boolean again = false;
    private int score = 0;

    public GameField(){
        setBackground(Color.black);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void initGame(){
        dots=3;
        for (int i = 0; i < dots ; i++) {
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }
        score = 0;
        if(!again)
            timer = new Timer(250, this);
            timer.start();
            createEat();
    }


    public void createEat(){
        eatX = new Random().nextInt(20)*DOT_SIZE;
        eatY = new Random().nextInt(20)*DOT_SIZE;
    }
    public void loadImages(){
        ImageIcon iie = new ImageIcon("eat.png");
        eat = iie.getImage();
        ImageIcon iid = new ImageIcon("partsnake.png");
        dot = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);
        if (inGame) {
            g.drawImage(eat, eatX, eatY, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
                g.drawString(score+"", 16,16);
            }
        } else {
            String str = "Press Enter";
            g.drawString(str, 141, SIZE / 2);
        }
    }


    public void move(){
        for (int i = dots; i > 0  ; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(left){
            x[0] -= DOT_SIZE;
        }
        if(right){
            x[0] += DOT_SIZE;
        }
        if(up){
            y[0] -= DOT_SIZE;
        }
        if(down){
            y[0] += DOT_SIZE;
        }
    }

    public void checkEat(){
        if(x[0] == eatX && y[0] == eatY){
            dots++;
            score++;
            createEat();
        }
    }

    public void checkCollisions(){
        for (int i = dots; i > 0 ; i--) {
            if(i>4 &&x[0]==x[i] && y[0] == y[i]){
                inGame=false;
            }
        }
        if(x[0]>SIZE || x[0]<16 || y[0]>SIZE || y[0]<16){
            inGame=false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            checkEat();
            checkCollisions();
            move();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_UP && !down){
                left = false;
                up = true;
                right = false;
            }
            if(key == KeyEvent.VK_DOWN && !up){
                left = false;
                down = true;
                right = false;
            }
            if(key == KeyEvent.VK_ENTER){
                inGame = true;
                again = true;
                left = false;
                right = true;
                down = false;
                up = false;
                initGame();
            }
        }
    }
}
