package com.zyw.tank;

import lombok.Getter;

import java.awt.*;
import java.io.Serializable;

public abstract class AbstractGameObject implements Serializable {

    private static final long serialVersionUID = -8544393060622442617L;

    @Getter
    public boolean live;

    public abstract void paint(Graphics g);

}
