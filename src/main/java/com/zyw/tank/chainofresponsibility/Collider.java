package com.zyw.tank.chainofresponsibility;

import com.zyw.tank.AbstractGameObject;

import java.io.Serializable;

public interface Collider extends Serializable {

    boolean collide(AbstractGameObject go1, AbstractGameObject go2);
}
