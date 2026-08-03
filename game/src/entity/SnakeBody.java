package entity;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;

public class SnakeBody {
    private final List<Point> segments = new ArrayList<>();
    private int pendingGrowth = 0;
    private BufferedImage bodyImage;

    public void moveTo(int x, int y) {
        segments.add(new Point(x, y));

        if (pendingGrowth > 0) {
            pendingGrowth--;
        } else if (!segments.isEmpty()) {
            segments.remove(0);
        }
    }

    public void grow(int amount) {
        pendingGrowth += amount;
    }

    public List<Point> getSegments() {
        return Collections.unmodifiableList(segments);
    }

    public void loadImage() {
        bodyImage = readImage("body.png");
    }

    public void draw(Graphics2D g2, int tileSize) {
        if (bodyImage != null) {
            for (Point segment : segments) {
                g2.drawImage(bodyImage, segment.x, segment.y, tileSize, tileSize, null);
            }
            return;
        }

        g2.setColor(java.awt.Color.GREEN);
        for (Point segment : segments) {
            g2.fillRect(segment.x, segment.y, tileSize, tileSize);
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
                    System.err.println("Unable to load snake body image " + path + ": " + e.getMessage());
                }
            }
        }

        return null;
    }
}
