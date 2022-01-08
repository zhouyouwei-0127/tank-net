package com.zyw.tank.chainofresponsibility;

import com.zyw.tank.AbstractGameObject;
import com.zyw.tank.PropertyMgr;

import java.util.ArrayList;
import java.util.List;

public class ColliderChain implements Collider {

    private List<Collider> colliders;

    public ColliderChain() {
        initCollider();
    }

    private void initCollider() {
        this.colliders = new ArrayList<>();
        String[] colliderNames = PropertyMgr.get("colliders").split(",");
        try {
            for (String colliderName : colliderNames) {
                Class clazz = Class.forName("com.zyw.tank.chainofresponsibility." + colliderName);
                Collider collider = (Collider) clazz.getDeclaredConstructor().newInstance();
                colliders.add(collider);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        for (Collider collider : colliders) {
            if (!collider.collide(go1, go2)) {
                return false;
            }
        }
        return true;
    }
}
