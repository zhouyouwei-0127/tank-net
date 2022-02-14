package com.zyw.tank.strategy;

import com.zyw.tank.Bullet;
import com.zyw.tank.Player;
import com.zyw.tank.ResourceMgr;
import com.zyw.tank.TankFrame;
import com.zyw.tank.net.Client;
import com.zyw.tank.net.msg.BulletNewMsg;

public class DefaultFireStrategy implements FireStrategy{

    @Override
    public void fire(Player player) {
        int bX = player.getX() + ResourceMgr.goodTankU.getWidth()/2 - ResourceMgr.bulletU.getWidth()/2;
        int bY = player.getY() + ResourceMgr.goodTankU.getHeight()/2 - ResourceMgr.bulletU.getHeight()/2;
        Bullet bullet = new Bullet(player.getId(), bX, bY, player.getDir(), player.getGroup());
        TankFrame.INSTANCE.getGm().add(bullet);
        //send a bullet new message to server when a bullet is born.
        Client.INSTANCE.send(new BulletNewMsg(bullet));
    }
}
