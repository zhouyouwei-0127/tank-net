package com.zyw.tank;

import lombok.Getter;

import java.awt.*;

public class Wall extends AbstractGameObject {

    private static final long serialVersionUID = 2807278703150570582L;

    private final int x, y, w, h;

    private final Rectangle rect;

    @Getter
    private final boolean live = true;

    public Wall(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        rect = new Rectangle(x, y, w, h);
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.GRAY);
        g.fillRect(x, y, w, h);
        g.setColor(c);
    }

    public Rectangle getRect() {
        return rect;
    }
}
