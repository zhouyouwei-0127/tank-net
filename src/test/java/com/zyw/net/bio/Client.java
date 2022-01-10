package com.zyw.net.bio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost", 8888);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        writer.write("zyw");
        writer.newLine();
        writer.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String str = reader.readLine();
        System.out.println(str);

        writer.close();
        s.close();
    }
}
