package com.seunghoo.thread;

public class ThreadBasic {
    public static void main(String[] args) {
        System.out.println("Main Thread name =" + Thread.currentThread().getName());
        new Thread (()->{
            System.out.println("createThread name =" + Thread.currentThread().getName());
        }).start();

        new Thread (()->{
            System.out.println("createThread name =" + Thread.currentThread().getName());
        }).start();
    }
}
