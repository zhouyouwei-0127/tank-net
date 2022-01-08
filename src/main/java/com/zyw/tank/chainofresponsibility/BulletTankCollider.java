package com.zyw.tank.chainofresponsibility;

import com.zyw.tank.AbstractGameObject;
import com.zyw.tank.Bullet;
import com.zyw.tank.Tank;

public class BulletTankCollider implements Collider{
    @Override
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        if (go1 instanceof Bullet && go2 instanceof Tank) {
            Bullet b = (Bullet) go1;
            Tank t = (Tank) go2;
            if (!b.isLive() || !t.isLive() || b.getGroup() == t.getGroup()) {
                return false;
            }
            if (b.getRect().intersects(t.getRect())) {
                b.die();
                t.die();
                return false;
            }
        } else if (go1 instanceof Tank && go2 instanceof Bullet){
            return collide(go2, go1);
        }
        return true;
    }
}
