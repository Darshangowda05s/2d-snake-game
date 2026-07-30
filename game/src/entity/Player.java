package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.Gamepanel;
import main.KeyHandler;

public class Player extends Entity {

    private final Gamepanel gp;
    private final KeyHandler keyH;

    public Player(Gamepanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
    }

    private void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    private void getPlayerImage() {
        try {
            down = ImageIO.read(new java.io.File("src/resources/player/head.png"));
            up = ImageIO.read(new java.io.File("src/resources/player/up.png"));
            right = ImageIO.read(new java.io.File("src/resources/player/right.png"));
            left = ImageIO.read(new java.io.File("src/resources/player/left.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Unable to load player images: " + e.getMessage());
        }
    }

    public void update() {
        if (keyH.upPressed) {
            direction = "up";
            y -= speed;
        } else if (keyH.downPressed) {
            direction = "down";
            y += speed;
        } else if (keyH.rightPressed) {
            direction = "right";
            x += speed;
        } else if (keyH.leftPressed) {
            direction = "left";
            x -= speed;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = down;
        switch (direction) {
            case "up" -> image = up;
            case "down" -> image = down;
            case "right" -> image = right;
            case "left" -> image = left;
            default -> image = down;
        }

        if (image != null) {
            g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
        }
    }
}
