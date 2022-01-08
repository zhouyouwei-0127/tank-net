package com.zyw.tank;

import lombok.Getter;

import java.awt.*;

public class Explode extends AbstractGameObject {
    @Getter
    private final int x, y;

    private final int width, height;

    private int step = 0;

    @Getter
    private boolean live = true;


    public Explode(int x, int y) {
        this.x = x;
        this.y = y;

        this.width = ResourceMgr.explodes[0].getWidth();
        this.height = ResourceMgr.explodes[0].getHeight();
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
