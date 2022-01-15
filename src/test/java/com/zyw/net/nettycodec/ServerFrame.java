package com.zyw.net.nettycodec;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerFrame extends Frame {

    public static final ServerFrame INSTANCE = new ServerFrame();
    private final TextArea taServer = new TextArea();
    private final TextArea taClient = new TextArea();
    private Server server = new Server();

    private ServerFrame() {
        this.setSize(800, 600);
        this.setLocation(300, 30);
        Panel p = new Panel(new GridLayout(1, 2));
        p.add(taServer);
        p.add(taClient);
        this.add(p);

        taServer.setFont(new Font("consolas",Font.PLAIN,25));
        taClient.setFont(new Font("consolas",Font.PLAIN,25));

        this.updateServerMsg("Server:");
        this.updateClientMsg("Client:");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public void updateServerMsg(String str) {
        this.taServer.setText(taServer.getText() + str + System.getProperty("line.separator"));
    }
    public void updateClientMsg(String str) {
        this.taClient.setText(taClient.getText() + str + System.getProperty("line.separator"));
    }

    public static void main(String[] args) {
        ServerFrame.INSTANCE.setVisible(true);
        ServerFrame.INSTANCE.server.serverStart();
    }
}
