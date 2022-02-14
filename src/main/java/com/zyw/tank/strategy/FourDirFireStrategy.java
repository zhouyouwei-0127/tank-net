package com.zyw.tank.strategy;

import com.zyw.tank.*;

public class FourDirFireStrategy implements FireStrategy{

    @Override
    public void fire(Player player) {
        int bX = player.getX() + ResourceMgr.goodTankU.getWidth()/2 - ResourceMgr.bulletU.getWidth()/2;
        int bY = player.getY() + ResourceMgr.goodTankU.getHeight()/2 - ResourceMgr.bulletU.getHeight()/2;
        for (Dir dir : Dir.values()) {
            TankFrame.INSTANCE.getGm().add(new Bullet(player.getId(), bX, bY, dir, player.getGroup()));
        }
    }
}
