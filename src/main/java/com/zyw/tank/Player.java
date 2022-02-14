package com.zyw.tank;

import com.zyw.tank.net.Client;
import com.zyw.tank.net.msg.TankMoveOrDirChangeMsg;
import com.zyw.tank.net.msg.TankStopMsg;
import com.zyw.tank.strategy.FireStrategy;
import lombok.Getter;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.UUID;

public class Player extends AbstractGameObject {

    private static final long serialVersionUID = -1938217222300422186L;

    @Getter
    private int x, y;

    @Getter
    private Dir dir;

    private boolean bL, bU, bR, bD;

    private static final int SPEED = 5;

    @Getter
    private boolean moving;

    @Getter
    private final Group group;

    @Getter
    private boolean live = true;

    private FireStrategy strategy = null;

    @Getter
    private final UUID id = UUID.randomUUID();

    public Player(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;

        initFireStrategy();
    }

    public void paint(Graphics g) {
        if (!this.isLive()) return;

        Color c = g.getColor();
        g.setColor(Color.YELLOW);
        g.drawString(id.toString(), x, y - 10);
        g.setColor(c);

        switch (dir) {
            case L:
                g.drawImage(this.group.equals(Group.BAD) ? ResourceMgr.badTankL : ResourceMgr.goodTankL, x, y, null);
                break;
            case U:
                g.drawImage(this.group.equals(Group.BAD) ? ResourceMgr.badTankU : ResourceMgr.goodTankU, x, y, null);
                break;
            case R:
                g.drawImage(this.group.equals(Group.BAD) ? ResourceMgr.badTankR : ResourceMgr.goodTankR, x, y, null);
                break;
            case D:
                g.drawImage(this.group.equals(Group.BAD) ? ResourceMgr.badTankD : ResourceMgr.goodTankD, x, y, null);
                break;
        }

        move();
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                bL = true;
                break;
            case KeyEvent.VK_UP:
                bU = true;
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                break;
        }

        setMainDir();
    }

    private void setMainDir() {
        boolean oldMoving = moving;
        Dir oldDir = this.getDir();
        if (!bL && !bU && !bR && !bD) {
            moving = false;
            if (oldMoving)
                Client.INSTANCE.send(new TankStopMsg(this.id, this.x, this.y));
        }else {
            moving = true;
            if (bL && !bU && !bR && !bD)
                dir = Dir.L;
            if (!bL && bU && !bR && !bD)
                dir = Dir.U;
            if (!bL && !bU && bR && !bD)
                dir = Dir.R;
            if (!bL && !bU && !bR && bD)
                dir = Dir.D;

            if (!oldMoving || !oldDir.equals(this.dir))
                Client.INSTANCE.send(new TankMoveOrDirChangeMsg(this.id, this.x, this.y, this.dir));
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                bL = false;
                break;
            case KeyEvent.VK_UP:
                bU = false;
                break;
            case KeyEvent.VK_RIGHT:
                bR = false;
                break;
            case KeyEvent.VK_DOWN:
                bD = false;
                break;
            case KeyEvent.VK_CONTROL:
                fire();
                break;
        }

        setMainDir();
    }

    private void initFireStrategy() {
        try {
            Class<?> clazz = Class.forName("com.zyw.tank.strategy." + PropertyMgr.get("fireStrategy"));
            strategy = (FireStrategy) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fire() {

        strategy.fire(this);
    }

    private void move() {
        if (!moving) {
            return;
        }
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
    }

    public void die() {
        live = false;
    }
}
