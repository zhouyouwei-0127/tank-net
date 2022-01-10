package com.zyw.tanktest;

import org.junit.Test;

import java.io.*;

public class SerializeTest {

    @Test
    public void testSave() throws Exception {
        T t = new T();
        File file = new File("D:/zyw/java/study/s.dat");
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(t);
        oos.flush();
        oos.close();
        fos.flush();
        fos.close();
    }

    @Test
    public void testLoad() throws Exception {
        File file = new File("D:/zyw/java/study/s.dat");
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        T t = (T) ois.readObject();
        assert t.m == 8;
        assert t.n == 5;
    }
}

class T implements Serializable{

    private static final long serialVersionUID = 1316678938606272432L;

    int m = 8;
    int n = 5;

    //if decorated by transient,not participate in serialize
    transient String password = "123456";
}
