package com.zyw.tank;

import com.zyw.tank.chainofresponsibility.ColliderChain;
import lombok.Getter;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameModel implements Serializable {

    private static final long serialVersionUID = -7399388434888388772L;

    @Getter
    private Player myTank;

    private List<AbstractGameObject> objects;

    private final ColliderChain colliderChain = new ColliderChain();

    public GameModel() {
        initGameObjects();
    }

    private void initGameObjects() {
        this.myTank = new Player(100, 100, Dir.D, Group.GOOD);

        this.objects = new ArrayList<>();
        int tankCount = Integer.parseInt(PropertyMgr.get("initTankCount"));
        for (int i = 0; i < tankCount; i++) {
            objects.add(new Tank(100 * i, 200, Dir.D, Group.BAD));
        }
        //this.add(new Wall(300, 400, 400, 50));
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("objects: " + objects.size(), 10, 50);
        g.setColor(c);

        if (myTank.isLive()) {
            myTank.paint(g);
        }

        for (int i = 0; i < objects.size(); i++) {
            if (!objects.get(i).isLive()) {
                objects.remove(i);
            }
        }
        for (int i = 0; i < objects.size(); i++) {
            AbstractGameObject go1 = objects.get(i);
            for (int j = 0; j < objects.size(); j++) {
                colliderChain.collide(go1, objects.get(j));
            }
            if (objects.get(i).isLive()) {
                objects.get(i).paint(g);
            }
        }
    }

    public void add(AbstractGameObject go) {
        objects.add(go);
    }
}
