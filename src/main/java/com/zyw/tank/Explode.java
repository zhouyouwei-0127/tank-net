package com.zyw.tank;

import lombok.Getter;

import java.awt.*;

public class Explode extends AbstractGameObject {

    private static final long serialVersionUID = -8293613591505251396L;

    @Getter
    private final int x, y;

    private int step = 0;

    @Getter
    private boolean live = true;


    public Explode(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void paint(Graphics g) {
        if (!live) {
            return;
        }
        g.drawImage(ResourceMgr.explodes[step], x, y, null);
        step++;

        if (step >= ResourceMgr.explodes.length) {
            die();
        }
    }

    private void die() {
        live = false;
    }

}
