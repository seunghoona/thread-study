package com.seunghoo.thread;

public class ThreadSleep {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        new Thread(()-> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("");
        }).start();
        long end = System.currentTimeMillis();
        long l = end - start;
        System.out.println("걸린시간 = " + l);
    }
}
