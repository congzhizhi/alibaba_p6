package com.mashibing.tank;

import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

public class CallableDemo implements Callable<String> {
    public String call() throws Exception {
        Thread.sleep(3000);//阻塞案例演示
        return "hello world";
    }

    public static void main(String[] args) throws ExecutionException,
            InterruptedException {
        ExecutorService es= Executors.newFixedThreadPool(1);
        CallableDemo callableDemo = new CallableDemo();
        FutureTask futureTask = new FutureTask(callableDemo);

        Future future=es.submit(callableDemo);
        System.out.println(futureTask.get());
    }
}


