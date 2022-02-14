package com.zyw.tank;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

public class TankFrame extends Frame {

    public static final TankFrame INSTANCE = new TankFrame();

    @Getter
    private final int GAME_WIDTH = Integer.parseInt(PropertyMgr.get("gameWidth"));
    @Getter
    private final int GAME_HEIGHT = Integer.parseInt(PropertyMgr.get("gameHeight"));

    @Getter
    @Setter
    private GameModel gm = new GameModel();

    private TankFrame() {
        this.setTitle("tank war");
        this.setLocation(400, 100);
        this.setSize(GAME_WIDTH, GAME_HEIGHT);

        this.addKeyListener(new TankKeyListener());

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        gm.paint(g);
    }

    /**
     * 消除闪烁
     */
    Image offScreenImage = null;
    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    public void load() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            File file = new File("D:/zyw/java/study/tank.dat");
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            this.gm = (GameModel) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void save() {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            File file = new File("D:/zyw/java/study/tank.dat");
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(this.gm);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.flush();
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class TankKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_S) {
                save();
            } else if (keyCode == KeyEvent.VK_L) {
                load();
            }
            gm.getMyTank().keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            gm.getMyTank().keyReleased(e);
        }
    }
}
