package com.zyw.tank.strategy;

import com.zyw.tank.Player;

import java.io.Serializable;

public interface FireStrategy extends Serializable {

    void fire(Player player);
}
