package com.zyw.tank;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.UUID;

public class Bullet extends AbstractGameObject {

    private static final long serialVersionUID = -4190659397383230979L;

    public static final int SPEED = 10;

    @Getter
    private final Dir dir;

    @Getter
    private final Group group;

    @Getter
    private int x, y;

    @Getter
    @Setter
    private UUID id = UUID.randomUUID();

    @Getter
    private final UUID playerId;

    @Getter
    private boolean live = true;

    public static final int W = ResourceMgr.bulletU.getWidth();

    public static final int H = ResourceMgr.bulletU.getHeight();

    private final Rectangle rect;

    public Bullet(UUID playerId, int x, int y, Dir dir, Group group) {
        this.playerId = playerId;
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;

        rect = new Rectangle(x, y, W, H);
    }

    public void paint(Graphics g) {
        switch (dir) {
            case L:
                g.drawImage(ResourceMgr.bulletL, x, y, null);
                break;
            case U:
                g.drawImage(ResourceMgr.bulletU, x, y, null);
                break;
            case R:
                g.drawImage(ResourceMgr.bulletR, x, y, null);
                break;
            case D:
                g.drawImage(ResourceMgr.bulletD, x, y, null);
                break;
        }

        move();
        rect.x = x;
        rect.y = y;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void die() {
        this.live = false;
    }

    private void move() {
        switch (dir) {
            case L:
                x -= SPEED;
                break;
            case U:
                y -= SPEED;
                break;
            case R:
                x += SPEED;
                break;
            case D:
                y += SPEED;
                break;
        }
        boundsCheck();
    }

    private void boundsCheck() {
        if (x < 0 || y < 30 || x > TankFrame.INSTANCE.getGAME_WIDTH() || y > TankFrame.INSTANCE.getGAME_WIDTH()) {
            live = false;
        }
    }
}
