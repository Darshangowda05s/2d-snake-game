package main;
import entity.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
public class Gamepanel extends JPanel implements Runnable {
    final int originalTileSize=16;  //16x16 tile
    final int scale=3;
    final int FPS=60;

    public final int tileSize=originalTileSize*scale;  //48x48 size title

    final int maxScreenCol=16;
    final int maxScreenRow=14;

    final int screenWidth=tileSize*maxScreenCol;  //width 768 px
    final int screenHeight=tileSize*maxScreenRow; //height 576 px

    KeyHandler keyH=new KeyHandler();

    Thread gameThread;

    Player player =new Player(this,keyH);

    //player default position
    
    public Gamepanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }


    public void startGameThread(){
        gameThread=new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){
        
        double drawInterval=1000000000/FPS;
        double delta=0;
        long lastTime=System.nanoTime();
        long currentTime;

        while(gameThread!=null){
            currentTime=System.nanoTime();
            delta+=(currentTime-lastTime)/drawInterval;
            lastTime=currentTime;
            
            if(delta>=1){
                update();
                repaint();
                delta--;
            }
        
        }

    }

    public void update(){
        player.update();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D)g;

        player.draw(g2);
        g2.dispose();
    }

    


    
}
