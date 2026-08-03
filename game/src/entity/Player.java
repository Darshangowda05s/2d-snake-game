package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.Gamepanel;
import main.KeyHandler;

public class Player extends Entity {

    private final Gamepanel gp;
    private final KeyHandler keyH;
    private final SnakeBody body = new SnakeBody();
    private boolean moving = true;
    private long growthTimerStart = -1L;

    public Player(Gamepanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
        body.loadImage();
    }

    private void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 2;
        direction = "right";
    }

    private void getPlayerImage() {
        down = readImage("head.png");
        up = readImage("up.png");
        right = readImage("right.png");
        left = readImage("left.png");

        if (down == null || up == null || right == null || left == null) {
            System.err.println("Unable to load player images from the configured resource paths.");
        }
    }

    private BufferedImage readImage(String fileName) {
        String[] candidates = {
                "game/src/resources/player/" + fileName,
                "src/resources/player/" + fileName,
                "resources/player/" + fileName
        };

        for (String path : candidates) {
            File file = new File(path);
            if (file.exists()) {
                try {
                    return ImageIO.read(file);
                } catch (IOException e) {
                    System.err.println("Unable to load player image " + path + ": " + e.getMessage());
                }
            }
        }

        return null;
    }

    public void update() {
        if (!moving) {
            return;
        }

        if (growthTimerStart == -1L) {
            growthTimerStart = System.currentTimeMillis();
        } else if (System.currentTimeMillis() - growthTimerStart >= 2000L) {
            grow();
            growthTimerStart = System.currentTimeMillis();
        }

        String requestedDirection = direction;
        if (keyH.upPressed && !keyH.downPressed) {
            requestedDirection = "up";
        } else if (keyH.downPressed && !keyH.upPressed) {
            requestedDirection = "down";
        } else if (keyH.leftPressed && !keyH.rightPressed) {
            requestedDirection = "left";
        } else if (keyH.rightPressed && !keyH.leftPressed) {
            requestedDirection = "right";
        }

        if (!isOppositeDirection(direction, requestedDirection)) {
            direction = requestedDirection;
        }

        int previousX = x;
        int previousY = y;

        switch (direction) {
            case "up" -> y -= speed;
            case "down" -> y += speed;
            case "right" -> x += speed;
            case "left" -> x -= speed;
            default -> {
            }
        }

        body.moveTo(previousX, previousY);

        if (x < 0 || x + gp.tileSize > gp.screenWidth || y < 0 || y + gp.tileSize > gp.screenHeight) {
            moving = false;
        }
    }

    private boolean isOppositeDirection(String currentDirection, String requestedDirection) {
        return (currentDirection.equals("up") && requestedDirection.equals("down"))
                || (currentDirection.equals("down") && requestedDirection.equals("up"))
                || (currentDirection.equals("left") && requestedDirection.equals("right"))
                || (currentDirection.equals("right") && requestedDirection.equals("left"));
    }

    public void grow() {
        body.grow(1);
    }

    public void draw(Graphics2D g2) {
        body.draw(g2, gp.tileSize);

        BufferedImage image = switch (direction) {
            case "up" -> up;
            case "down" -> down;
            case "right" -> right;
            case "left" -> left;
            default -> down;
        };

        if (image != null) {
            g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
        }
    }
}
