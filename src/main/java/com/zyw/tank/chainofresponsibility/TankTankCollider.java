package com.zyw.tank.chainofresponsibility;

import com.zyw.tank.AbstractGameObject;
import com.zyw.tank.Bullet;
import com.zyw.tank.Tank;
import com.zyw.tank.Wall;

public class TankTankCollider implements Collider {
    @Override
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        if (go1 != go2 && go1 instanceof Tank && go2 instanceof Tank) {
            Tank t1 = (Tank) go1;
            Tank t2 = (Tank) go2;
            if (t1.getRect().intersects(t2.getRect())) {
                t1.back();
                t2.back();
            }
        }
        return true;
    }
}
