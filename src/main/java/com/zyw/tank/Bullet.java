package com.zyw.tank;

import lombok.Getter;

import java.awt.*;

public class Bullet {

    public static final int SPEED = 10;

    private final Dir dir;

    private final Group group;

    private int x, y;

    @Getter
    private boolean live = true;

    public Bullet(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
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
    }

    public void collidesWithTank(Tank tank) {
        if (!this.isLive() || !tank.isLive() || this.group == tank.getGroup()) {
            return;
        }
        Rectangle rect = new Rectangle(this.x, this.y, ResourceMgr.bulletU.getWidth(), ResourceMgr.bulletU.getHeight());
        Rectangle rectTank = new Rectangle(tank.getX(), tank.getY(),
                ResourceMgr.goodTankU.getWidth(), ResourceMgr.goodTankU.getHeight());
        if (rect.intersects(rectTank)) {
            this.die();
            tank.die();
        }
    }

    private void die() {
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
        if (x < 0 || y < 30 || x > TankFrame.INSTANCE.GAME_WIDTH || y > TankFrame.INSTANCE.GAME_HEIGHT) {
            live = false;
        }
    }
}
