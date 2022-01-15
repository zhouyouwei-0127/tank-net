package com.zyw.net.nettycodec;

import lombok.Getter;

@Getter
public class TankMsg {

    private final int x;
    private final int y;

    public TankMsg(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "TankMsg{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
