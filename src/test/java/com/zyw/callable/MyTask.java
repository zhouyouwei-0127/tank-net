package com.zyw.callable;

import java.util.concurrent.Callable;

public class MyTask implements Callable<Long> {

    @Override
    public Long call() throws Exception {
        long r = 0;
        for (int i = 0; i < 10; i++) {
            r += i;
            Thread.sleep(500);
            System.out.println(i + "added");
        }
        return r;
    }
}
