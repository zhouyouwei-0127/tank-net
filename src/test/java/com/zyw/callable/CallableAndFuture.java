package com.zyw.callable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableAndFuture {
    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newCachedThreadPool();
        Future<Long> future = service.submit(new MyTask());

        Long result = future.get(); //waite until there's a result
        System.out.println(result);

        System.out.println("go on");
    }
}
