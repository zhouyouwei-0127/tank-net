package com.zyw.tank;

import com.zyw.tank.net.Client;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        TankFrame.INSTANCE.setVisible(true);

        new Thread(() -> {
            for (; ; ) {
                try {
                    TimeUnit.MILLISECONDS.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                TankFrame.INSTANCE.repaint();
            }
        }).start();

        Client.INSTANCE.connect();
    }
}
