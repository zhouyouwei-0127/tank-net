package com.zyw.tank;

import lombok.Getter;

import java.awt.*;
import java.util.Random;

public class Tank {
    @Getter
    private int x, y;

    private Dir dir;

    private static final int SPEED = 5;

    private boolean moving = true;

    @Getter
    private final Group group;

    @Getter
    private boolean live = true;

    private int oldX, oldY;

    private final int width, height;

    private final Random r = new Random();

    public Tank(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;

        this.oldX = x;
        this.oldY = y;

        this.width = ResourceMgr.goodTankU.getWidth();
        this.height = ResourceMgr.goodTankU.getHeight();
    }

    public void paint(Graphics g) {
        switch (dir) {
            case L:
                g.drawImage(ResourceMgr.badTankL, x, y, null);
                break;
            case U:
                g.drawImage(ResourceMgr.badTankU, x, y, null);
                break;
            case R:
                g.drawImage(ResourceMgr.badTankR, x, y, null);
                break;
            case D:
                g.drawImage(ResourceMgr.badTankD, x, y, null);
                break;
        }

        move();
    }

    private void fire() {
        int bX = this.x + ResourceMgr.goodTankU.getWidth()/2 - ResourceMgr.bulletU.getWidth()/2;
        int bY = this.y + ResourceMgr.goodTankU.getHeight()/2 - ResourceMgr.bulletU.getHeight()/2;
        TankFrame.INSTANCE.addBullet(new Bullet(bX, bY, dir, group));
    }

    private void move() {
        if (!moving) return;

        oldX = x;
        oldY = y;

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
        randomDir();

        if (r.nextInt(100) > 85)
            fire();
    }

    private void boundsCheck() {
        if (x < 0 || y < 30 || x + width > TankFrame.INSTANCE.GAME_WIDTH || y + height > TankFrame.INSTANCE.GAME_HEIGHT) {
            back();
        }
    }

    private void back() {
        x = oldX;
        y = oldY;
    }

    private void randomDir() {
        if (r.nextInt(100) > 90)
            this.dir = Dir.randomDir();
    }

    public void die() {
        live = false;
        TankFrame.INSTANCE.addExplode(new Explode(x, y));
    }
}