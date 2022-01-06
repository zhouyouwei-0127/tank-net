package com.zyw.tank;

import java.io.IOException;
import java.util.Properties;

public class PropertyMgr {

    private static final Properties prop = new Properties();

    static {
        try {
            prop.load(PropertyMgr.class.getClassLoader().getResourceAsStream("configure"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return (String) prop.get(key);
    }
}
