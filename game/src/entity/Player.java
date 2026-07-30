package entity;
//import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import mainn.Gamepanel;
import mainn.KeyHandler;
public class Player extends Entity{
    
    Gamepanel gp;
    KeyHandler keyH;

    public Player(Gamepanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage(); 
    }

    public void setDefaultValues(){
        x=100;
        y=100;
        speed=4;
        direction="down";
    }

    public void getPlayerImage(){
    try {
        down = ImageIO.read(new java.io.File("src/res/player/down.png"));
        up = ImageIO.read(new java.io.File("src/res/player/up.png"));
        right = ImageIO.read(new java.io.File("src/res/player/right.png"));
        left = ImageIO.read(new java.io.File("src/res/player/left.png"));
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    

    public void update(){
        if(keyH.upPressed==true){
            direction="up";
            y-=speed;

        }
        else if(keyH.downPressed==true){
            direction="down";
            y+=speed;
        }
        else if(keyH.rightPressed==true){
            direction="right";
            x+=speed;
        }
        else if(keyH.leftPressed==true){
            direction="left";
            x-=speed;
        }
    }

    public void draw(Graphics2D g2){
       // g2.setColor(Color.white);
        //g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image=down;
        switch(direction){
            case "up":
                image=up;
                break;
            case "down":
                image=down;
                break;
            case "right":
                image=right;
                break;
            case "left":
                image=left;
                break;
        }
        
        g2.drawImage(image,x,y,gp.tileSize,gp.tileSize,null);
    }
}
