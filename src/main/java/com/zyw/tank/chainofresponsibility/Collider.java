package com.zyw.tank.chainofresponsibility;

import com.zyw.tank.AbstractGameObject;

public interface Collider {

    boolean collide(AbstractGameObject go1, AbstractGameObject go2);
}
