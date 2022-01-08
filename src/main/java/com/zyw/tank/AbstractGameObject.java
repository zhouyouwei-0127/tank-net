package com.zyw.tank;

import lombok.Getter;

import java.awt.*;

public abstract class AbstractGameObject {

    @Getter
    public boolean live;

    public abstract void paint(Graphics g);

}
