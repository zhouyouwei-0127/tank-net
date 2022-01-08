package com.zyw.tank.strategy;

import com.zyw.tank.Bullet;
import com.zyw.tank.Player;
import com.zyw.tank.ResourceMgr;
import com.zyw.tank.TankFrame;

public class DefaultFireStrategy implements FireStrategy{

    @Override
    public void fire(Player player) {
        int bX = player.getX() + ResourceMgr.goodTankU.getWidth()/2 - ResourceMgr.bulletU.getWidth()/2;
        int bY = player.getY() + ResourceMgr.goodTankU.getHeight()/2 - ResourceMgr.bulletU.getHeight()/2;
        TankFrame.INSTANCE.add(new Bullet(bX, bY, player.getDir(), player.getGroup()));
    }
}