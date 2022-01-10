package com.zyw.net.nettychart;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientFrame extends Frame {

    public static final ClientFrame INSTANCE = new ClientFrame();

    private final TextArea ta = new TextArea();
    private final TextField tf = new TextField();

    private Client c;

    private ClientFrame() {
        this.setSize(300, 400);
        this.setLocation(400, 20);
        this.add(ta, BorderLayout.CENTER);
        this.add(tf, BorderLayout.SOUTH);
        this.setTitle("zyw");

        tf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.send(tf.getText());
                tf.setText("");
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                c.closeConnection();
                System.exit(0);
            }
        });
    }

    public void connectToServer() {
        c = new Client();
        c.connect();
    }

    public static void main(String[] args) {
        ClientFrame cf = ClientFrame.INSTANCE;
        cf.setVisible(true);
        cf.connectToServer();
    }

    public void updateText(String str) {
        ta.setText(ta.getText() + str + "\r\n");
    }
}
